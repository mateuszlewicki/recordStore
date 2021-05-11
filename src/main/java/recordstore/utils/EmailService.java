package recordstore.utils;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmailWithVerificationToken(String contextPath, String recipientAddress, String token)
            throws MessagingException;
}