package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.AccountDTO;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;
    private final AccountMapper mapper;

    public AccountController(AccountService service, AccountMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public Page<AccountDTO> showAllAccounts(Pageable pageable) {
        return  mapper.toDTOs(service.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public AccountDTO showAccountInfo(@PathVariable long id) {
        return mapper.accountToDTO(service.getAccount(id));
    }
}