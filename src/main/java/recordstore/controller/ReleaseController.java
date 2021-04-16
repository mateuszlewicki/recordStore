package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.entity.YouTubeVideo;
import recordstore.service.ReleaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService service;

    public ReleaseController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllReleases(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Release> releases = service.getAllReleases(PageRequest.of(currentPage - 1 ,30, Sort.by("releaseDate").descending()));
        model.addAttribute("releases", releases);
        getPages(model, releases.getTotalPages());
        return "client/releases/index";
    }

    @GetMapping("/genre/{id}")
    public String showAllReleasesByGenre(Model model,
                                         @PathVariable long id,
                                         @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Release> releases = service.getReleasesByGenre(id, PageRequest.of(currentPage - 1 ,30 , Sort.by("releaseDate").descending()));
        if (!releases.isEmpty()) {
            model.addAttribute("releases", releases);
            model.addAttribute("id", id);
            getPages(model, releases.getTotalPages());
        } else {
            model.addAttribute("error", "Releases not found");
        }
        return "client/releases/list";
    }

    @GetMapping("/{id}")
    public String showReleaseInfo(@PathVariable long id, Model model, @AuthenticationPrincipal Account account){
        if (service.isPresent(id)){
            Release release = service.getRelease(id);
            model.addAttribute("release", release);
            model.addAttribute("videoIds", getVideoIds(release));
            model.addAttribute("inCollection", release.getCollections().contains(account));
            model.addAttribute("inWantlist", release.getWantlists().contains(account));
        } else {
            model.addAttribute("error", "Release not found");
        }
        return "client/releases/view";
    }

    private void getPages(Model model, int pages) {
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    private String getVideoIds(Release release) {
        return release.getPlaylist().stream().map(YouTubeVideo::getVideoId).collect(Collectors.joining(","));
    }
}