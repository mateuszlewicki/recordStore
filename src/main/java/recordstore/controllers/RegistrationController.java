package recordstore.controllers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.CreateAccountDTO;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;
import recordstore.service.email.EmailService;
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
    public ResponseEntity<Object> registerNewAccount(@Valid CreateAccountDTO accountDTO, HttpServletRequest request) {
        var account = service.createAccount(accountDTO);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, getAppUrl(request)));
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Object> confirmRegistration(@RequestParam("token") String token) {
        service.enableAccount(token);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/resendRegistrationToken")
    public ResponseEntity<Object> resendRegistrationToken(HttpServletRequest request,
                                                   @RequestBody Map<String, String> requestParams) throws MessagingException {
        var token = service.regenerateToken(requestParams.get("token"));
        var account = token.getAccount();
        emailService.sendEmailWithVerificationToken(getAppUrl(request), account.getEmail(), token.getToken());
        return ResponseEntity.status(200).build();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}