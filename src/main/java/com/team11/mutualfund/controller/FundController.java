package com.team11.mutualfund.controller;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.form.BuyFundForm;
import com.team11.mutualfund.form.SellFundForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.FundService;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.Pair;
import com.team11.mutualfund.utils.Positionvalue;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.LinkedList;
import java.util.List;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.utils.Constant.CUSTOMERNOTLOGIN;

@Controller
@SessionAttributes(value = {"customerPosition", "fundList"})
public class FundController {

    @Autowired
    private FundService fundService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("buy_fund")
    public String buyFund(HttpServletRequest request, RedirectAttributes ra, Model model) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = customerService.getCustomerById(user.getId());
        List<Positionvalue> pv = fundService.listPositionvalueByCustomerId(customer.getId());
        List<Fund> fundList = fundService.listFund();
        model.addAttribute("customerPosition", pv);
        model.addAttribute("fundList", fundList);
        BuyFundForm buyFundForm = new BuyFundForm();
        buyFundForm.setAvailable(customer.getCash() - customer.getPendingCashDecrease());
        model.addAttribute("buyFundForm", buyFundForm);
        return "buy_fund";
    }

    @RequestMapping(value = "buy_fund", method = RequestMethod.POST)
    public String buyFund(HttpServletRequest request, RedirectAttributes ra,
                          @ModelAttribute("customerPosition") LinkedList<Positionvalue> pv,
                          @ModelAttribute("fundList") LinkedList<Fund> fundList,
                          @Valid BuyFundForm buyFundForm, BindingResult result, Model model) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        result.addAllErrors(buyFundForm.getValidationError());
        if (result.hasErrors())
            return "buy_fund";
        User user = (User) request.getSession().getAttribute("user");
        Customer c = customerService.getCustomerByUserName(user.getUserName());
        try {
            transactionService.buyFund(user.getId(), buyFundForm.getFundTicker(),
                buyFundForm.getAmount());
        } catch (RollbackException e) {
            String message = e.getMessage();
            if (message.startsWith("customer"))
                result.rejectValue("", "0", message);
            else if (message.startsWith("fund"))
                result.rejectValue("fundTicker", "", message);
            else
                result.rejectValue("amount", "", message);
            return "buy_fund";
        }
        model.addAttribute("success", "Transaction has been submitted successfully, please wait for the next transition day!");
        return "success";
    }

    @RequestMapping("sell_fund")
    public String sellFund(HttpServletRequest request, RedirectAttributes ra, Model model) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        User user = (User) request.getSession().getAttribute("user");
        Customer c = customerService.getCustomerByUserName(user.getUserName());
        List<Positionvalue> pv = fundService.listPositionvalueByCustomerId(c.getId());
        model.addAttribute("customerPosition", pv);
        SellFundForm sellFundForm = new SellFundForm();
        model.addAttribute("sellFundForm", sellFundForm);
        return "sell_fund";
    }

    @RequestMapping(value = "sell_fund", method = RequestMethod.POST)
    public String sellFund(HttpServletRequest request, RedirectAttributes ra,
                           @Valid SellFundForm sellFundForm, BindingResult result, Model model,
                           @ModelAttribute("customerPosition") LinkedList<Positionvalue> pv) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        result.addAllErrors(sellFundForm.getValidationError());
        if (result.hasErrors())
            return "sell_fund";
        User user = (User) request.getSession().getAttribute("user");
        try {
            transactionService.sellFund(user.getId(), sellFundForm.getFundTicker(), sellFundForm.getShare());
        } catch (RollbackException e) {
            String message = e.getMessage();
            if (message.startsWith("customer id"))
                result.rejectValue("", "0", message);
            else if (message.startsWith("fund") || message.startsWith("customer does not")) {
                result.rejectValue("fundTicker", "1", message);
            }
            else
                result.rejectValue("share", "2", message);
            return "sell_fund";
        }
        model.addAttribute("success", "Transaction has been submitted successfully, please wait for the next transition day!");
        return "success";
    }
}
