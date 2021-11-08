package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.AccountDTO;
import recordstore.service.AccountService;

@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountsController {

    private final AccountService service;

    public AdminAccountsController(AccountService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<AccountDTO> showAllAccounts(Pageable pageable) {
        return service.getAllAccounts(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}