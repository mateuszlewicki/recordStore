package recordstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyExistException;
import recordstore.error.MailAuthenticationException;
import recordstore.error.TokenNotFoundException;
import recordstore.mapper.AccountMapper;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;
import recordstore.utils.EmailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
public class RegistrationController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    EmailService emailService;

    public RegistrationController(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new AccountDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewAccount(@ModelAttribute("user") @Valid AccountDTO accountDTO, BindingResult result,
                                     HttpServletRequest request, Model model) {
        if (result.hasErrors()){
            return "registration";
        }
        try{
            Account account = accountService.createNewAccount(accountMapper.fromDTO(accountDTO));
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, request.getContextPath()));
        } catch (AccountAlreadyExistException e) {
            model.addAttribute("emailError", e.getMessage());
            return "registration";
        } catch (MailAuthenticationException e){
            model.addAttribute("message", "Error in java mail configuration.");
            return "/errorPages/emailError";
        } catch (RuntimeException e){
            model.addAttribute("message", e.getMessage());
            return "/errorPages/badUser";
        }
        return "redirect:/login";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = accountService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Invalid token.");
            return "/errorPages/badUser";
        }
        if (isDateExpired(verificationToken) && verificationToken.getAccount().isEnabled() == false) {
            model.addAttribute("message", "Your registration token has expired.");
            model.addAttribute("expired", true);
            model.addAttribute("token", verificationToken.getToken());
            return "/errorPages/badUser";
        }
        accountService.saveRegisterUser(verificationToken.getAccount());
        return "redirect:/login";
    }

    @GetMapping("/resendRegistrationToken")
    public String resendRegistrationToken(HttpServletRequest request,
                                          @RequestParam("token") String existingToken, Model model) throws MessagingException {
        try {
            VerificationToken token = accountService.generateNewVerificationToken(existingToken);
            Account account = token.getAccount();
            emailService.sendEmailWithVerificationToken(request.getContextPath(), account.getEmail(), token.getToken());
        } catch (TokenNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "/errorPages/badUser";
        } catch (MailAuthenticationException e) {
            model.addAttribute("message", "Error in java mail configuration.");
            return "/errorPages/emailError";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "/errorPages/badUser";
        }
        return "redirect:/login";
    }

    private boolean isDateExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0;
    }
}