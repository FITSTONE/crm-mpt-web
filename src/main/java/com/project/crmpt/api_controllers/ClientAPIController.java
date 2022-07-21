package com.project.crmpt.api_controllers;

import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.entity.Client;
import com.project.crmpt.entity.User;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.repository.DealRepo;
import com.project.crmpt.service.ClientService;
import com.project.crmpt.service.DealService;
import com.project.crmpt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
public class ClientAPIController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DealService dealService;

    @Autowired
    private TaskService taskService;


    @GetMapping
    public Iterable<ClientModel> getClients(@AuthenticationPrincipal User user)  {
        return clientService.getClients(user.getID());
    }

    @GetMapping("/{id}")
    public ClientModel getClient(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return clientService.getClient(id, user.getID());
    }

    @GetMapping("/type/{id}")
    public Iterable<ClientModel> getClientsByTypeClient(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return clientService.getClientsByTypeClient(id,user);
    }

    @GetMapping("/status/{id}")
    public Iterable<ClientModel> getClientsByStatusClient(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return clientService.getClientsByStatusClient(id,user);
    }

    @GetMapping("/company/{id}")
    public Iterable<ClientModel> getClientsByCompany(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return clientService.getClientsByCompany(id,user);
    }

    @GetMapping("/deal/{id}")
    public Iterable<DealModel> getDealByClient(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return dealService.getDealsByClient(id, user.getID());
    }

    @GetMapping("task/{id}")
    public Iterable<TaskModel> getTaskByStatus(@AuthenticationPrincipal User user, @PathVariable Long id)  {
        return taskService.getTaskByClient(id, user.getID());
    }

    @PostMapping
    public ResponseEntity addClient(@AuthenticationPrincipal User user,
                                    @RequestBody ClientModel client){
        try{
            clientService.addClient(user, client);
            return ResponseEntity.ok("Клиент успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateClient(@AuthenticationPrincipal User user,
                                       @RequestBody ClientModel client){
        try{
            clientService.updateClient(user, client);
            return ResponseEntity.ok("Клиент успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable long id){
        try{
            clientService.deleteClient(id);
            return ResponseEntity.ok("Клиент успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/only/{id}")
    public ResponseEntity deleteOnlyClient(@AuthenticationPrincipal User user, @PathVariable long id){
        try{
            clientService.deleteOnlyClient(id, user);
            return ResponseEntity.ok("Клиент успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}