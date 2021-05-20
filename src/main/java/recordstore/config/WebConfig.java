package recordstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import recordstore.utils.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    String uploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/images/**",
                "/fonts/**",
                "/css/**",
                "/js/**",
                "/select2/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "file:" + uploadPath,
                        "classpath:/static/fonts/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/select2/");
    }

    @Bean
    public FileService fileService(){
        return new FileServiceImpl();
    }

    @Bean
    public EmailService emailService(JavaMailSender javaMailSender){
        return new EmailServiceImpl(javaMailSender);
    }
}