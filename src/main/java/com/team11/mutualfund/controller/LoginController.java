package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.LoginForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

import static com.team11.mutualfund.utils.Constant.NOUSERNAME;
import static com.team11.mutualfund.utils.Constant.WRONGPASSWORD;

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;


    public static boolean checkEmployee(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null && user.getType() == 1;
    }

    public static boolean checkCustomer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null && user.getType() == 0;
    }

    // employee

    @RequestMapping(value = "/employee_login", method = RequestMethod.GET)
    public String employeeLogin(HttpServletRequest request, Model model,
                                @ModelAttribute("loginError") String lem) {
        if (!lem.isEmpty()) {
            model.addAttribute("error", lem);
        }

        LoginForm LoginForm = new LoginForm();
        model.addAttribute("loginForm", LoginForm);
        return "employee_login";
    }

    @RequestMapping(value = "/employee_login", method = RequestMethod.POST)
    public String employeeLogin(HttpServletRequest request, Model model,
                                @Valid LoginForm loginForm, BindingResult result) {
        if (result.hasErrors())
            return "employee_login";
        Employee e = employeeService.getEmployeeByUserName(loginForm.getUserName());
        if (e == null) {
            FieldError userNameExistError = new FieldError("loginForm", "userName", NOUSERNAME);
            result.addError(userNameExistError);
            return "employee_login";
        }
        if (!e.getPassword().equals(loginForm.getPassword())) {
            FieldError wrongPasswordError = new FieldError("loginForm", "password", WRONGPASSWORD);
            result.addError(wrongPasswordError);
            return "employee_login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", new User(null, e.getUserName(), 1));
        return "redirect:/home";
    }

    // customer

    @RequestMapping(value = "/customer_login" , method = RequestMethod.GET)
    public String customerLogin(HttpServletRequest request, Model model,
                                @ModelAttribute("loginError") String lem) {
        if (!lem.isEmpty()) {
            model.addAttribute("error", lem);
        }
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "customer_login";
    }

    @RequestMapping(value = "/customer_login", method = RequestMethod.POST)
    public String loginCustomer(HttpServletRequest request, Model model,
                                @Valid LoginForm loginForm, BindingResult result) {
        if (result.hasErrors())
            return "customer_login";
        Customer c = customerService.getCustomerByUserName(loginForm.getUserName());
        if (c == null) {
            FieldError userNameExistError = new FieldError("loginForm", "userName", NOUSERNAME);
            result.addError(userNameExistError);
            return "customer_login";
        }
        if (!c.getPassword().equals(loginForm.getPassword())) {
            FieldError wrongPasswordError = new FieldError("loginForm", "password", WRONGPASSWORD);
            result.addError(wrongPasswordError);
            return "customer_login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", new User(c.getId(), c.getUserName(), 0));
        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/home";
    }

}
