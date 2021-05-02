package recordstore.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithVerificationToken(String contextPath, String recipientAddress, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String confirmationUrl = contextPath + "/registrationConfirm?token=" + token;
        String message = "You registered successfully. To confirm your registration, please click on the ";

        helper.setText(message + "<a href='https://thebeststore.herokuapp.com" + confirmationUrl + "'>link</a>", true);

        helper.setSubject("Registration confirmation");
        helper.setTo(recipientAddress);
        helper.setFrom("thebeststore@mariia.top");

        mailSender.send(mimeMessage);
    }
}