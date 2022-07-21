package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.Type_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.TypeDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/type_deal")
public class TypeDealAPIController {

    @Autowired
    private TypeDealService typeService;

    @GetMapping
    public List<TypeStatusModel> getTypeDeals (@AuthenticationPrincipal User user) {
        return typeService.getTypeDeals(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel getTypeDeal (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return typeService.getTypeDeal(id,user);
    }

    @PostMapping
    public ResponseEntity addTypeDeal(@AuthenticationPrincipal User user, @RequestBody Type_deal type){
        try{
            typeService.addTypeDeal(type, user);
            return ResponseEntity.ok("Тип сделки успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateTypeDeal(@AuthenticationPrincipal User user, @RequestBody Type_deal type){
        try{
            typeService.updateTypeDeal(type, user);
            return ResponseEntity.ok("Тип сделки успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTypeDeal(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            typeService.deleteTypeDeal(id, user);
            return ResponseEntity.ok("Тип сделки успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}