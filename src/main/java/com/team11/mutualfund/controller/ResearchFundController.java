package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.DepositCheckForm;
import com.team11.mutualfund.service.FundService;
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
public class ResearchFundController {

    @Autowired
    private FundService fundService;

    @RequestMapping("/research_fund")
    public String depositCheck(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        DepositCheckForm researchFundForm = new ResearchFundForm();
        model.addAttribute("researchFundForm", researchFundForm);
        return "research_fund";
    }

    @RequestMapping(value = "/research_fund", method = RequestMethod.POST)
    public String depositCheck(HttpServletRequest request, Model model,
                               @Valid DepositCheckForm researchFundForm, BindingResult result,
                               RedirectAttributes ra) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "research_fund";
        Pair pair = fundService.getFundPriceHistory(researchFundForm.getTicker());
        if (!pair.getRes()) {
            FieldError fundIdNotExisterror = new FieldError("researchFundForm", "fundId", pair.getMessage());
            result.addError(fundIdNotExisterror);
            return "research_fund";
        }

        // transactionService.executeDepositCheck(depositCheckForm.getCustomerId(), LocalDate.now());
        return "success";
    }
}
