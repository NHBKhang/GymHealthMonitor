package com.healthmonitor.controllers;

import com.healthmonitor.pojo.Payment;
import com.healthmonitor.repositories.impl.PaymentRepositoryImpl;
import com.healthmonitor.services.PaymentService;
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
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String payments(Model model, @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = PaymentRepositoryImpl.getPageSize();

            long totalPayments = paymentService.countPayments(params);
            int totalPages = (int) Math.ceil((double) totalPayments / pageSize);
            List<Payment> payments = paymentService.getPayments(params);

            model.addAttribute("payments", payments);
            model.addAttribute("totalPayments", totalPayments);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải lịch sử giao dịch!");
        }
        return "payments";
    }

    @PostMapping("/save")
    public String savePayment(@Valid @ModelAttribute("payment") Payment payment,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("payment", payment);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống!");
            if (payment.getId() == null) {
                return "redirect:/payments/add";
            } else {
                return "redirect:/payments/edit/" + payment.getId();
            }
        }

        try {
            Payment s;
            if (payment.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Thêm mới thành công!");
                s = paymentService.createOrUpdatePayment(payment);
            } else {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
                s = paymentService.createOrUpdatePayment(payment);
            }
            return "redirect:/payments/edit/" + s.getId();
        } catch (Exception e) {
            System.out.print(e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/payments/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPaymentForm(@PathVariable(value = "id") int id, Model model) {
        Payment payment = paymentService.getPaymentById(id);
        model.addAttribute("payment", payment);
        return "payment_form";
    }

}
