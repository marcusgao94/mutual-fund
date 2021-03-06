package com.team11.mutualfund.controller;

import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.form.ChangePasswordForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.utils.Constant.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.RollbackException;
import javax.validation.Valid;

@Controller
@SessionAttributes("customerList")
public class ChangePasswordController {


    @Autowired
    MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;


    public boolean checkEmployee(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) return false;
        User user = (User) session.getAttribute("user");
        return (user.getType() == 1);
    }

    //employee

    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.GET)
    public String employChangePassword(HttpServletRequest request, Model model,
                                       RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        User user = (User) request.getSession().getAttribute("user");
        changePasswordForm.setUserName(user.getUserName());
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "employee_changepassword";
    }

    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
    public String employeeChangePassword(HttpServletRequest request, Model model,
                                         @Valid ChangePasswordForm changePasswordForm, BindingResult result,
                                         RedirectAttributes redirectAttributes) {

        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

        result.addAllErrors(changePasswordForm.getValidationErrors());
        if (result.hasErrors())
            return "employee_changepassword";

        // get the user from session attribute
        HttpSession session = request.getSession();
        String name = ((User) session.getAttribute("user")).getUserName();

        // confirm and update the password
        String originPassword = changePasswordForm.getOriginPassword();
        String newPassword = changePasswordForm.getNewPassword();
        try {
            employeeService.updatePassword(name, originPassword, newPassword);
        } catch (RollbackException e) {
            result.rejectValue("originPassword", "", e.getMessage());
            return "employee_changepassword";
        }

        model.addAttribute("success", "Employee " + name + " update password successfully");
        return "success";
    }


    //customer update his own password
    @RequestMapping(value = "/customer_changepassword", method = RequestMethod.GET)
    public String customerChangePassword(HttpServletRequest request, Model model,
                                         RedirectAttributes redirectAttributes) {
        // Check whether the customer has logged in.
        if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        User user = (User) request.getSession().getAttribute("user");
        changePasswordForm.setUserName(user.getUserName());
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "customer_changepassword";
    }

    @RequestMapping(value = "/customer_changepassword", method = RequestMethod.POST)
    public String customerChangePassword(HttpServletRequest request, Model model,
                                         @Valid ChangePasswordForm changePasswordForm, BindingResult result,
                                         RedirectAttributes redirectAttributes) {
        if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
        result.addAllErrors(changePasswordForm.getValidationErrors());
        if (result.hasErrors())
            return "customer_changepassword";
        // get the user id from session attribute
        HttpSession session = request.getSession();
        Long cid = ((User) session.getAttribute("user")).getId();
        // confirm and update the password
        String originPassword = changePasswordForm.getOriginPassword();
        String newPassword = changePasswordForm.getNewPassword();
        try {
            customerService.updatePassword(cid, originPassword, newPassword);
        } catch (RollbackException e) {
            result.rejectValue("originPassword", "", e.getMessage());
            return "customer_changepassword";
        }
        model.addAttribute("success", "Customer " + ((User) session.getAttribute("user")).getUserName() + " update password successfully");
        return "success";
    }

    //employee update customer's password
    @RequestMapping(value = "/employee_changecuspassword/{userName}", method = RequestMethod.GET)
    public String employeeChangeCusPassword(HttpServletRequest request, Model model,
                                            RedirectAttributes redirectAttributes,
                                            @PathVariable String userName) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        Customer customer = customerService.getCustomerByUserName(userName);
        if (customer == null)
            return "redirect:/employee_searchcustomer";
        ChangePasswordForm cpf = new ChangePasswordForm();
        cpf.setUserName(userName);
        cpf.setOriginPassword(customer.getPassword());
        model.addAttribute("changePasswordForm", cpf);
        return "employee_changecuspassword";
    }


    @RequestMapping(value = "/employee_changecuspassword/{un}", method = RequestMethod.POST)
    public String employeeChangeCusPassword(HttpServletRequest request, Model model,
                                            @PathVariable String un,
                                            @Valid ChangePasswordForm changePasswordForm, BindingResult result,
                                            String fast, RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
        // get the error from the form validation
        result.addAllErrors(changePasswordForm.getValidationErrors());
        if (result.hasErrors())
            return fast == null ? "employee_changecuspassword" : "employee_changecuspassword_fast";

        String userName = changePasswordForm.getUserName();
        String originPassword = changePasswordForm.getOriginPassword();
        String newPassword = changePasswordForm.getNewPassword();

        try {
            customerService.updatePassword(changePasswordForm.getUserName(), originPassword, newPassword);
        } catch (RollbackException e) {
            String message = e.getMessage();
            if (message.startsWith("Customer"))
                result.rejectValue("userName", "", message);
            else if (message.startsWith("Password"))
                result.rejectValue("originPassword", "", message);
            else
                result.rejectValue("originPassword", "", WRONGPASSWORD);
            return fast == null ? "employee_changecuspassword" : "employee_changecuspassword_fast";
        }
        model.addAttribute("success", "Customer " + userName + " password has been updated successfully");
        return "success";
    }

}
