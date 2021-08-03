package recordstore.controllers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.CreateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;
import recordstore.utils.EmailService;
import recordstore.utils.GenericResponse;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class RegistrationController {

    private final AccountService service;
    private final ApplicationEventPublisher eventPublisher;
    private final EmailService emailService;

    public RegistrationController(AccountService service,
                                  ApplicationEventPublisher eventPublisher,
                                  EmailService emailService) {
        this.service = service;
        this.eventPublisher = eventPublisher;
        this.emailService = emailService;
    }

    @PostMapping("/registration")
    public GenericResponse registerNewAccount(@Valid CreateAccountDTO accountDTO, HttpServletRequest request) {
        Account account = service.createNewAccount(accountDTO);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, getAppUrl(request)));
        return new GenericResponse("success");
    }

    @GetMapping("/registrationConfirm")
    public GenericResponse confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = service.getVerificationToken(token);
        service.saveRegisterUser(verificationToken.getAccount());
        return new GenericResponse("success");
    }

    @PostMapping("/resendRegistrationToken")
    public GenericResponse resendRegistrationToken(HttpServletRequest request,
                                                   @RequestBody Map<String, String> requestParams) throws MessagingException {
        VerificationToken token = service.generateNewVerificationToken(requestParams.get("token"));
        Account account = token.getAccount();
        emailService.sendEmailWithVerificationToken(getAppUrl(request), account.getEmail(), token.getToken());
        return new GenericResponse("success");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}