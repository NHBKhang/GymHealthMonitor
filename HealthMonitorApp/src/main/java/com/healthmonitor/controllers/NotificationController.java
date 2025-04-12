package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Notification;
import com.healthmonitor.repositories.NotificationRepository;
import com.healthmonitor.services.NotificationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String notifications(Model model, @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = NotificationRepository.getPageSize();

            long totalNotifications = notificationService.countNotifications(params);
            int totalPages = (int) Math.ceil((double) totalNotifications / pageSize);
            List<Notification> notifications = notificationService.getNotifications(params);

            model.addAttribute("notifications", notifications);
            model.addAttribute("totalNotifications", totalNotifications);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải danh sách thành viên!");
        }
        return "notifications";
    }

    @PostMapping("/seen")
    @ResponseBody
    public ResponseEntity<?> seenNotifications(@RequestBody Map<String, List<Integer>> request,
            RedirectAttributes redirectAttributes) {
        List<Integer> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("info", "Không tìm thấy thông báo nào!"));
        } else {

            return ResponseEntity.ok().body(Map.of("success", "Cập nhật trạng thái thông báo thành công!"));
        }
    }

}
