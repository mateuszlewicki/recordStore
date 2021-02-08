package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import recordstore.entity.Account;
import recordstore.service.AccountService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String showSignUpForm(Model model) {
        model.addAttribute("userForm", new Account());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid Account user,
                          BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Your new password and confirmation password do not match");
            return "registration";
        }
        if (user.getPassword().length() < 6) {
            model.addAttribute("passwordError", "Password should have min 6 characters");
            return "registration";
        }
        if (user.getPassword().length() > 16) {
            model.addAttribute("passwordError", "Password should have max 16 characters");
            return "registration";
        }
        if (!accountService.saveUser(user)) {
            model.addAttribute("usernameError", "User with the same name already exists");
            return "registration";
        }
        return "redirect:/login";
    }
}