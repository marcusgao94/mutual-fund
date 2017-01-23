package com.team11.mutualfund.controller;

import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.utils.CustomerForm;
import com.team11.mutualfund.utils.EmployeeForm;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class LoginController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    // employee

    @RequestMapping(value = "/employee_login", method = RequestMethod.GET)
    public String employeeLogin(HttpServletRequest request, Model model) {
        String message = (String) request.getParameter("error");
        if (message != null)
            model.addAttribute("error", "You have not login as an employee");
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);
        return "employee_login";
    }

    @RequestMapping(value = "/employee_login", method = RequestMethod.POST)
    public String employeeLogin(HttpServletRequest request, Model model,
                                @Valid EmployeeForm employeeForm, BindingResult result) {
        if (result.hasErrors())
            return "employee_login";
        Employee e = employeeService.getEmployeeByUserName(employeeForm.getUserName());
        if (e == null) {
            FieldError userNameExistError = new FieldError("employeeForm", "userName",
                    messageSource.getMessage("noUserName", new String[]{employeeForm.getUserName()}, Locale.getDefault()));
            result.addError(userNameExistError);
            return "employee_login";
        }
        if (!e.getPassword().equals(employeeForm.getPassword())) {
            FieldError wrongPasswordError = new FieldError("employeeForm", "password",
                    messageSource.getMessage("wrongPassword", null, Locale.getDefault()));
            result.addError(wrongPasswordError);
            return "employee_login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", new User(null, e.getUserName(), 1));
        return "redirect:/home";
    }

    // customer

    @RequestMapping(value = "/customer_login", method = RequestMethod.GET)
    public String customerLogin(HttpServletRequest request, Model model) {
        String message = (String) request.getParameter("error");
        if (message != null)
            model.addAttribute("error", "You have not login");
        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm", customerForm);
        return "customer_login";
    }

    @RequestMapping(value = "/customer_login", method = RequestMethod.POST)
    public String loginCustomer(HttpServletRequest request, Model model,
                                @Valid CustomerForm customerForm, BindingResult result) {
        if (result.hasErrors())
            return "customer_login";
        Customer c = customerService.findCustomerByUserName(customerForm.getUserName());
        if (c == null) {
            FieldError userNameExistError = new FieldError("customerForm", "userName",
                    messageSource.getMessage("noUserName", new String[]{customerForm.getUserName()}, Locale.getDefault()));
            result.addError(userNameExistError);
            return "customer_login";
        }
        if (!c.getPassword().equals(customerForm.getPassword())) {
            FieldError wrongPasswordError = new FieldError("customerForm", "password",
                    messageSource.getMessage("wrongPassword", null, Locale.getDefault()));
            result.addError(wrongPasswordError);
            return "customer_login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", new User(c.getId(), c.getUserName(), 0));
        return "redirect:/home";
    }

}
