package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.AccountDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService service;
    private final MapStructMapper mapStructMapper;

    public AccountsController(AccountService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<AccountDTO> showAllAccounts(Pageable pageable) {
        return  service.getAllUsers(pageable).map(mapStructMapper::accountToAccountDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> showAccountInfo(@PathVariable long id) {
        AccountDTO accountDTO = mapStructMapper.accountToAccountDTO(service.getAccount(id));
        return ResponseEntity.ok(accountDTO);
    }
}