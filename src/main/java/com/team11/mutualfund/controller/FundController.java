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
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.utils.Constant.CUSTOMERNOTLOGIN;

@Controller
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
        BuyFundForm buyFundForm = new BuyFundForm();
        buyFundForm.setAvailable(customer.getCash() - customer.getPendingCashDecrease());
        model.addAttribute("buyFundForm", buyFundForm);
        return "buy_fund";
    }

    @RequestMapping(value = "buy_fund", method = RequestMethod.POST)
    public String buyFund(HttpServletRequest request, RedirectAttributes ra,
                          @Valid BuyFundForm buyFundForm, BindingResult result) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        if (result.hasErrors())
            return "buy_fund";
        User user = (User) request.getSession().getAttribute("user");
        try {
            transactionService.buyFund(user.getId(), buyFundForm.getFundTicker(),
                buyFundForm.getAmount());

            /*
            Fund fund = fundService.getFundByTicker(buyFundForm.getFundTicker());
            fundService.updateFundPrice(fund, LocalDate.now(), 23);
            transactionService.executeBuyFund(buyFundForm.getFundTicker(), LocalDate.now());
            */


            return "success";
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
    }

    @RequestMapping("sell_fund")
    public String sellFund(HttpServletRequest request, RedirectAttributes ra, Model model) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        SellFundForm sellFundForm = new SellFundForm();
        model.addAttribute("sellFundForm", sellFundForm);
        return "sell_fund";
    }

    @RequestMapping(value = "sell_fund", method = RequestMethod.POST)
    public String sellFund(HttpServletRequest request, RedirectAttributes ra,
                           @Valid SellFundForm sellFundForm, BindingResult result) {
        if (!checkCustomer(request)) {
            ra.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        if (result.hasErrors())
            return "sell_fund";
        User user = (User) request.getSession().getAttribute("user");
        try {
            transactionService.sellFund(user.getId(), sellFundForm.getFundTicker(), sellFundForm.getShare());
            return "success";
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
    }
}
