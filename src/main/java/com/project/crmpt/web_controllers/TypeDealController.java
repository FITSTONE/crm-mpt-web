package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Type_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.TypeDealService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/typedeal")
public class TypeDealController {

    @Autowired
    private TypeDealService typeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getTypeDeal(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("TypeDeal", typeService.getTypeDeals(user));
        return "settings/typedeal";
    }

    @PostMapping("/add")
    public String addTypeDeal(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_deal type_deal = new Type_deal(title);
            typeService.addTypeDeal(type_deal, user);
            return "redirect:/typedeal";
        }catch (ExceptionMessage e){
            return "redirect:/typedeal";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTypeDeal(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Type_deal type = new Type_deal(id, title);
            typeService.updateTypeDeal(type, user);
            return "redirect:/typedeal";
        }catch (ExceptionMessage e){
            return "redirect:/typedeal";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeDeal(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            typeService.deleteTypeDeal(id, user);
            return "redirect:/typedeal";
        }catch (ExceptionMessage e){
            return "redirect:/typedeal";
        }
    }
}
