package com.team11.mutualfund.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by marcusgao on 17/1/20.
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"/", "/home", "/index"})
    public String index() {
        return "home";
    }
}
