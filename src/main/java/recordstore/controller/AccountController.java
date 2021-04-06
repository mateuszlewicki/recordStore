package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.service.AccountService;
import recordstore.service.ReleaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final ReleaseService releaseService;

    public AccountController(AccountService accountService, ReleaseService releaseService) {
        this.accountService = accountService;
        this.releaseService = releaseService;
    }

    @GetMapping("/{id}")
    public String showAccountInfo(@PathVariable long id, Model model, @AuthenticationPrincipal Account authAccount) {
        if (accountService.isPresent(id)) {
            Account account = accountService.getAccount(id);
            model.addAttribute("account", account);
            model.addAttribute("isAuth", isAuth(authAccount, id));

        } else {
            model.addAttribute("error", "Account not found");
        }
        return "client/user/profile";
    }

    @GetMapping("/{id}/collection")
    public String showAccountCollection(Model model,
                                        @PathVariable long id,
                                        @RequestParam("page") Optional<Integer> page,
                                        @AuthenticationPrincipal Account authAccount) {
        if (accountService.isPresent(id)) {
            int currentPage = page.orElse(1);
            Page<Release> releases = releaseService.getCollectionByAccount(id, PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
            model.addAttribute("account", accountService.getAccount(id));
            model.addAttribute("isAuth", isAuth(authAccount, id));
            if (!releases.isEmpty()){
                model.addAttribute("releases", releases);
                model.addAttribute("q", "collection");
                getPages(model, releases);
            }
        } else {
            model.addAttribute("error", "Account not found");
        }
        return "client/user/profile";
    }

    @GetMapping("/{id}/wantlist")
    public String showAccountWantlist(Model model,
                                        @PathVariable long id,
                                        @RequestParam("page") Optional<Integer> page,
                                        @AuthenticationPrincipal Account authAccount) {
        if (accountService.isPresent(id)) {
            int currentPage = page.orElse(1);
            Page<Release> releases = releaseService.getWantListByAccount(id, PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
            model.addAttribute("account", accountService.getAccount(id));
            model.addAttribute("isAuth", isAuth(authAccount, id));
            if (!releases.isEmpty()){
                model.addAttribute("releases", releases);
                model.addAttribute("q", "wantlist");
                getPages(model, releases);
            }
        } else {
            model.addAttribute("error", "Account not found");
        }
        return "client/user/profile";
    }

    private boolean isAuth(Account authAccount, long id) {
        return authAccount != null && authAccount.getId() == id ? true : false;
    }

    private void getPages(Model model, Page<Release> releases) {
        int pages = releases.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}