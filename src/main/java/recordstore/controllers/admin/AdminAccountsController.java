package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.AccountDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.AccountService;

import java.io.IOException;

@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountsController {

    private final AccountService service;
    private final MapStructMapper mapStructMapper;

    public AdminAccountsController(AccountService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<AccountDTO> showAllAccounts(Pageable pageable) {
        return service.getAllUsers(pageable).map(mapStructMapper::accountToAccountDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id) throws IOException {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}