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

import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import static com.team11.mutualfund.utils.Constant.CUSTOMERNOTEXIST;
import static com.team11.mutualfund.utils.Constant.WRONGPASSWORD;

public class ChangePasswordController {
<<<<<<< HEAD
	

	@Autowired
=======
    @Autowired
>>>>>>> 3ce19ded58444d930c1f9da1c5ea77aaf7fc6c37
    MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;


    //employee

    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.GET)
<<<<<<< HEAD
    public String employChangePassword(HttpServletRequest request, Model model,
    										RedirectAttributes redirectAttributes) {
    		
    		if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }

    		model.addAttribute("changePasswordForm", new ChangePasswordForm());
    		return "employee_changepassword";
=======
    public String employChangePassword(HttpServletRequest request, Model model) {
        String message = (String) request.getParameter("error");
        if (message != null) {
            model.addAttribute("error", "You have not login as an employee");
        }
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);
        return "employee_changepassword";
>>>>>>> 3ce19ded58444d930c1f9da1c5ea77aaf7fc6c37
    }

    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
<<<<<<< HEAD
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
    		Employee e = (Employee) session.getAttribute("user");
    		
    		/*
    		 * confirm and update the password
    		 */
    		String confirmPassword = changePasswordForm.getConfirmNewPassword();
    		
    		if (!employeeService.matchPassword(e, confirmPassword)) {
    			FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
    			result.addError(wrongPasswordError);
    			return "employee_changepassword";
    		} 
  
    		employeeService.updatePassword(e, confirmPassword);
    			
    		session.setAttribute("user", new User(null, e.getUserName(), 1));
    			
		/*consider to separate the page*/
		 model.addAttribute("success", "Employee " + e.getUserName() + " update password successfully");
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
    		 * get the user from session attribute
    		 */
    		HttpSession session = request.getSession();
    		Customer c = (Customer) session.getAttribute("user");
    		
    		/*
    		 * confirm and update the password
    		 */
    		String confirmPassword = changePasswordForm.getConfirmNewPassword();
    		
    		if (!customerService.matchPassword(c, confirmPassword)) {
    			FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
    					messageSource.getMessage("wrongPasswordError", null, Locale.getDefault()));
    			result.addError(wrongPasswordError);
    			return "customer_changepassword";
    		} 
  
    		customerService.updatePassword(c, confirmPassword);
    			session.setAttribute("user", new User(null, c.getUserName(), 1));
    			
    			/*consider to separate the page*/
    			 model.addAttribute("success", "Customer " + c.getUserName() + " update password successfully");
    			return "success";
    		
    }
    
    
    
    
=======
    public String employeeChangePassword(HttpServletRequest request, Model model,
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
        String message = (String) request.getParameter("error");
        if (message != null) {
            model.addAttribute("error", "You have not login");
        }
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("customerForm", employeeForm);
        return "customer_changepassword";
    }

    @RequestMapping(value = "/employee_changepassword", method = RequestMethod.POST)
    public String customerChangePassword(HttpServletRequest request, Model model,
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


>>>>>>> 3ce19ded58444d930c1f9da1c5ea77aaf7fc6c37
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
<<<<<<< HEAD
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
           
       
       Long customerId = changePasswordForm.getCustomerId();
       String confirmPassword = changePasswordForm.getConfirmNewPassword();
       
       Customer c = customerService.findCustomerbyId(customerId);
       /*
        * Check Customer by id
        */
       
       if (!customerService.checkCustomerbyId(customerId)) {
    	   		FieldError customerNotExitError = new FieldError("changePasswordForm","customerId", CUSTOMERNOTEXIST); 
	   		result.addError(customerNotExitError);
       }
       
       /*
        * Check password
        */
       
       if (!customerService.matchPassword(c, changePasswordForm.getOriginPassword())) {
    	   		FieldError wrongPasswordError = new FieldError("changePasswordForm", "password",
    	   														WRONGPASSWORD);
			result.addError(wrongPasswordError);
       }
       
      /*
       * Update the password
       */
       c = customerService.updateCustomerPassword(customerId, confirmPassword);

    		if (result.hasErrors()) {
    			return "employee_changecuspassword";
    		}

		HttpSession session = request.getSession();
		session.setAttribute("user", new User(null, c.getUserName(), 0));
		
		/*consider to separate the page*/
		model.addAttribute("success", "Customer " + c.getUserName() + " password has been updated successfully");
		return "success";
=======
    public String employeeChangeCusPassword(HttpServletRequest request, Model model,
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
        /*
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
>>>>>>> 3ce19ded58444d930c1f9da1c5ea77aaf7fc6c37
    }

}
