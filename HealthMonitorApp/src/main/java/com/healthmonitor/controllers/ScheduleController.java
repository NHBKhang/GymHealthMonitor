package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Schedule;
import com.healthmonitor.repositories.ScheduleRepository;
import com.healthmonitor.services.ScheduleService;
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
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public String schedule(Model model, @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = ScheduleRepository.getPageSize();

            long totalSchedules = scheduleService.countSchedules(params);
            int totalPages = (int) Math.ceil((double) totalSchedules / pageSize);
            List<Schedule> schedules = scheduleService.getSchedules(params);

            model.addAttribute("schedules", schedules);
            model.addAttribute("totalSchedules", totalSchedules);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải danh sách thành viên!");
        }
        return "schedules";
    }

    @GetMapping("/add")
    public String showAddScheduleForm(Model model, RedirectAttributes redirectAttributes) {
        try {
            Schedule s = new Schedule();
            String generatedCode = scheduleService.generateNextCode();
            s.setCode(generatedCode);

            model.addAttribute("schedule", s);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
        }
        return "schedule_form";
    }

    @PostMapping("/save")
    public String saveSchedule(@Valid @ModelAttribute("schedule") Schedule schedule, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("schedule", schedule);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (schedule.getId() == null) {
                return "redirect:/schedules/add";
            } else {
                return "redirect:/schedules/edit/" + schedule.getId();
            }
        }

        try {
            Schedule s;
            if (schedule.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
                s = scheduleService.createOrUpdateSchedule(schedule);
            } else {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
                s = scheduleService.createOrUpdateSchedule(schedule);
            }
            return "redirect:/schedules/edit/" + s.getId();
        } catch (Exception e) {
            System.out.print(e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/schedules/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditScheduleForm(@PathVariable(value = "id") int id, Model model) {
        Schedule s = scheduleService.getScheduleById(id);
        model.addAttribute("schedule", s);
        return "schedule_form";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSchedule(@PathVariable(value = "id") int id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lỗi khi xóa lịch tập."));
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteSchedules(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Không có ID nào được chọn để xóa."));
        }

        scheduleService.deleteSchedules(ids);
        return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
    }

}
