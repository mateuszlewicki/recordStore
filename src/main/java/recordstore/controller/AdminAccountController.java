package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.service.AccountDetailsServiceImpl;

@Controller
@RequestMapping("admin/accounts")
public class AdminAccountController {

    private final AccountDetailsServiceImpl accountDetailsService;

    public AdminAccountController(AccountDetailsServiceImpl accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("accounts", accountDetailsService.allUsers());
        return "admin/accounts/index";
    }

    @PostMapping("delete/{id}")
    public String deleteUser(@RequestParam long id) {
        accountDetailsService.deleteUser(id);
        return "redirect:/admin/accounts";
    }
}
