package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.entity.Account;
import recordstore.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public String showAccountInfo(@PathVariable long id, Model model) {
        if (accountService.isPresent(id)) {
            Account account = accountService.getAccount(id);
            model.addAttribute("account", account);
        } else {
            model.addAttribute("error", "Account not found");
        }
        return "client/user/profile";
    }
}