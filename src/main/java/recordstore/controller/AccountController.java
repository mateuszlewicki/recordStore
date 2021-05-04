package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.error.AccountNotFoundException;
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
        Account account = accountService.getAccount(id);
        model.addAttribute("account", account);
        model.addAttribute("isAuth", account.equals(authAccount));
        return "client/user/view";
    }

    @GetMapping("/{id}/collection")
    public String showAccountCollection(Model model,
                                        @PathVariable long id,
                                        @RequestParam("page") Optional<Integer> page,
                                        @AuthenticationPrincipal Account authAccount) {
        int currentPage = page.orElse(1);
        Account account = accountService.getAccount(id);
        Page<Release> releases = releaseService.getCollectionByAccount(id, PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
        model.addAttribute("account", account);
        model.addAttribute("isAuth", account.equals(authAccount));
        if (!releases.isEmpty()) {
            model.addAttribute("releases", releases);
            getPages(model, releases.getTotalPages());
        }
        return "client/user/view_collection";
    }

    @GetMapping("/{id}/wantlist")
    public String showAccountWantlist(Model model,
                                        @PathVariable long id,
                                        @RequestParam("page") Optional<Integer> page,
                                        @AuthenticationPrincipal Account authAccount) {
        int currentPage = page.orElse(1);
        Account account = accountService.getAccount(id);
        Page<Release> releases = releaseService.getWantListByAccount(id, PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
        model.addAttribute("account", account);
        model.addAttribute("isAuth", account.equals(authAccount));
        if (!releases.isEmpty()) {
            model.addAttribute("releases", releases);
            getPages(model, releases.getTotalPages());
        }
        return "client/user/view_wantlist";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ModelAndView accountNotFoundHandler(AccountNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("/errorPages/pageNotFound");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }

    private void getPages(Model model, int pages) {
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}