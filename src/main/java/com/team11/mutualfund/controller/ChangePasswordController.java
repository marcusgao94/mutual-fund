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
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.utils.Constant.*;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.RollbackException;
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

    		model.addAttribute("changePasswordForm", new ChangePasswordForm());
    		return "employee_changepassword";
    }
    
    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
    public  String employeeChangePassword(HttpServletRequest request, Model model,
    										  @Valid ChangePasswordForm changePasswordForm, BindingResult result,
    										  RedirectAttributes redirectAttributes) {
    		
    		if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
    		
    		
    		result.addAllErrors(changePasswordForm.getValidationErrors());
    		
    		if (result.hasErrors()) {
    			return "employee_changepassword";
    		}
    		/*
    		 * get the user from session attribute
    		 */
    		HttpSession session = request.getSession();
    		String name = ((Employee) session.getAttribute("user")).getUserName();
    		
    		
    		/*
    		 * confirm and update the password
    		 */
    		String confirmPassword = changePasswordForm.getConfirmNewPassword();
    		
    		try {
				employeeService.updatePassword(name, confirmPassword);
			} catch (RollbackException e1) {
				FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
						messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
				result.addError(wrongPasswordError);
				return "employee_changepassword";
			} 

    		session.setAttribute("user", new User(null, name, 1));
    			
		/*consider to separate the page*/
		 model.addAttribute("success", "Employee " + name + " update password successfully");
		return "success";
    		
    }
    
    
    
    
    //customer update his own password
    @RequestMapping(value = "/customer_changepassword", method = RequestMethod.GET)
    public String customerChangePassword(HttpServletRequest request, Model model,
    										RedirectAttributes redirectAttributes) {
    		
    		/*
    		 * Check whether the customer has logged in.
    		 */
    		if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/customer_login";
        }

    		model.addAttribute("changePasswordForm", new ChangePasswordForm());
    		return "customer_changepassword";
    }
    
    @RequestMapping(value = "/customer_changepassword", method = RequestMethod.POST)
    public  String customerChangePassword(HttpServletRequest request, Model model,
    										  @Valid ChangePasswordForm changePasswordForm, BindingResult result,
    										  RedirectAttributes redirectAttributes) {
    		
    		if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/customer_login";
        }
    		
    		
    		result.addAllErrors(changePasswordForm.getValidationErrors());
    		
    		if (result.hasErrors()) {
    			return "customer_changepassword";
    		}
    		/*
    		 * get the user id from session attribute
    		 */
    		HttpSession session = request.getSession();
    		Long cid = (Long) ((Customer) session.getAttribute("user")).getId();
    		
 
    		
    		/*
    		 * confirm and update the password
    		 */
    		String confirmPassword = changePasswordForm.getConfirmNewPassword();
    			
    		
    			try {
					customerService.updatePassword(cid, confirmPassword);
				} catch (RollbackException e) {
		    			FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
		    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
		    			result.addError(wrongPasswordError);
		    			return "customer_changepassword";
				}

    			session.setAttribute("user", new User(cid, ((User) session.getAttribute("user")).getUserName(), 1));
    			
    			/*consider to separate the page*/
    			 model.addAttribute("success", "Customer " + ((User) session.getAttribute("user")).getUserName() + " update password successfully");
    			return "success";
    		
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
    	 	
    	 	
    	 	/*
    	 	 * get the error from the form validation
    	 	 */
    	 	result.addAllErrors(changePasswordForm.getValidationErrors());
            if (result.hasErrors()) {
                return "employee_changecuspassword";
            }
           
       
       Long cid = changePasswordForm.getCustomerId();
       String confirmPassword = changePasswordForm.getConfirmNewPassword();
       
       /*
        * Check Customer by id
        */
       
       if (!customerService.checkCustomerbyId(cid)) {
    	   		FieldError customerNotExitError = new FieldError("changePasswordForm","customerId", CUSTOMERNOTEXIST); 
	   		result.addError(customerNotExitError);
       }
       
       /*
        * Check password
        */
       
     
    	   Customer c;
		try {
			c = customerService.updateCustomerPassword(cid, confirmPassword);
		} catch (RollbackException e) {
			FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
						WRONGPASSWORD);
			result.addError(wrongPasswordError);
		}

      /*
       * Update the password
       */
       
    		if (result.hasErrors()) {
    			return "employee_changecuspassword";
    		}

		HttpSession session = request.getSession();
		session.setAttribute("user", new User(null, ((User) session.getAttribute("user")).getUserName(), 0));
		
		/*consider to separate the page*/
		model.addAttribute("success", "Customer " + ((User) session.getAttribute("user")).getUserName() + " password has been updated successfully");
		return "success";
    }
    
}
