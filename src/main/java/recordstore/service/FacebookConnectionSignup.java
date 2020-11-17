package recordstore.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final AccountDetailsServiceImpl service;

    public FacebookConnectionSignup(AccountDetailsServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(Connection<?> connection) {
        Account user = new Account();
        user.setUsername(connection.getDisplayName());
        user.setPassword(RandomStringUtils.randomAlphabetic(4));
        service.saveUser(user);
        return user.getUsername();
    }
}