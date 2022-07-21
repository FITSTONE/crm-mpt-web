package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/deal")
public class DealController {

    private String messageError;

    @Autowired
    private UserService userService;

    @Autowired
    private DealService dealService;

    @Autowired
    private TypeDealService typeService;

    @Autowired
    private StatusDealService statusService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ClientService clientService;


    @GetMapping
    public String getDeals(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Deal", dealService.getDeals(user.getID()));
        model.addAttribute("Type_deal", typeService.getTypeDeals(user));
        model.addAttribute("Status_deal", statusService.getStatusDeals(user));
        return "deal/dealList";
    }

    @GetMapping("/search")
    public String getDealBySearch(@AuthenticationPrincipal User user,
                                @RequestParam Long type, Long status,
                                Model model)  {
        if(type != 0 && status != 0)
            model.addAttribute("Deal", dealService.getDeals(user.getID()));
        if(type != 0 && status == 0)
            model.addAttribute("Deal", dealService.getDealsByType(type, user.getID()));
        if(type == 0 && status != 0)
            model.addAttribute("Deal", dealService.getDealsByStatus(status, user.getID()));
        if(type == 0 && status == 0)
            model.addAttribute("Deal", dealService.getDeals(user.getID()));
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Type_deal", typeService.getTypeDeals(user));
        model.addAttribute("Status_deal", statusService.getStatusDeals(user));
        return "deal/dealList";
    }

    @GetMapping("/{id}")
    public String getDeal(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Deal", dealService.getDeal(id,user.getID()));
        model.addAttribute("Task", taskService.getTaskByDeal(id, user.getID()));
        return "deal/dealDetails";
    }

    @GetMapping("close/{id}")
    public String closeDeal(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        dealService.isClosed(id, user);
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Deal", dealService.getDeal(id,user.getID()));
        model.addAttribute("Task", taskService.getTaskByDeal(id, user.getID()));
        return "deal/dealDetails";
    }

    @PostMapping("/add")
    public String addDeal(@AuthenticationPrincipal User user,
                            @RequestParam String title, @RequestParam String description,
                            @RequestParam Double total, @RequestParam String type,
                            @RequestParam String status, @RequestParam Long client){
        try{
            DealModel model = new DealModel(title, description, total, type, status, client);
            dealService.addDeal(model, user);
            messageError = null;
            return "redirect:/deal";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/deal/add";
        }
    }

    @GetMapping("/add")
    public String getAddDeal(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("Type_deal", typeService.getTypeDeals(user));
        model.addAttribute("Status_deal", statusService.getStatusDeals(user));
        model.addAttribute("Error", messageError);
        return "deal/dealAdd";
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @AuthenticationPrincipal User user,
                          @RequestParam String title, @RequestParam String description,
                          @RequestParam Double total, @RequestParam String type,
                          @RequestParam String status, @RequestParam Long client){
        try{
            DealModel model = new DealModel(id, title, description, total, type, status, client);
            dealService.updateDeal(model, user);
            messageError = null;
            return "redirect:/deal/{id}";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/deal/update/{id}";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateDeal(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Deal", dealService.getDeal(id, user.getID()));
        model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("Type_deal", typeService.getTypeDeals(user));
        model.addAttribute("Status_deal", statusService.getStatusDeals(user));
        model.addAttribute("Error", messageError);
        return "deal/dealUpdate";
    }

    @PostMapping("/delete/{id}")
    public String deleteDeal(@PathVariable long id){
        try{
            dealService.deleteDeal(id);
            return "redirect:/deal";
        }
        catch (ExceptionMessage e){
            return "redirect:/deal/{id}";
        }
    }
}
