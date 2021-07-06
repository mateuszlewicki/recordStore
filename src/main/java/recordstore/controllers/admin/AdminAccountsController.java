package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.AccountDTO;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

import java.io.IOException;

@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountsController {

    private final AccountService service;
    private final AccountMapper mapper;

    public AdminAccountsController(AccountService service, AccountMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public Page<AccountDTO> showAllAccounts(Pageable pageable) {
        return  mapper.toDTOs(service.getAllUsers(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAccount(@PathVariable long id) throws IOException {
        service.deleteAccount(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}