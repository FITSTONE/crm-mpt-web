package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.models.UserModel;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUser (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("User", userService.getUser(user));
        return "profile/profile";
    }

    @GetMapping("/edit")
    public String getEditUser (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Users", user);
        return "profile/profiledit";
    }

    @PostMapping("/edit")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String name, @RequestParam String surname,
                                @RequestParam String middle, @RequestParam String phone,
                                @RequestParam String email, @RequestParam String pass){
        try{
            User user1 = new User(name, surname, middle, phone, email, pass);
            userService.updateAccount(user, user1);
            return "redirect:/profile";
        }
        catch (ExceptionMessage e){
            return "redirect:/profile/edit";
        }
    }
}
