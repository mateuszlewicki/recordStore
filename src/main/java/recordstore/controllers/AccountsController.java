package recordstore.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import recordstore.service.AccountService;
import recordstore.DTO.AccountDTO;
import recordstore.mapper.AccountMapper;
import recordstore.DTO.UpdateAccountDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService service;
    private final AccountMapper accountMapper;

    public AccountsController(AccountService service, AccountMapper accountMapper) {
        this.service = service;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountInfo(@PathVariable long id) {
        var accountDTO = accountMapper.toDTO(service.getAccount(id));
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }

//    @GetMapping("/{id}/releases")
//    public Page<ReleaseProjection> getAllReleasesByAccount(@PathVariable long id, Pageable pageable){
//        return service.getReleasesByAccount(id, pageable);
//    }

    // managing the account
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDTO> updateAccount(@Valid @RequestBody UpdateAccountDTO updateAccountDTO,
                                                    @PathVariable long id) {
        var accountDTO = accountMapper.toDTO(service.updateAccount(id, updateAccountDTO));
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDTO> uploadImage(@PathVariable("id") long id,
                                                  @RequestParam("file") MultipartFile file) {
        var accountDTO = accountMapper.toDTO(service.uploadImage(id, file));
        return ResponseEntity.ok(accountDTO);
    }

    // The handling of account's associations
    @PatchMapping("/{userId}/releases/{releaseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDTO> addReleaseToCollection(@PathVariable("userId") long userId,
                                                             @PathVariable("releaseId") long releaseId){
        var accountDTO = accountMapper.toDTO(service.addReleaseToCollection(userId, releaseId));
        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/{userId}/releases/{releaseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDTO> removeReleaseFromCollection(@PathVariable("userId") long userId,
                                                                  @PathVariable("releaseId") long releaseId){
        var accountDTO = accountMapper.toDTO(service.removeReleaseFromCollection(userId, releaseId));
        return ResponseEntity.ok(accountDTO);
    }
}