package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.repositories.impl.SubscriptionRepositoryImpl;
import com.healthmonitor.services.SubscriptionService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public String subscriptions(Model model, @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = SubscriptionRepositoryImpl.getPageSize();

            long totalSubscriptions = subscriptionService.countSubscriptions(params);
            int totalPages = (int) Math.ceil((double) totalSubscriptions / pageSize);
            List<Subscription> subscriptions = subscriptionService.getSubscriptions(params);

            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("totalSubscriptions", totalSubscriptions);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải đăng ký gói tập!");
        }
        return "subscriptions";
    }

    @GetMapping("/add")
    public String showAddSubscriptionForm(Model model) {
        Subscription s = new Subscription();
        String generatedCode = subscriptionService.generateNextCode();
        s.setCode(generatedCode);

        model.addAttribute("subscription", s);
        return "subscription_form";
    }

    @PostMapping("/save")
    public String saveSubscription(@Valid @ModelAttribute("subscription") Subscription subscription,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("subscription", subscription);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (subscription.getId() == null) {
                return "redirect:/subscriptions/add";
            } else {
                return "redirect:/subscriptions/edit/" + subscription.getId();
            }
        }

        try {
            Subscription s;
            if (subscription.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
                s = subscriptionService.createOrUpdateSubscription(subscription);
            } else {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
                s = subscriptionService.createOrUpdateSubscription(subscription);
            }
            return "redirect:/subscriptions/edit/" + s.getId();
        } catch (Exception e) {
            System.out.print(e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/subscriptions/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditSubscriptionForm(@PathVariable(value = "id") int id, Model model) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        model.addAttribute("subscription", subscription);
        return "subscription_form";
    }

}
