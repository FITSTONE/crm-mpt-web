package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Type_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.StatusClientService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statusclient")
public class StatusClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatusClientService statusService;

    @GetMapping
    public String getStatusClient(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("StatusClient", statusService.getStatusClients(user));
        return "settings/statusclient";
    }

    @PostMapping("/add")
    public String addStatusClient(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_client status = new Status_client(title);
            statusService.addStatusClient(status, user);
            return "redirect:/statusclient";
        }catch (ExceptionMessage e){
            return "redirect:/statusclient";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStatusClient(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_client status = new Status_client(id, title);
            statusService.updateStatusClient(status, user);
            return "redirect:/statusclient";
        }catch (ExceptionMessage e){
            return "redirect:/statusclient";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStatusClient(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            statusService.deleteStatusClient(id, user);
            return "redirect:/statusclient";
        }catch (ExceptionMessage e){
            return "redirect:/statusclient";
        }
    }
}