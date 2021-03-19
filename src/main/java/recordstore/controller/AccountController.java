package recordstore.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import recordstore.entity.Account;

@Controller
public class AccountController {

    @GetMapping("/profile")
    public String showDashboard(Model model, @AuthenticationPrincipal Account account) {
        if (account == null) {
            return "login";
        }
        model.addAttribute("account", account);
        return "client/user/profile";
    }
}

