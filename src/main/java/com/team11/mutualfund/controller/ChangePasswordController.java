package com.team11.mutualfund.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.utils.EmployeeForm;
import com.team11.mutualfund.utils.User;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


public class ChangePasswordController {
	@Autowired
    MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;
    
    
    //employ
    
    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.GET)
    public String employChangePassword(HttpServletRequest request, Model model) {
    		String message = (String)request.getParameter("error");
    		if (message != null) {
    			model.addAttribute("error", "You have not login as an employee");
    		}
    		EmployeeForm employeeForm = new EmployeeForm();
    		model.addAttribute("employeeForm", employeeForm);
    		return "employee_changepassword";
    }
    
    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
    public  String employeeChangePassword(HttpServletRequest request, Model model,
    										  @Valid EmployeeForm employeeForm, BindingResult result) {
    		if (result.hasErrors()) {
    			return "employee_changepassword";
    		}
    		/**
    		 * get the user from session attribute
    		 */
    		HttpSession session = request.getSession();
    		Employee e = (Employee) session.getAttribute("user");
    		
    		/**
    		 * confirm the old password and update the new password
    		 */
    		if (!e.getPassword().equals(employeeForm.getPassword())) {
    			FieldError wrongPasswordError = new FieldError("employeeForm", "password",
    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
    			result.addError(wrongPasswordError);
    			return "employee_changepassword";
    		} 
    			
    			String newPassword = employeeForm.getPassword();
    			String confirmPassword = employeeForm.getConfirmPassword();
    			
    			if (!newPassword.equals(confirmPassword)) {
    				FieldError PasswordNotMatchError = new FieldError("employeeForm", "confirm_password",
    					messageSource.getMessage("PasswordNotMatchError", null, Locale.getDefault()));
    				result.addError(PasswordNotMatchError);
    			}
    			
    			e = employeeService.updatePassword(e, confirmPassword);
    			session.setAttribute("user", new User(null, e.getUserName(), 1));
    			return "customer_viewaccount";
    		
    }
    
}
