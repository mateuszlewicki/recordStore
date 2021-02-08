package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.service.AccountService;

@Controller
@RequestMapping("admin/accounts/")
public class AdminAccountController {

    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("accounts", accountService.getAllUsers());
        return "admin/accounts/index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id){
        if (accountService.isPresent(id)) {
            accountService.deleteUser(id);
        }
    return "redirect:/admin/accounts/";
    }
}