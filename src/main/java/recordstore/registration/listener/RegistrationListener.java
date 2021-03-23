package recordstore.registration.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.registration.OnRegistrationCompleteEvent;
import recordstore.service.AccountService;
import recordstore.utils.EmailService;

import javax.mail.MessagingException;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final AccountService accountService;
    private final EmailService emailService;

    public RegistrationListener(AccountService accountService, EmailService emailService) {
        this.accountService = accountService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        Account account = event.getAccount();
        VerificationToken token = accountService.createVerificationToken(account);
        emailService.sendEmailWithVerificationToken(event.getAppUrl(), account.getEmail(), token.getToken());
    }
}