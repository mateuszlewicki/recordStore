package recordstore.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.AccountService;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AccountService service;
    private final MapStructMapper mapStructMapper;

    public UserController(AccountService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@Valid @RequestBody UpdateAccountDTO updateAccountDTO,
                                                    @PathVariable long id) throws IOException {
        AccountDTO accountDTO = mapStructMapper.accountToAccountDTO(
                service.updateAccount(id, updateAccountDTO));
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> uploadImage(@PathVariable("id") long id,
                                                  @RequestParam("file") MultipartFile file) {
        AccountDTO accountDTO = mapStructMapper.accountToAccountDTO(service.uploadImage(id, file));
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }

    @PatchMapping("/{userId}/releases/{releaseId}")
    public ResponseEntity<AccountDTO> addReleaseToCollection(@PathVariable("userId") long userId,
                                                        @PathVariable("releaseId") long releaseId){
        AccountDTO accountDTO = mapStructMapper.accountToAccountDTO(service.addReleaseToCollection(userId, releaseId));
        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/{userId}/releases/{releaseId}")
    public ResponseEntity<AccountDTO> removeReleaseFromCollection(@PathVariable("userId") long userId,
                                                             @PathVariable("releaseId") long releaseId){
        AccountDTO accountDTO = mapStructMapper.accountToAccountDTO(service.removeReleaseFromCollection(userId, releaseId));
        return ResponseEntity.ok(accountDTO);
    }
}