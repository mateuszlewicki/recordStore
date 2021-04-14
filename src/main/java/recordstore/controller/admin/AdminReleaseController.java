package recordstore.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.TrackDTO;
import recordstore.DTO.YouTubeVideoDTO;
import recordstore.entity.Release;
import recordstore.enums.Format;
import recordstore.mapper.ReleaseMapper;
import recordstore.service.ReleaseService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/releases/")
public class  AdminReleaseController {

    private final ReleaseMapper releaseMapper;
    private final ReleaseService service;

    public AdminReleaseController(ReleaseMapper releaseMapper, ReleaseService service) {
        this.releaseMapper = releaseMapper;
        this.service = service;
    }

    @GetMapping
    public String showAllReleases(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Release> releases = service.getAllReleases(PageRequest.of(currentPage - 1 ,10, Sort.by("releaseDate").descending()));
        model.addAttribute("releases", releases);
        getPages(model, releases);
        return "admin/releases/index";
    }

    @GetMapping("/{id}")
    public String showReleaseInfo(@PathVariable long id, Model model){
        if (service.isPresent(id)){
            model.addAttribute("release", service.getRelease(id));
        } else {
            model.addAttribute("error", "Release not found");
        }
        return "admin/releases/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("release" , new ReleaseDTO());
        model.addAttribute("formatTypes" , Format.values());
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRelease(@Valid @ModelAttribute("release") ReleaseDTO releaseDTO,
                             BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("formatTypes" , Format.values());
            return "admin/releases/add";
        }
        service.saveRelease(releaseMapper.fromDTO(releaseDTO));
        return "redirect:/admin/releases/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        if (service.isPresent(id)){
            model.addAttribute("release", releaseMapper.toDTO(service.getRelease(id)));
            model.addAttribute("formatTypes" , Format.values());
        } else {
            model.addAttribute("error", "Release is not found");
        }
        return "admin/releases/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRecord(@Valid @ModelAttribute("release") ReleaseDTO releaseDTO,
                               BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            model.addAttribute("formatTypes" , Format.values());
            return "admin/releases/edit";
        }
        service.saveRelease(releaseMapper.fromDTO(releaseDTO));
        return "redirect:/admin/releases/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) throws IOException {
        if (service.isPresent(id)){
            service.deleteRelease(id);
        }
        return "redirect:/admin/releases/";
    }

    @PostMapping("/addTrack")
    public String addTrack(@ModelAttribute("release") ReleaseDTO releaseDTO, Model model) {
        releaseDTO.getTracklist().add(new TrackDTO());
        model.addAttribute("release", releaseDTO);
        return "admin/releases/tracks";
    }

    @PostMapping("/removeTrack")
    public String removeTrack(@ModelAttribute("release") ReleaseDTO releaseDTO,
                              @RequestParam("removeIndex") int index, Model model) {
        releaseDTO.getTracklist().remove(releaseDTO.getTracklist().get(index));
        model.addAttribute("release", releaseDTO);
        return "admin/releases/tracks";
    }

    @PostMapping("/addVideo")
    public String addVideo(@ModelAttribute("release") ReleaseDTO releaseDTO, Model model) {
        releaseDTO.getPlaylist().add(new YouTubeVideoDTO());
        model.addAttribute("release", releaseDTO);
        return "admin/releases/video";
    }

    @PostMapping("/removeVideo")
    public String removeVideo(@ModelAttribute("release") ReleaseDTO releaseDTO,
                              @RequestParam("removeIndex") int index, Model model) {
        releaseDTO.getPlaylist().remove(releaseDTO.getPlaylist().get(index));
        model.addAttribute("release", releaseDTO);
        return "admin/releases/video";
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