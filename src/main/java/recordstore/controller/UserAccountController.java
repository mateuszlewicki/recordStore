package recordstore.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.error.WrongIdException;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserAccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    public UserAccountController(AccountMapper accountMapper, AccountService accountService) {
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
                                BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "client/user/edit";
        }
        accountService.updateAccount(accountDTO);
        model.addAttribute("accountId", accountDTO.getId());
        return "redirect:/account/{accountId}";
    }

    @ExceptionHandler(WrongIdException.class)
    public ModelAndView wrongIdHandler(WrongIdException ex) {
        ModelAndView modelAndView = new ModelAndView("/errorPages/error");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }
}