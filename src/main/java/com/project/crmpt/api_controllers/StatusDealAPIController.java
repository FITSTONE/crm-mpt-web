package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Status_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.StatusDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/status_deal")
public class StatusDealAPIController {

    @Autowired
    private StatusDealService statusService;

    @GetMapping
    public List<TypeStatusModel> StatusDealsGet (@AuthenticationPrincipal User user) {
        return statusService.getStatusDeals(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel statusDealGet (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return statusService.getStatusDeal(id, user);
    }

    @PostMapping
    public ResponseEntity addStatusClient(@AuthenticationPrincipal User user, @RequestBody Status_deal status){
        try{
            statusService.addStatusDeal(status, user);
            return ResponseEntity.ok("Статус сделки успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateStatusClient(@AuthenticationPrincipal User user, @RequestBody Status_deal status){
        try{
            statusService.updateStatusDeal(status, user);
            return ResponseEntity.ok("Статус сделки успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStatusClient(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            statusService.deleteStatusDeal(id, user);
            return ResponseEntity.ok("Статус сделки успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}