package com.team11.mutualfund.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.form.ChangePasswordForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.utils.EmployeeForm;
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

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
    
    
    public boolean checkEmployee(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) return false;
        User user = (User) session.getAttribute("user");
        return user.isEmployee();
    }
    
    //employee
    
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
    			
    			/*add a success message*/
    			
    			return "employee_viewaccount";
    		
    }
    
    //customer update his own password
    @RequestMapping(value = "/customer_changepassword", method = RequestMethod.GET)
    public String customerChangePassword(HttpServletRequest request, Model model) {
    		String message = (String)request.getParameter("error");
    		if (message != null) {
    			model.addAttribute("error", "You have not login");
    		}
    		EmployeeForm employeeForm = new EmployeeForm();
    		model.addAttribute("customerForm", employeeForm);
    		return "customer_changepassword";
    }
    
    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
    public  String customerChangePassword(HttpServletRequest request, Model model,
    										  @Valid EmployeeForm customerForm, BindingResult result) {
    		if (result.hasErrors()) {
    			return "customer_changepassword";
    		}
    		/**
    		 * get the user from session attribute
    		 */
    		HttpSession session = request.getSession();
    		Customer e = (Customer) session.getAttribute("user");
    		
    		/**
    		 * confirm the old password and update the new password
    		 */
    		if (!e.getPassword().equals(customerForm.getPassword())) {
    			FieldError wrongPasswordError = new FieldError("customerForm", "password",
    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
    			result.addError(wrongPasswordError);
    			return "customer_changepassword";
    		} 
    			
    			String newPassword = customerForm.getPassword();
    			String confirmPassword = customerForm.getConfirmPassword();
    			
    			if (!newPassword.equals(confirmPassword)) {
    				FieldError PasswordNotMatchError = new FieldError("customerForm", "confirm_password",
    					messageSource.getMessage("PasswordNotMatchError", null, Locale.getDefault()));
    				result.addError(PasswordNotMatchError);
    			}
    			
    			e = customerService.updatePassword(e, confirmPassword);
    			session.setAttribute("user", new User(null, e.getUserName(), 1));
    			
    			/*add a success message*/
    			return "customer_viewaccount";
    }
    
    
    
    //employee update customer's password
    @RequestMapping(value = "/employee_changecuspassword", method = RequestMethod.GET)
    public String employeeChangeCusPassword(HttpServletRequest request, Model model, 
    											RedirectAttributes redirectAttributes) {
    		
    	    if (!checkEmployee(request)) {
    	    		redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
    	    		return "redirect:/employee_login";
    	    }
    		model.addAttribute("changePasswordForm", new ChangePasswordForm());
    		return "employee_changecuspassword";
    }
    
    @RequestMapping(value = "/employee_changecuspassword", method = RequestMethod.POST)
    public  String employeeChangeCusPassword(HttpServletRequest request, Model model,
    										  @Valid ChangePasswordForm changePasswordForm, BindingResult result,
    										  RedirectAttributes redirectAttributes) {
    	 	if (!checkEmployee(request)) {
             redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
             return "redirect:/employee_login";
         }
    	 	
    	 	
    	 	/**
    	 	 * get the error from the form validation
    	 	 */
    	 	result.addAllErrors(changePasswordForm.getValidationErrors());
            if (result.hasErrors()) {
                return "employee_register";
            }
         
            
        
       
    		if (result.hasErrors()) {
    			return "employee_changecuspassword";
    		}
    		/**
    		 * get the user from session attribute
    		 */
    		HttpSession session = request.getSession();
    		
    		/**
    		 * confirm the customer id
    		 */
    		
    		
    		
    		/**
    		 * confirm the old password and update the new password
    		 */
    		if (!e.getPassword().equals(customerForm.getPassword())) {
    			FieldError wrongPasswordError = new FieldError("customerForm", "password",
    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
    			result.addError(wrongPasswordError);
    			return "customer_changepassword";
    		} 
    			
    			String newPassword = customerForm.getPassword();
    			String confirmPassword = customerForm.getConfirmPassword();
    			
    			if (!newPassword.equals(confirmPassword)) {
    				FieldError PasswordNotMatchError = new FieldError("customerForm", "confirm_password",
    					messageSource.getMessage("PasswordNotMatchError", null, Locale.getDefault()));
    				result.addError(PasswordNotMatchError);
    			}
    			
    			e = customerService.updatePassword(e, confirmPassword);
    			session.setAttribute("user", new User(null, e.getUserName(), 1));
    			
    			/*add a success message*/
    			return "customer_viewaccount";
    }
    
}
