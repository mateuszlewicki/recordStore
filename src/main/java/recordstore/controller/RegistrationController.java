package recordstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recordstore.DTO.CreateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyExistException;
import recordstore.error.TokenExpiredException;
import recordstore.error.TokenNotFoundException;
import recordstore.mapper.AccountMapper;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;
import recordstore.utils.EmailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    private ApplicationEventPublisher eventPublisher;
    private EmailService emailService;

    public RegistrationController(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/registration")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new CreateAccountDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewAccount(@ModelAttribute("user") @Valid CreateAccountDTO accountDTO, BindingResult result,
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
        }
        return "redirect:/login";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = accountService.getVerificationToken(token);
        accountService.saveRegisterUser(verificationToken.getAccount());
        return "redirect:/login";
    }

    @PostMapping("/resendRegistrationToken")
    public String resendRegistrationToken(HttpServletRequest request,
                                          @RequestParam("token") String existingToken, Model model) throws MessagingException {
        VerificationToken token = accountService.generateNewVerificationToken(existingToken);
        Account account = token.getAccount();
        emailService.sendEmailWithVerificationToken(request.getContextPath(), account.getEmail(), token.getToken());
        return "redirect:/login";
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ModelAndView tokenNotFoundHandler(TokenNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("/errorPages/tokenNotFoundError");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(MessagingException.class)
    public ModelAndView mailAuthenticationHandler() {
        ModelAndView modelAndView = new ModelAndView("/errorPages/emailError");
        modelAndView.getModel().put("message", "Error in mail configuration.");
        return modelAndView;
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ModelAndView tokenExpiredHandler(TokenExpiredException ex) {
        ModelAndView modelAndView = new ModelAndView("/errorPages/tokenExpiredError");
        modelAndView.getModel().put("message", "Your registration token has expired.");
        modelAndView.getModel().put("token", ex.getMessage());
        return modelAndView;
    }
}