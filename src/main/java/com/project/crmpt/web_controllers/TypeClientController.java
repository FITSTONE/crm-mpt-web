package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Client;
import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.Type_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.TypeClientService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/typeclient")
public class TypeClientController {

    @Autowired
    private TypeClientService typeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getTypeClient(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("TypeClient", typeService.getTypeClients(user));
        return "settings/typeclient";
    }

    @PostMapping("/add")
    public String addTypeClient(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_client client = new Type_client(title);
            typeService.addTypeClient(client, user);
            return "redirect:/typeclient";
        }catch (ExceptionMessage e){
            return "redirect:/typeclient";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTypeClient(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_client type = new Type_client(id, title);
            typeService.updateTypeClient(type, user);
            return "redirect:/typeclient";
        }catch (ExceptionMessage e){
            return "redirect:/typeclient";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeClient(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            typeService.deleteTypeClient(id, user);
            return "redirect:/typeclient";
        }catch (ExceptionMessage e){
            return "redirect:/typeclient";
        }
    }
}
