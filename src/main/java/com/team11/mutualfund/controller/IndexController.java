package com.team11.mutualfund.controller;

import com.team11.mutualfund.utils.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @RequestMapping(value = {"/", "/home", "/index"})
    public String index() {
        return "home";
    }

    @RequestMapping("/header")
    public String header(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User("Guest", -1);
        }
        model.addAttribute("user", user);
        return "header";
    }
}
