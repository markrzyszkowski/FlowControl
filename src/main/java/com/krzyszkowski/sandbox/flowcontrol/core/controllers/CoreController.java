package com.krzyszkowski.sandbox.flowcontrol.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CoreController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "dashboard", method = {RequestMethod.GET, RequestMethod.POST})
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
