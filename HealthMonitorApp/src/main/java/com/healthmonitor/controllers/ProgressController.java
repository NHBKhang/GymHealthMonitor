package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Progress;
import com.healthmonitor.repositories.ProgressRepository;
import com.healthmonitor.services.ProgressService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping
    public String progress(Model model, @RequestParam Map<String, String> params) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
        int pageSize = ProgressRepository.getPageSize();

        long totalProgressList = progressService.countProgress(params);
        int totalPages = (int) Math.ceil((double) totalProgressList / pageSize);
        List<Progress> progressList = progressService.getProgress(params);
        
        model.addAttribute("progressList", progressList);
        model.addAttribute("totalprogressList", totalProgressList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "progress";
    }

    @GetMapping("/add")
    public String showAddProgressForm(Model model) {
        Progress p = new Progress();
        String generatedCode = progressService.generateNextCode();
        p.setCode(generatedCode);
        
        model.addAttribute("progress", p);
        return "progress_form";
    }

    @PostMapping("/save")
    public String saveProgress(@Valid @ModelAttribute("progress") Progress progress, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("package", progress);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (progress.getId() == null) {
                return "redirect:/progress/add";
            } else {
                return "redirect:/progress/edit/" + progress.getId();
            }
        }

        try {
            Progress p;
            if (progress.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
                p = progressService.createOrUpdateProgress(progress);
            } else {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
                p = progressService.createOrUpdateProgress(progress);
            }
            return "redirect:/packages/edit/" + progress.getId();
        } catch (Exception e) {
            System.out.print(e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/packages/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditProgressForm(@PathVariable(value = "id") int id, Model model) {
        Progress p = progressService.getProgressById(id);
        model.addAttribute("progress", p);
        return "progress_form";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProgress(@PathVariable(value = "id") int id) {
        try {
            progressService.deleteProgress(id);
            return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lỗi khi xóa gói tập."));
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteProgressList(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Không có ID nào được chọn để xóa."));
        }

        progressService.deleteProgressList(ids);
        return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
    }
    
}
