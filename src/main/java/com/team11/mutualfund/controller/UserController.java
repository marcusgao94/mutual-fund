package com.team11.mutualfund.controller;

import com.team11.mutualfund.model.Customer;
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

import com.team11.mutualfund.model.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	MessageSource messageSource;

	public boolean checkLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return session.getAttribute("user") != null;
	}

	public boolean checkEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) return false;
		User user = (User) session.getAttribute("user");
		return user.isEmployee();
	}

	// employee

	@RequestMapping(value = "/employee_register", method = RequestMethod.GET)
	public String createEmployee(HttpServletRequest request, Model model) {
		model.addAttribute("employeeForm", new EmployeeForm());
		if (!checkEmployee(request)) {
			return "redirect:/employee_login?error=login";
		}
		return "employee_register";
	}

	@RequestMapping(value = "/employee_register", method = RequestMethod.POST)
	public String createEmployee(HttpServletRequest request, Model model,
								 @Valid EmployeeForm employeeForm, BindingResult result) {
		if (!checkEmployee(request))
			return "redirect:/employee_login";
		if (result.hasErrors())
			return "employee_register";
		if (employeeForm.getUserName().isEmpty()) {
			FieldError emptyUserNameError = new FieldError("employeeForm", "userName",
					messageSource.getMessage("emptyUserName", null, Locale.getDefault()));
			result.addError(emptyUserNameError);
			return "employee_register";
		}
		if (employeeForm.getPassword().isEmpty()) {
			FieldError emptyPasswordError = new FieldError("employeeForm", "password",
					messageSource.getMessage("emptyPassword", null, Locale.getDefault()));
			result.addError(emptyPasswordError);
			return "employee_register";
		}
		if (!employeeForm.getPassword().equals(employeeForm.getConfirmPassword())) {
			FieldError passwordInconsistentError = new FieldError("employeeForm", "confirmPassword",
					messageSource.getMessage("passwordInconsistent", null, Locale.getDefault()));
			result.addError(passwordInconsistentError);
			return "employee_register";
		}
		Employee employee = new Employee(employeeForm);
		if (!employeeService.createEmployee(employee)) {
			FieldError userNameUniqueError = new FieldError("employeeForm", "userName",
					messageSource.getMessage("notUniqueUserName", new String[]{employee.getUserName()}, Locale.getDefault()));
			result.addError(userNameUniqueError);
			return "employee_register";
		}
		model.addAttribute("success", "Employee " + employee.getUserName() + " registered successfully");
		return "success";
	}

	@RequestMapping(value = "/employee_login", method = RequestMethod.GET)
	public String employeeLogin(HttpServletRequest request, Model model) {
		String message = (String) request.getParameter("error");
		if (message != null)
			model.addAttribute("error", "You have not login");
		EmployeeForm employeeForm = new EmployeeForm();
		model.addAttribute("employeeForm", employeeForm);
		return "employee_login";
	}

	@RequestMapping(value = "/employee_login", method = RequestMethod.POST)
	public String employeeLogin(HttpServletRequest request, Model model,
								@Valid EmployeeForm employeeForm, BindingResult result) {
		if (result.hasErrors())
			return "employee_login";
		Employee e = employeeService.findEmployeeByUserName(employeeForm.getUserName());
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
		session.setAttribute("user", new User(e.getUserName(), 1));
		return "redirect:/home";
	}

	// customer

	@RequestMapping(value = "customer_register", method = RequestMethod.GET)
	public String createCustomer(HttpServletRequest request, Model model) {
		model.addAttribute("customerForm", new CustomerForm());
		if (!checkEmployee(request)) {
			return "redirect:/employee_login?error=login";
		}
		return "customer_register";
	}

	@RequestMapping(value = "/customer_register", method = RequestMethod.POST)
	public String createCustomer(HttpServletRequest request, Model model,
								 @Valid CustomerForm customerForm, BindingResult result) {
		if (!checkEmployee(request))
			return "redirect:/employee_login";
		if (result.hasErrors())
			return "customer_register";
		if (customerForm.getUserName().isEmpty()) {
			FieldError emptyUserNameError = new FieldError("customerForm", "userName",
					messageSource.getMessage("emptyUserName", null, Locale.getDefault()));
			result.addError(emptyUserNameError);
			return "customer_register";
		}
		if (customerForm.getPassword().isEmpty()) {
			FieldError emptyPasswordError = new FieldError("customerForm", "password",
					messageSource.getMessage("emptyPassword", null, Locale.getDefault()));
			result.addError(emptyPasswordError);
			return "customer_register";
		}
		if (!customerForm.getPassword().equals(customerForm.getConfirmPassword())) {
			FieldError passwordInconsistentError = new FieldError("customerForm", "confirmPassword",
					messageSource.getMessage("passwordInconsistent", null, Locale.getDefault()));
			result.addError(passwordInconsistentError);
			return "customer_register";
		}
		Customer customer = new Customer(customerForm);
		if (!customerService.createCustomer(customer)) {
			FieldError userNameUniqueError = new FieldError("customerForm", "userName",
					messageSource.getMessage("notUniqueUserName", new String[]{customer.getUserName()}, Locale.getDefault()));
			result.addError(userNameUniqueError);
			return "customer_register";
		}
		model.addAttribute("success", "customer " + customer.getUserName() + " registered successfully");
		return "success";
	}

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
		session.setAttribute("user", new User(c.getUserName(), 0));
		return "redirect:/home";
	}

	/*
	@RequestMapping(value = { "/edit-{userName}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String userName, ModelMap model) {
		Employee employee = service.findEmployeeByUserName(userName);
		model.addAttribute("employee", employee);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	@RequestMapping(value = { "/edit-{userName}-employee" }, method = RequestMethod.POST)
	public String updateEmployee( Employee employee, BindingResult result,
			ModelMap model, @PathVariable String userName) {

		if (result.hasErrors()) {
			return "registration";
		}

		service.updateEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getUserName()	+ " updated successfully");
		return "success";
	}
	*/

}
