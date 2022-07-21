package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Status_deal;
import com.project.crmpt.entity.Status_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.StatusTaskService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statustask")
public class StatusTaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatusTaskService statusService;

    @GetMapping
    public String getStatusTask(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("StatusTask", statusService.getStatusTasks(user));
        return "settings/statustask";
    }

    @PostMapping("/add")
    public String addStatusTask(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_task status = new Status_task(title);
            statusService.addStatusTask(status, user);
            return "redirect:/statustask";
        }catch (ExceptionMessage e){
            return "redirect:/statustask";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStatusTask(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_task status = new Status_task(id, title);
            statusService.updateStatusTask(status, user);
            return "redirect:/statustask";
        }catch (ExceptionMessage e){
            return "redirect:/statustask";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStatusTask(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            statusService.deleteStatusTask(id, user);
            return "redirect:/statustask";
        }catch (ExceptionMessage e){
            return "redirect:/statustask";
        }
    }
}