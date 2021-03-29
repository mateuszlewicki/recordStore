package recordstore.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthAccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    public AuthAccountController(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, @AuthenticationPrincipal Account account) {
        model.addAttribute("account", accountMapper.toDTO(accountService.getAccount(account.getId())));
        return "client/user/edit";
    }

    @PostMapping("/edit")
    public String updateAccount(@Valid @ModelAttribute("account") UpdateAccountDTO accountDTO,
                                BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "client/user/edit";
        }
        accountService.updateAccount(accountDTO);
        return "redirect:/account/" + accountDTO.getId();
    }
}