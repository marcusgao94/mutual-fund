package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.*;
import com.team11.mutualfund.model.FundPriceHistory;
import com.team11.mutualfund.service.FundService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDate;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.*;


@Controller
public class ResearchFundController {

    @Autowired
    private FundService fundService;

    @RequestMapping("/customer_researchfund")
    public String researchFund(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }

        ResearchFundForm researchFundForm = new ResearchFundForm();
        model.addAttribute("researchFundForm", researchFundForm);
        return "customer_researchfund";
    }


    @RequestMapping(value = "/cutomer_researchfund", method = RequestMethod.POST)
    public String researchFund(HttpServletRequest request, Model model,
                               @Valid ResearchFundForm researchFundForm, BindingResult result,
                               RedirectAttributes ra) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        if (result.hasErrors())
            return "customer_researchfund";
        FundPriceHistory fundPriceHistory = fundService.getFundPriceHistoryByTicker(researchFundForm.getTicker());
        /*
        if (!pair.getRes()) {
            FieldError fundIdNotExisterror = new FieldError("researchFundForm", "fundId", pair.getMessage());
            result.addError(fundIdNotExisterror);
            return "research_fund";
        } */
        HttpSession session = request.getSession();
        session.setAttribute("fundPriceHistory", fundPriceHistory);
 
        // transactionService.executeDepositCheck(depositCheckForm.getCustomerId(), LocalDate.now());
        return "customer_researchfund"; //?
    }
}
