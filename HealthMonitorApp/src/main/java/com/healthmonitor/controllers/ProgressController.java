package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Progress;
import com.healthmonitor.repositories.impl.ProgressRepositoryImpl;
import com.healthmonitor.services.ProgressService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping
    public String users(Model model, @RequestParam Map<String, String> params) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
        int pageSize = ProgressRepositoryImpl.getPageSize();

        long totalUsers = progressService.countProgress(params);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        List<Progress> progress = progressService.getProgress(params);
        
        model.addAttribute("progress", progress);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "progress";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new Progress());
        return "progress_form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("progress") Progress progress, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("progress", progress);
            return "user_form";
        }

        Progress p = progressService.createOrUpdateProgress(progress);
        return "redirect:/progress/edit/" + p.getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable(value = "id") int id, Model model) {
        Progress p = progressService.getProgressById(id);
        model.addAttribute("progress", p);
        return "progress_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) {
        progressService.deleteProgress(id);
        return "redirect:/users";
    }
    
}
