package com.team11.mutualfund.controller;

import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.team11.mutualfund.model.FundPriceHistory;
import com.team11.mutualfund.utils.TransitionFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team11.mutualfund.form.TransitionForm;
import com.team11.mutualfund.service.FundService;


@Controller
public class TransitionController {

    @Autowired
    private FundService fundService;


    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/transitionday", method = RequestMethod.GET)
    public String transitionDay(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        TransitionForm transitionForm = new TransitionForm();
        LocalDate date = fundService.getLastTransitionDay();
        if (date == null)
            model.addAttribute("lastTransitionday", "no last transition day");
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = formatter.format(date);
            model.addAttribute("lastTransitionday", formattedDate);
        }
        List<TransitionFund> fundList = fundService.listFundPrice();
        transitionForm.setFundList(fundList);
        model.addAttribute("transitionForm", transitionForm);
        return "transitionday";
    }

    @RequestMapping(value = "/transitionday", method = RequestMethod.POST)
    public String createFund(HttpServletRequest request, Model model,
                               @Valid TransitionForm transitionForm, BindingResult result, RedirectAttributes ra) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "transitionday";
        /*
        Transition transition = new Transition(); //没写完
        model.addAttribute("success", "fund " + transition.getFundId() + " updated successfully");
        */

        return "success";
    }

}