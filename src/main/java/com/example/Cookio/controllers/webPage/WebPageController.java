package com.example.Cookio.controllers.webPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Refers to login.html in templates/
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // Refers to dashboard.html in templates/
    }
}
