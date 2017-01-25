package com.team11.mutualfund.controller;

import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.team11.mutualfund.model.Transition;
import com.team11.mutualfund.service.FundService;


@Controller
public class TransitionController {

    @Autowired
    private FundService TransitionService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/transition_day", method = RequestMethod.GET)
    public String transitionDay(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {

        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        TransitionForm transitionForm = new TransitionForm();
        model.addAttribute("createFundForm", transitionForm);
        return "transition_day";
    }

    @RequestMapping(value = "/transition_day", method = RequestMethod.POST)
    public String createFund(HttpServletRequest request, Model model,
                               @Valid TransitionForm transitionForm, BindingResult result, RedirectAttributes ra) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "transition_day";
        if (transitionForm.getDate().isEmpty()) {
            FieldError emptyDateError = new FieldError("transitionForm", "date",
                    messageSource.getMessage("emptyDate", null, Locale.getDefault()));
            result.addError(emptyDateError);
            return "transition_day";
        }
        if (transitionForm.getPrice().isEmpty()) {
            FieldError emptyPriceError = new FieldError("transitionForm", "price",
                    messageSource.getMessage("emptyPrice", null, Locale.getDefault()));
            result.addError(emptyPriceError);
            return "transition_day";
        }

        Transition transition = new Transition(); //没写完
        model.addAttribute("success", "fund " + transition.getFundId() + " updated successfully");
        return "success";
    }

}