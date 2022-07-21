package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.ClientService;
import com.project.crmpt.service.StatusClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/status_client")
public class StatusClientAPIController {

    @Autowired
    private StatusClientService statusService;

    @GetMapping
    public List<TypeStatusModel> StatusClientsGet (@AuthenticationPrincipal User user) {
        return statusService.getStatusClients(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel statusClientGet (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return statusService.getStatusClient(id,user);
    }

    @PostMapping
    public ResponseEntity addStatusClient(@AuthenticationPrincipal User user, @RequestBody Status_client status){
        try{
            statusService.addStatusClient(status, user);
            return ResponseEntity.ok("Статус клиента успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateStatusClient(@AuthenticationPrincipal User user, @RequestBody Status_client status){
        try{
            statusService.updateStatusClient(status, user);
            return ResponseEntity.ok("Статус клиента успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStatusClient(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            statusService.deleteStatusClient(id, user);
            return ResponseEntity.ok("Статус клиента успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}