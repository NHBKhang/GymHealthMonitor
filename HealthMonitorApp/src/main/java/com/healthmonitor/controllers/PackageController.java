package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.repositories.PackageRepository;
import com.healthmonitor.services.PackageService;
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
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping
    public String packages(Model model, @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = PackageRepository.getPageSize();

            long totalPackages = packageService.countPackages(params);
            int totalPages = (int) Math.ceil((double) totalPackages / pageSize);
            List<Package> packages = packageService.getPackages(params);

            model.addAttribute("packages", packages);
            model.addAttribute("totalPackages", totalPackages);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải danh sách thành viên!");
        }
        return "packages";
    }

    @GetMapping("/add")
    public String showAddPackageForm(Model model) {
        Package p = new Package();
        String generatedCode = packageService.generateNextCode();
        p.setCode(generatedCode);

        model.addAttribute("package", p);
        return "package_form";
    }

    @PostMapping("/save")
    public String savePackage(@Valid @ModelAttribute("package") Package pkg, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("package", pkg);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (pkg.getId() == null) {
                return "redirect:/packages/add";
            } else {
                return "redirect:/packages/edit/" + pkg.getId();
            }
        }

        try {
            Package p;
            if (pkg.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
                p = packageService.createOrUpdatePackage(pkg);
            } else {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
                p = packageService.createOrUpdatePackage(pkg);
            }
            return "redirect:/packages/edit/" + p.getId();
        } catch (Exception e) {
            System.out.print(e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/packages/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPackageForm(@PathVariable(value = "id") int id, Model model) {
        Package pkg = packageService.getPackageById(id);
        model.addAttribute("package", pkg);
        return "package_form";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePackage(@PathVariable(value = "id") int id) {
        try {
            packageService.deletePackage(id);
            return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lỗi khi xóa gói tập."));
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deletePackages(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Không có ID nào được chọn để xóa."));
        }

        packageService.deletePackages(ids);
        return ResponseEntity.ok().body(Map.of("message", "Xóa gói tập thành công!"));
    }

}
