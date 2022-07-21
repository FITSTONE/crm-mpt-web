package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

@Controller
public class RegistrationController {

    private String messageError;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String Register (Model model) {
        model.addAttribute("Error", messageError);
        return "register";
    }

    @GetMapping("/good")
    public String Good (Model model) {
        return "good";
    }

    @PostMapping("/register")
    public String UserPost(@RequestParam String name, @RequestParam String surname,
                           @RequestParam String middle_name, @RequestParam String phone,
                           @RequestParam String email, @RequestParam String pass,
                           Model model) {
        try{
            User user = new User(name, surname, middle_name, phone, email, pass);
            Long id = userService.registrationAccount(user);
            userService.registrationDataByAccount(user);
            messageError = null;
            return "redirect:/good";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model){
        boolean isActivate = userService.activateUser(code);
        if(isActivate == true)
            model.addAttribute("message", "Ваш аккаунт активирован");
        else
            model.addAttribute("message","Ваш аккаунт еще не активирован");
        return "redirect:/login";
    }

    @GetMapping("/recovery")
    public String recovery(Model model){
        model.addAttribute("Error", messageError);
        return "recoveryPassword";
    }

    @GetMapping("/support")
    public String support(Model model){
        model.addAttribute("Error", messageError);
        return "supportPage";
    }

    @PostMapping("/support")
    public String supportPost (@RequestParam String email, @RequestParam String body){
        try{
            userService.support(email, body);
            messageError = null;
            return "redirect:/";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/support";
        }
    }

    @PostMapping("/recovery")
    public String recoveryAccount(@RequestParam String email){
        try{
            userService.recoveryPassword(email);
            messageError = null;
            return "recoveryPage";
        }
        catch (ExceptionMessage e){
            return "redirect:/recovery";
        }
    }

    @GetMapping("/reset/{code}")
    public String reset(@PathVariable String code, Model model){
        model.addAttribute("code", code);
        return "resetPassword";
    }

    @PostMapping("/reset")
    public String resetPassword (@RequestParam String code, @RequestParam String password){
        userService.resetPassword(code, password);
        return "redirect:/login";
    }

    @GetMapping("/images/favicon.ico")
    public ImageIcon favicon (){
        return new ImageIcon("favicon.ico");
    }
}