package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recordstore.entity.Record;
import recordstore.service.RecordService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/")
public class AdminRecordController {

    private final RecordService service;

    public AdminRecordController(RecordService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllRecordsForEdit(Model model){
        List<Record> records = service.getAllRecords();
        model.addAttribute("records", records);
        return "admin/index";
    }

    @GetMapping("add")
    public String addRecord(){
        return "admin/add";
    }

    @PostMapping("add")
    public String saveRecord(Record record){
        service.saveRecord(record);
        return "redirect:/admin/";
    }

    @GetMapping("{id}")
    public ModelAndView editRecord(@PathVariable long id){
        ModelAndView view = new ModelAndView("admin/edit");
        Record record = service.getRecord(id);
        view.getModelMap().addAttribute("record", record);
        return view;
    }

    @PostMapping("update")
    public ModelAndView updateRecord(@ModelAttribute("record") @Valid Record record, BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("admin/edit", "record", record);
        }
        service.updateRecord(record);
        return new ModelAndView("redirect:/admin/");
    }

    @PostMapping("delete/{id}")
    public String delete(@RequestParam long id){
        service.deleteRecord(id);
        return "redirect:/admin/";
    }
}
