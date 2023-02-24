package com.estate.estateserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    @RequestMapping("/*")
    @RolesAllowed("USER")
    public String getUser() {
        return "Welcome, user";
    }

    @RequestMapping("/admin")
    @RolesAllowed("ADMIN")
    public String getAdmin() {
        return "Welcome, admin";
    }

}
