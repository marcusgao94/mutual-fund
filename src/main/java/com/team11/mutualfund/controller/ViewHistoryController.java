package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.CustomerRegisterForm;
import com.team11.mutualfund.form.EmployeeRegisterForm;
import com.team11.mutualfund.form.SearchForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes("customerList")
public class ViewHistoryController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageSource messageSource;

    // employeeViewHistory

    @RequestMapping(value = "/employee_searchtransaction", method = RequestMethod.GET)
    public String employeeViewHistory(HttpServletRequest request, Model model, 
    		RedirectAttributes redirectAttributes, 
    		@RequestParam(value = "un", required = false) String userName) {
    	
    	if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
    	if (userName != null) {
            Customer c = customerService.getCustomerByUserName(userName);
            if (c == null)
                return "redirect:/employee_searchcustomer";
    		SearchForm searchForm = new SearchForm();
    		searchForm.setUserName(userName);
    		model.addAttribute("userName",userName);
    		List<Transaction> pendingTransaction = transactionService.listPendingTransactionByCustomerId(c.getId());
        	model.addAttribute("employee_pendingTransaction", pendingTransaction);
            List<Transaction> finishTransaction = transactionService.listFinishTransactionByCustomerId(c.getId());
            model.addAttribute("employee_finishTransaction", finishTransaction);
    		return "employee_transactionhistory_fast";
    	}
		List<Customer> customerList = customerService.getCustomerList();
	    model.addAttribute("customerList",customerList);
        model.addAttribute("searchForm", new SearchForm());
        return "employee_searchtransaction";
    }

    // employeeViewHistory
    @RequestMapping(value = "/employee_searchtransaction", method = RequestMethod.POST)
    public String employeeViewHistory(HttpServletRequest request, Model model,
                                      // @ModelAttribute("customerList") LinkedList<Customer> customerList,
                                 @Valid SearchForm searchForm, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
    	if (!checkEmployee(request)) {
        	redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
    	if (result.hasErrors()) {
            return "employee_searchtransaction";
        }
	    Customer customer = customerService.getCustomerByUserName(searchForm.getUserName());
        if (customer == null) {
            result.rejectValue("userName", "", NOUSERNAME);
            return "employee_searchtransaction";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("searchForm", searchForm);
    	List<Transaction> pendingTransaction = transactionService.listPendingTransactionByCustomerId(customer.getId());
    	model.addAttribute("employee_pendingTransaction", pendingTransaction);
        List<Transaction> finishTransaction = transactionService.listFinishTransactionByCustomerId(customer.getId());
        model.addAttribute("employee_finishTransaction", finishTransaction);
        return "employee_transactionhistory";
    }

    // customer

    @RequestMapping(value = "customer_transactionhistory", method = RequestMethod.GET)
    public String customerViewHistory(HttpServletRequest request, Model model,
                                 RedirectAttributes redirectAttributes) {
    	if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        Customer c = customerService.getCustomerByUserName(user.getUserName());
        
        List<Transaction> pendingTransaction = transactionService.listPendingTransactionByCustomerId(c.getId());
        request.setAttribute("customer_pendingTransaction", pendingTransaction);
        
        List<Transaction> finishTransaction = transactionService.listFinishTransactionByCustomerId(c.getId());
        
        request.setAttribute("customer_finishTransaction", finishTransaction);
        
        return "customer_transactionhistory";
    }
}
