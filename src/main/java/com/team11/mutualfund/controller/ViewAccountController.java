package com.team11.mutualfund.controller;

import com.team11.mutualfund.form.CustomerRegisterForm;
import com.team11.mutualfund.form.EmployeeRegisterForm;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.service.CustomerService;
import com.team11.mutualfund.service.EmployeeService;
import com.team11.mutualfund.service.FundService;
import com.team11.mutualfund.service.TransactionService;
import com.team11.mutualfund.utils.Positionvalue;
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

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import static com.team11.mutualfund.controller.LoginController.checkCustomer;
import static com.team11.mutualfund.controller.LoginController.checkEmployee;
import static com.team11.mutualfund.utils.Constant.CUSTOMERNOTLOGIN;
import static com.team11.mutualfund.utils.Constant.DUPLICATEUSERNAME;
import static com.team11.mutualfund.utils.Constant.NOTLOGIN;

import java.util.List;

@Controller
public class ViewAccountController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FundService fundService;

    @Autowired
    private MessageSource messageSource;

    // employeeViewHistory

    @RequestMapping(value = "/employee_viewaccount", method = RequestMethod.GET)
    public String employeeViewAccount(HttpServletRequest request, Model model,
                                      RedirectAttributes redirectAttributes) {
        if (!checkEmployee(request)) {
            redirectAttributes.addFlashAttribute("loginError", NOTLOGIN);
            return "redirect:/employee_login";
        }
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Customer c = customerService.getCustomerByUserName(user.getUserName());
        model.addAttribute("customer_account", c);
        return "employee_viewaccount";
    }

    // customer

    @RequestMapping(value = "customer_viewaccount", method = RequestMethod.GET)
    public String customerViewAccount(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!checkCustomer(request)) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Customer c = customerService.getCustomerById(user.getId());
        model.addAttribute("customer_account", c);
        
        try {
            List<Positionvalue> positionvalueList = fundService.listPositionvalueByCustomerId(
                    c.getId());
            model.addAttribute("customerPosition", positionvalueList);
            return "customer_viewaccount";
        } catch (RollbackException e) {
            redirectAttributes.addFlashAttribute("loginError", CUSTOMERNOTLOGIN);
            return "redirect:/customer_login";
        }
    }

//    @RequestMapping(value = "/customer_transactionhistory", method = RequestMethod.POST)
//    public String customerViewAccount(HttpServletRequest request, Model model,
//                                 @Valid CustomerRegisterForm customerRegisterForm, BindingResult result,
//                                 RedirectAttributes redirectAttributes) {
//    	
//		HttpSession session = request.getSession();
//		Customer c = (Customer) session.getAttribute("user");
//		request.setAttribute("customer_account", c);
//        //Customer c = customerService.findCustomerByUserName(customerRegisterForm.getUserName());
//    	List<Transaction> pendingTransaction = transactionService.listPendingTransactionByCustomerId(c.getId());
//        request.setAttribute("customer_pendingTransaction", pendingTransaction);
//        
//        List<Transaction> finishTransaction = transactionService.listFinishTransactionByCustomerId(c.getId());
//        request.setAttribute("customer_finishTransaction", finishTransaction);
//        
//        return "customer_viewaccount";
//    }

}