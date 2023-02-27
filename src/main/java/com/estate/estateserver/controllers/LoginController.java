package com.estate.estateserver.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @RequestMapping("/**")
    @RolesAllowed("USER")
    public String getUser() {
        return "Welcome, user";
    }

    @RequestMapping("/admin")
    @RolesAllowed("ADMIN")
    public String getAdmin() {
        return "Welcome, admin";
    }

    @RequestMapping("/*")
    public String getGithub() {
        return "Welcome, Github User !";
    }

}
