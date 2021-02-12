package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import recordstore.DTO.AccountDTO;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    public RegistrationController(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new AccountDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid AccountDTO accountDTO,
                          BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", accountDTO);
            return "registration";
        }
        if (!accountService.saveUser(accountMapper.fromDTO(accountDTO))) {
            model.addAttribute("usernameError", "User with the same name already exists");
            return "registration";
        }
        return "redirect:/login";
    }
}