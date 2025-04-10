package com.healthmonitor.controllers;

import com.healthmonitor.pojo.User;
import com.healthmonitor.repositories.UserRepository;
import com.healthmonitor.services.UserService;
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
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String users(Model model, @RequestParam Map<String, String> params,  
            RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = UserRepository.getPageSize();

            long totalUsers = userService.countUsers(params);
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
            List<User> users = userService.getUsers(params);

            model.addAttribute("users", users);
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải danh sách thành viên!");
        }
        return "users";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (user.getId() == null) {
                return "redirect:/users/add";
            } else {
                return "redirect:/users/edit/" + user.getId();
            }
        }

        try {
            User u;
            if (user.getId() == null) {
                u = userService.createOrUpdateUser(user);
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
            } else {
                u = userService.createOrUpdateUser(user);
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            }
            return "redirect:/users/edit/" + u.getId();
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Tên người dùng đã tồn tại!");
            return "redirect:/users/add";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/users/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable(value = "id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_form";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body(Map.of("message", "Xóa thành viên thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lỗi khi xóa thành viên."));
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteUsers(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Không có ID nào được chọn để xóa."));
        }

        userService.deleteUsers(ids);
        return ResponseEntity.ok().body(Map.of("message", "Xóa thành viên thành công!"));
    }
}
