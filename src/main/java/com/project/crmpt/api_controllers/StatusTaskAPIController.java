package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Status_deal;
import com.project.crmpt.entity.Status_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.StatusTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/status_task")
public class StatusTaskAPIController {

    @Autowired
    private StatusTaskService statusService;

    @GetMapping
    public List<TypeStatusModel> StatusDealsGet (@AuthenticationPrincipal User user) {
        return statusService.getStatusTasks(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel statusDealGet (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return statusService.getStatusTask(id, user);
    }

    @PostMapping
    public ResponseEntity addStatusTask(@AuthenticationPrincipal User user, @RequestBody Status_task status){
        try{
            statusService.addStatusTask(status, user);
            return ResponseEntity.ok("Статус задачи успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateStatusTask(@AuthenticationPrincipal User user, @RequestBody Status_task status){
        try{
            statusService.updateStatusTask(status, user);
            return ResponseEntity.ok("Статус задачи успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStatusTask(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            statusService.deleteStatusTask(id, user);
            return ResponseEntity.ok("Статус задачи успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}