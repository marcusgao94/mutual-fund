package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.DepositCheckForm;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;


@Controller
public class DepostiCheckController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/deposit_check")
    public String depositCheck(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        DepositCheckForm depositCheckForm = new DepositCheckForm();
        model.addAttribute("depositCheckForm", depositCheckForm);
        return "deposit_check";
    }

    @RequestMapping(value = "/deposit_check", method = RequestMethod.POST)
    public String depositCheck(HttpServletRequest request, Model model,
                               @Valid DepositCheckForm depositCheckForm, BindingResult result,
                               RedirectAttributes ra) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "deposit_check";
        Pair pair = transactionService.depositCheck(
                depositCheckForm.getCustomerId(), depositCheckForm.getAmount());
        if (!pair.getRes()) {
            FieldError customerIdNotExisterror = new FieldError("depositCheckForm", "customerId", pair.getMessage());
            result.addError(customerIdNotExisterror);
            return "deposit_check";
        }

        // transactionService.executeDepositCheck(depositCheckForm.getCustomerId(), LocalDate.now());
        return "success";
    }
}
