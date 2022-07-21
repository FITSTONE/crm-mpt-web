package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.ClientService;
import com.project.crmpt.service.TypeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/type_client")
public class TypeClientAPIController {

    @Autowired
    private TypeClientService typeService;

    @GetMapping
    public List<TypeStatusModel> getTypeClient (@AuthenticationPrincipal User user) {
        return typeService.getTypeClients(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel getTypeClient (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return typeService.getTypeClient(id,user);
    }

    @PostMapping
    public ResponseEntity addTypeClient(@AuthenticationPrincipal User user, @RequestBody Type_client type){
        try{
            typeService.addTypeClient(type, user);
            return ResponseEntity.ok("Тип клиента успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateTypeClient(@AuthenticationPrincipal User user, @RequestBody Type_client type){
        try{
            typeService.updateTypeClient(type, user);
            return ResponseEntity.ok("Тип клиента успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTypeClient(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            typeService.deleteTypeClient(id, user);
            return ResponseEntity.ok("Тип клиента успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}