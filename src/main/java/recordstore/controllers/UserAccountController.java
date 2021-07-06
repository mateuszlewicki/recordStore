package recordstore.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.service.AccountService;
import recordstore.utils.GenericResponse;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserAccountController {

    private final AccountService service;

    public UserAccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/edit")
    public GenericResponse updateAccount(@Valid UpdateAccountDTO accountDTO,
                                         @AuthenticationPrincipal Account account) throws IOException {
        service.updateAccount(account.getId(), accountDTO);
        return new GenericResponse("success");
    }
}