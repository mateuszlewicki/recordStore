package recordstore.service.facebook;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;
import recordstore.service.AccountService;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final AccountService service;

    public FacebookConnectionSignup(AccountService service) {
        this.service = service;
    }

    @Override
    public String execute(Connection<?> connection) {
        String email = getUserEmail(connection);
        var existedAccount = service.getAccountByEmail(email);
        if (existedAccount != null) {
            return existedAccount.getUsername();
        }
        var account = new Account();
        account.setUsername(connection.getDisplayName());
        account.setPassword(randomAlphabetic(8));
        account.setEmail(getUserEmail(connection));
        account.setEnabled(true);
        var savedAccount = service.createAccountWithFacebook(account);
        return savedAccount.getUsername();
    }

    private String getUserEmail(Connection<?> connection){
        Facebook facebook = (Facebook) connection.getApi();
        return facebook.fetchObject("me", User.class, "email").getEmail();
    }
}