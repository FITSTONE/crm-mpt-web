package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Type_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.service.TypeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/type_task")
public class TypeTaskAPIController {

    @Autowired
    private TypeTaskService typeService;

    @GetMapping
    public List<TypeStatusModel> typeTaskssGet (@AuthenticationPrincipal User user) {
        return typeService.getTypeTasks(user);
    }

    @GetMapping("/{id}")
    public TypeStatusModel typeTaskGet (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return typeService.getTypeTask(id, user);
    }

    @PostMapping
    public ResponseEntity addTypeTask(@AuthenticationPrincipal User user, @RequestBody Type_task type){
        try{
            typeService.addTypeTask(type, user);
            return ResponseEntity.ok("Тип задачи успешно добавлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateTypeTask(@AuthenticationPrincipal User user, @RequestBody Type_task type){
        try{
            typeService.updateTypeTask(type, user);
            return ResponseEntity.ok("Тип задачи успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTypeTask(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            typeService.deleteTypeTask(id, user);
            return ResponseEntity.ok("Тип задачи успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}