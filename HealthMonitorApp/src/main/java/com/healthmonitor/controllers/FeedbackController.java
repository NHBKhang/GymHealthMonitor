package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Feedback;
import com.healthmonitor.repositories.PackageRepository;
import com.healthmonitor.services.FeedbackService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String feedback(Model model, @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = PackageRepository.getPageSize();

            long totalFeedbackList = feedbackService.countFeedbackList(params);
            int totalPages = (int) Math.ceil((double) totalFeedbackList / pageSize);
            List<Feedback> feedbackList = feedbackService.getFeedbackList(params);

            model.addAttribute("feedbackList", feedbackList);
            model.addAttribute("totalFeedbackList", totalFeedbackList);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải danh sách thành viên!");
        }
        return "feedback";
    }
    
}
