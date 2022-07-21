package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    private String messageError;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DealService dealService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TypeClientService typeService;

    @Autowired
    private StatusClientService statusService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getDetailsClient(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Client", clientService.getClient(id, user.getID()));
        model.addAttribute("Deal", dealService.getDealsByClient(id, user.getID()));
        model.addAttribute("Task", taskService.getTaskByClient(id, user.getID()));
        messageError = null;
        return "client/clientDetails";
    }

    @GetMapping
    public String getClients(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("TypeClient", typeService.getTypeClients(user));
        model.addAttribute("StatusClient", statusService.getStatusClients(user));
        model.addAttribute("Company", companyService.getCompanies(user));
        messageError = null;
        return "client/clientList";
    }

    @GetMapping("/search")
    public String getClientsBySearch(@RequestParam Long type, @RequestParam Long status,
                                     @AuthenticationPrincipal User user, Model model)  {
        if(type != 0 && status != 0)
            model.addAttribute("Client", clientService.getClients(user.getID()));
        if(type != 0 && status == 0)
            model.addAttribute("Client", clientService.getClientsByTypeClient(type, user));
        if(type == 0 && status != 0)
            model.addAttribute("Client", clientService.getClientsByStatusClient(status, user));
        if(type == 0 && status == 0)
            model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("TypeClient", typeService.getTypeClients(user));
        model.addAttribute("StatusClient", statusService.getStatusClients(user));
        model.addAttribute("Company", companyService.getCompanies(user));
        return "client/clientList";
    }

    @GetMapping("/add")
    public String getAddClient (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Type_client", typeService.getTypeClients(user));
        model.addAttribute("Status_client", statusService.getStatusClients(user));
        model.addAttribute("Company", companyService.getCompanies(user));
        model.addAttribute("Error", messageError);
        return "client/clientAdd";
    }

    @PostMapping("/add")
    public String addClient(@AuthenticationPrincipal User user,
                            @RequestParam String name, @RequestParam String surname,
                            @RequestParam String middle, @RequestParam String phone,
                            @RequestParam String email, @RequestParam String type,
                            @RequestParam String status, @RequestParam String company){
        try{
            ClientModel model = new ClientModel(name, surname, middle, phone, email, type, status, company);
            clientService.addClient(user, model);
            messageError = null;
            return "redirect:/client";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/client/add";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateClient(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Client", clientService.getClient(id, user.getID()));
        model.addAttribute("Type_client", typeService.getTypeClients(user));
        model.addAttribute("Status_client", statusService.getStatusClients(user));
        model.addAttribute("Company", companyService.getCompanies(user));
        model.addAttribute("Error", messageError);
        return "client/clientUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@AuthenticationPrincipal User user, @PathVariable Long id,
                               @RequestParam String name, @RequestParam String surname,
                               @RequestParam String middle, @RequestParam String phone,
                               @RequestParam String email, @RequestParam String type,
                               @RequestParam String status, @RequestParam String company){
        try{
            ClientModel model = new ClientModel(id, name, surname, middle, phone, email, type, status, company);
            clientService.updateClient(user, model);
            messageError = null;
            return "redirect:/client/{id}";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/client/update/{id}";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable long id){
        try{
            clientService.deleteClient(id);
            return "redirect:/client";
        }
        catch (ExceptionMessage e){
            return "redirect:/client/{id}";
        }
    }

    @PostMapping("/only/{id}")
    public String deleteOnlyClient(@AuthenticationPrincipal User user, @PathVariable long id){
        try{
            clientService.deleteOnlyClient(id, user);
            return "redirect:/client";
        }
        catch (ExceptionMessage e){
            return "redirect:/client/{id}";
        }
    }
}
