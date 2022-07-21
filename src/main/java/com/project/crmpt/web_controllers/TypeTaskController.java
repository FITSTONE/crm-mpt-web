package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.Type_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.TypeTaskService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/typetask")
public class TypeTaskController {

    @Autowired
    private TypeTaskService typeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getTypeTask(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("TypeTask", typeService.getTypeTasks(user));
        return "settings/typetask";
    }

    @PostMapping("/add")
    public String addTypeTask(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_task task = new Type_task(title);
            typeService.addTypeTask(task, user);
            return "redirect:/typetask";
        }catch (ExceptionMessage e){
            return "redirect:/typetask";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTypeTask(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_task type = new Type_task(id, title);
            typeService.updateTypeTask(type, user);
            return "redirect:/typetask";
        }catch (ExceptionMessage e){
            return "redirect:/typetask";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeTask(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            typeService.deleteTypeTask(id, user);
            return "redirect:/typetask";
        }catch (ExceptionMessage e){
            return "redirect:/typetask";
        }
    }
}