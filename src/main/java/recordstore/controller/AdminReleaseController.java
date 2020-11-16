package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Release;
import recordstore.service.ReleaseService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/releases/")
public class AdminReleaseController {

    private final ReleaseService service;

    public AdminReleaseController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllReleases(Model model){
        List<Release> releases = service.getAllReleases();
        model.addAttribute("releases", releases);
        return "admin/releases/index";
    }

    @GetMapping("/add")
    public String showAddForm(){
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRecord(@Valid Release release, BindingResult result){
        if (result.hasErrors()) {
            return "admin/releases/add";
        }
        service.saveRelease(release);
        return "redirect:/admin/releases/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        Release release = service.getRelease(id);
        model.addAttribute("release", release);
        return "admin/releases/edit";
    }

    @PostMapping("/update/")
    public String updateRecord(@Valid Release release, BindingResult result){
        if(result.hasErrors()){
            return "admin/releases/edit";
        }
        service.saveRelease(release);
        return "redirect:/admin/releases/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
    service.deleteRelease(id);
    return "redirect:/admin/releases/";
    }
}