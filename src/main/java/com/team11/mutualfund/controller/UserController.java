package com.team11.mutualfund.controller;

import java.util.List;

import com.team11.mutualfund.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team11.mutualfund.model.Employee;

@Controller
public class UserController {

	@Autowired
	EmployeeService service;
	
	@Autowired
	MessageSource messageSource;


	public boolean checkLogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null;
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(Authentication auth, Model model) {
		if (auth == null)
			return "login";
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(Employee employee, Model model) {

		model.addAttribute("success", "Employee " + employee.getUserName() + " registered successfully");
		return "success";
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