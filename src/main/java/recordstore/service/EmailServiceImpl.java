package recordstore.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithVerificationToken(String contextPath, String recipientAddress, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String confirmationUrl = contextPath + "/registrationConfirm?token=" + token;
        String message = "You registered successfully. To confirm your registration, please click on the ";

        helper.setText(message + "<a href='" + confirmationUrl + "'>link</a>", true);

        helper.setSubject("Registration confirmation");
        helper.setTo(recipientAddress);
        helper.setFrom("thebeststore@mariia.top");

        mailSender.send(mimeMessage);
    }
}
