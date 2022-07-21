package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Status_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.StatusClientService;
import com.project.crmpt.service.StatusDealService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statusdeal")
public class StatusDealController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatusDealService statusService;

    @GetMapping
    public String getStatusDeal(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("StatusDeal", statusService.getStatusDeals(user));
        return "settings/statusdeal";
    }

    @PostMapping("/add")
    public String addStatusDeal(@AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_deal status = new Status_deal(title);
            statusService.addStatusDeal(status, user);
            return "redirect:/statusdeal";
        }catch (ExceptionMessage e){
            return "redirect:/statusdeal";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStatusDeal(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam String title){
        try {
            Status_deal status = new Status_deal(id, title);
            statusService.updateStatusDeal(status, user);
            return "redirect:/statusdeal";
        }catch (ExceptionMessage e){
            return "redirect:/statusdeal";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStatusDeal(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            statusService.deleteStatusDeal(id, user);
            return "redirect:/statusdeal";
        }catch (ExceptionMessage e){
            return "redirect:/statusdeal";
        }
    }
}
