package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.DepositCheckForm;
import com.team11.mutualfund.form.RequestCheckForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.Pair;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.CUSTOMERNOTLOGIN;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;


@Controller
public class CheckController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/deposit_check")
    public String depositCheck(HttpServletRequest request, RedirectAttributes ra, Model model) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        DepositCheckForm depositCheckForm = new DepositCheckForm();
        model.addAttribute("depositCheckForm", depositCheckForm);
        return "deposit_check";
    }

    @RequestMapping(value = "/deposit_check", method = RequestMethod.POST)
    public String depositCheck(HttpServletRequest request, RedirectAttributes ra, Model model,
                               @Valid DepositCheckForm depositCheckForm, BindingResult result) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "deposit_check";
        try {
            transactionService.depositCheck(
                depositCheckForm.getCustomerId(), depositCheckForm.getAmount());
            return "success";
        } catch (RollbackException e) {
            result.rejectValue("customerId", "0", e.getMessage());
            return "deposit_check";
        }
        //transactionService.executeDepositCheck(depositCheckForm.getCustomerId(), LocalDate.now());
    }

    @RequestMapping("/request_check")
    public String requestCheck(HttpServletRequest request, RedirectAttributes ra, Model model) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = customerService.getCustomerById(user.getId());
        RequestCheckForm requestCheckForm = new RequestCheckForm();
        requestCheckForm.setCustomerId(customer.getId());
        double available = customer.getCash() - customer.getPendingCashDecrease();
        requestCheckForm.setAvailable(Math.round(available * 100) / 100d);
        model.addAttribute("requestCheckForm", requestCheckForm);
        return "request_check";
    }

    @RequestMapping(value = "/request_check", method = RequestMethod.POST)
    public String requestCheck(HttpServletRequest request, RedirectAttributes ra, Model model,
                               @Valid RequestCheckForm requestCheckForm, BindingResult result) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        if (result.hasErrors())
            return "request_check";
        result.addAllErrors(requestCheckForm.getValidationErrors());
        if (result.hasErrors())
            return "request_check";
        User user = (User) request.getSession().getAttribute("user");
        try {
            transactionService.requestCheck(
                requestCheckForm.getCustomerId(), requestCheckForm.getAmount());
            return "success";
        } catch (RollbackException e) {
            String message = e.getMessage();
            if (message.startsWith("customer"))
                result.rejectValue("customerId", "0", message);
            else
                result.rejectValue("amount", "1", message);
            return "request_check";
        }
        //transactionService.executeRequestCheck(requestCheckForm.getCustomerId(), LocalDate.now());
    }

}
