package recordstore.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final AccountService service;

    public FacebookConnectionSignup(AccountService service) {
        this.service = service;
    }

    @Override
    public String execute(Connection<?> connection) {
        Account account = new Account();
        account.setUsername(connection.getDisplayName());
        account.setPassword(RandomStringUtils.randomAlphabetic(8));
        account.setEmail(getUserEmail(connection));
        //service.saveFacebookUser(account);
        return account.getUsername();
    }

    private String getUserEmail(Connection<?> connection){
        Facebook facebook = (Facebook) connection.getApi();
        return facebook.fetchObject("me", User.class, "email").getEmail();
    }
}