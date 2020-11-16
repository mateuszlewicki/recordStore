package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.service.AccountDetailsServiceImpl;

@Controller
@RequestMapping("admin/accounts/")
public class AdminAccountController {

    private final AccountDetailsServiceImpl accountDetailsService;

    public AdminAccountController(AccountDetailsServiceImpl accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("accounts", accountDetailsService.getAllUsers());
        return "admin/accounts/index";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
    accountDetailsService.deleteUser(id);
    return "redirect:/admin/accounts/";
}
}
