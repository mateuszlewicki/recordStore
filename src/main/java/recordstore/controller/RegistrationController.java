package recordstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyExistException;
import recordstore.mapper.AccountMapper;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
public class RegistrationController {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

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
        } catch (AccountAlreadyExistException aaeEx) {
            model.addAttribute("emailError", "User with the same email already exists.");
            return "registration";
        } catch (RuntimeException ex){
            model.addAttribute("message", "Error in java mail configuration.");
            return "/errorPages/emailError";
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
        if (isDateExpired(verificationToken)) {
            model.addAttribute("message", "Your registration token has expired. Please register again.");
            return "/errorPages/badUser";
        }
        accountService.saveRegisterUser(verificationToken.getAccount());
        return "redirect:/login";
    }

    private boolean isDateExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        if (token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return true;
        }
        return false;
    }
}