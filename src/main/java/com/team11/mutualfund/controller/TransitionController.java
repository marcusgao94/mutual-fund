package com.team11.mutualfund.controller;

import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.team11.mutualfund.service.TransitionService;
import com.team11.mutualfund.utils.TransitionFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private TransitionService transitionService;


    @RequestMapping(value = "/transitionday", method = RequestMethod.GET)
    public String transitionDay(HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        TransitionForm transitionForm = new TransitionForm();
        Date date = fundService.getLastTransitionDay();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (date == null)
            transitionForm.setLastDate("no last transition day");
        else {
            String formattedDate = df.format(date);
            transitionForm.setLastDate(formattedDate);
        }
        String newDate = df.format(Calendar.getInstance().getTime());
        transitionForm.setNewDate(newDate);
        List<TransitionFund> fundList = fundService.listFundPrice(date);
        transitionForm.setFundList(fundList);
        model.addAttribute("transitionForm", transitionForm);
        return "transitionday";
    }

    @RequestMapping(value = "/transitionday", method = RequestMethod.POST)
    public String transitionDay(HttpServletRequest request, Model model,
                               @Valid TransitionForm transitionForm, BindingResult result, RedirectAttributes ra) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        result.addAllErrors(transitionForm.getValidationErrors());
        if (result.hasErrors()) {
            model.addAttribute("transitionForm", transitionForm);
            return "transitionday";
        }

        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            Date date = df.parse(transitionForm.getNewDate());
            transitionService.transit(date, transitionForm.getFundList());
        } catch (ParseException e) {
            result.rejectValue("", "", "date must be the form MM/dd/yyyy");
            model.addAttribute("transitionForm", transitionForm);
            return "transitionday";
        } catch (RollbackException e) {
            result.rejectValue("", "", e.getMessage());
            model.addAttribute("transitionForm", transitionForm);
            return "transitionday";
        }
        model.addAttribute("success", SETTRANSITIONDAY);
        return "success";
    }

}