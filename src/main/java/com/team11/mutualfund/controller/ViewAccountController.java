package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.CustomerRegisterForm;
import com.team11.mutualfund.form.EmployeeRegisterForm;
import com.team11.mutualfund.form.SearchForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.service.FundService;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.Positionvalue;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class ViewAccountController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FundService fundService;

    // employeeViewHistory

    @RequestMapping(value = "/employee_searchcustomer", method = RequestMethod.GET)
    public String employeeViewAccount(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        SearchForm searchForm = new SearchForm();
        model.addAttribute("searchForm", searchForm);

        return "employee_searchcustomer";
    }

    @RequestMapping(value = "/employee_searchcustomer", method = RequestMethod.POST)
    public String employeeViewAccount(HttpServletRequest request, Model model,
                                      RedirectAttributes ra,
                                      @Valid SearchForm searchForm, BindingResult result) {
        if (!checkEmployee(request)) {
            ra.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        if (result.hasErrors())
            return "employee_searchcustomer";
        Customer c = customerService.getCustomerByUserName(searchForm.getUserName());

        if (c == null) {
            result.rejectValue("userName", "", NOUSERNAME);
            return "employee_searchcustomer";
        }
        searchForm.setUserName("");
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("employee_customeraccount", c);
        List<Positionvalue> pv = fundService.listPositionvalueByCustomerId(c.getId());
        model.addAttribute("employee_customerpositionvalue", pv);
        Date d = fundService.getLastTransitionDay();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("date", df.format(d));
        return "employee_viewaccount";
    }

    // customer

    @RequestMapping(value = "customer_viewaccount", method = RequestMethod.GET)
    public String customerViewAccount(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/customer_login";
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Customer c = customerService.getCustomerById(user.getId());
        model.addAttribute("customer_account", c);
        
        Date d = fundService.getLastTransitionDay();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("date", df.format(d)); 
        
        List<Positionvalue> pv = fundService.listPositionvalueByCustomerId(c.getId());
        model.addAttribute("customerPosition", pv);
        return "customer_viewaccount";
    }

}
