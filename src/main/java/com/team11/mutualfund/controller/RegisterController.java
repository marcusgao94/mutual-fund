package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.CustomerRegisterForm;
import com.team11.mutualfund.form.EmployeeRegisterForm;
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
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.DUPLICATEUSERNAME;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

@Controller
public class RegisterController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageSource messageSource;



    // employee

    @RequestMapping(value = "/employee_register", method = RequestMethod.GET)
    public String createEmployee(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        model.addAttribute("employeeRegisterForm", new EmployeeRegisterForm());
        return "employee_register";
    }

    @RequestMapping(value = "/employee_register", method = RequestMethod.POST)
    public String createEmployee(HttpServletRequest request, Model model,
                                 @Valid EmployeeRegisterForm employeeRegisterForm, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        // validate form
        result.addAllErrors(employeeRegisterForm.getValidationErrors());
        if (result.hasErrors()) {
            return "employee_register";
        }
        Employee employee = new Employee(employeeRegisterForm);
        if (!employeeService.createEmployee(employee)) {
            FieldError userNameUniqueError = new FieldError("employeeRegisterForm", "userName", DUPLICATEUSERNAME);
            result.addError(userNameUniqueError);
            return "employee_register";
        }

        model.addAttribute("success", "Employee " + employee.getUserName() + " registered successfully");
        return "success";
    }

    // customer

    @RequestMapping(value = "customer_register", method = RequestMethod.GET)
    public String createCustomer(HttpServletRequest request, Model model,
                                 RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        model.addAttribute("customerRegisterForm", new CustomerRegisterForm());
        return "customer_register";
    }

    @RequestMapping(value = "/customer_register", method = RequestMethod.POST)
    public String createCustomer(HttpServletRequest request, Model model,
                                 @Valid CustomerRegisterForm customerRegisterForm, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        // validate form
        result.addAllErrors(customerRegisterForm.getValidationErrors());
        if (result.hasErrors())
            return "customer_register";
        Customer customer = new Customer(customerRegisterForm);
        if (!customerService.createCustomer(customer)) {
            FieldError userNameUniqueError = new FieldError("customerRegisterForm", "userName", DUPLICATEUSERNAME);
            result.addError(userNameUniqueError);
            return "customer_register";
        }

        model.addAttribute("success", "customer " + customer.getUserName() + " registered successfully");
        return "success";
    }

}
