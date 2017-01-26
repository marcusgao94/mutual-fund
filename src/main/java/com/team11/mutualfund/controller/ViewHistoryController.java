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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.DUPLICATEUSERNAME;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;
import static com.team11.mutualfund.utils.Constant.NOUSERNAME;

import java.util.List;

@Controller
public class ViewHistoryController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageSource messageSource;



    // employeeViewHistory

    @RequestMapping(value = "/employee_searchtransaction", method = RequestMethod.GET)
    public String employeeViewHistory(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
    	if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
    	
        SearchForm SearchForm = new SearchForm();
        model.addAttribute("searchForm", SearchForm);
        
        return "employee_searchtransaction";
    }
    // employeeViewHistory
    @RequestMapping(value = "/employee_searchtransaction", method = RequestMethod.POST)
    public String employeeViewHistory(HttpServletRequest request, Model model,
                                 @Valid SearchForm searchForm, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return "employee_searchcustomer";
        Customer c = customerService.getCustomerByUserName(searchForm.getUserName());

        if (c == null) {
            FieldError userNameExistError = new FieldError("searchForm", "userName", NOUSERNAME);
            result.addError(userNameExistError);
            return "employee_searchcustomer";
        }
    	
    	List<Transaction> pendingTransaction = transactionService.listPendingTransactionByCustomerId(c.getId());
        request.setAttribute("employee_pendingTransaction", pendingTransaction);
        
        List<Transaction> finishTransaction = transactionService.listFinishTransactionByCustomerId(c.getId());
        request.setAttribute("employee_finishTransaction", finishTransaction);

        return "redirect:/employee_transactionhistory";
    }

    // customer

    @RequestMapping(value = "customer_transactionhistory", method = RequestMethod.GET)
    public String customerViewHistory(HttpServletRequest request, Model model,
                                 RedirectAttributes redirectAttributes) {
    	if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
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
