package recordstore.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithVerificationToken(String contextPath, String recipientAddress, String token) {
        SimpleMailMessage email = constructEmailMessage(contextPath, recipientAddress, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(String contextPath, String recipientAddress, String token) {
        String confirmationUrl = contextPath + "/registrationConfirm?token=" + token;
        String message = "You registered successfully. To confirm your registration, please click on the below link.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject("Registration confirmation");
        email.setText(message + " \r\n" + "http://localhost:8080" + confirmationUrl);
        email.setFrom("thebeststore@mariia.top");
        return email;
    }
}