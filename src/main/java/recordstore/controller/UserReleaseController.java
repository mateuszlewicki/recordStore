package recordstore.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.entity.Account;
import recordstore.service.AccountService;
import recordstore.service.ReleaseService;

@Controller
@RequestMapping("/user/releases")
public class UserReleaseController {

    private final AccountService accountService;
    private final ReleaseService releaseService;

    public UserReleaseController(AccountService accountService, ReleaseService releaseService) {
        this.accountService = accountService;
        this.releaseService = releaseService;
    }

    @PostMapping("/addToCollection")
    public String addReleaseToCollection(@RequestParam("id") long id,
                                         @AuthenticationPrincipal Account account) {
        accountService.addReleaseToCollection(account.getId(), releaseService.getRelease(id));
        return "redirect:/releases/" + id;
    }

    @PostMapping("/addToWantlist")
    public String addReleaseToWantlist(@RequestParam("id") long id,
                                       @AuthenticationPrincipal Account account) {
        accountService.addReleaseToWantlist(account.getId(), releaseService.getRelease(id));
        return "redirect:/releases/" + id;
    }

    @PostMapping("/removeFromCollection")
    public String removeReleaseFromCollection(@RequestParam("id") long id,
                                         @AuthenticationPrincipal Account account) {
        accountService.removeReleaseFromCollection(account.getId(), releaseService.getRelease(id));
        return "redirect:/releases/" + id;
    }

    @PostMapping("/removeFromWantlist")
    public String removeReleaseFromWantlist(@RequestParam("id") long id,
                                       @AuthenticationPrincipal Account account) {
        accountService.removeReleaseFromWantlist(account.getId(), releaseService.getRelease(id));
        return "redirect:/releases/" + id;
    }
}