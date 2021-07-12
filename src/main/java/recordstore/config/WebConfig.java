package recordstore.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import recordstore.utils.*;

@Configuration
@EnableAutoConfiguration
public class WebConfig {

    @Bean
    public FileService fileService(){
        return new FileServiceImpl();
    }

    @Bean
    public EmailService emailService(JavaMailSender javaMailSender){
        return new EmailServiceImpl(javaMailSender);
    }
}