package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/task")
public class TaskAPIController {

    @Autowired
    private TaskService taskService;

    @GetMapping("type/{id}")
    public Iterable<TaskModel> getTaskByType(@AuthenticationPrincipal User user, @PathVariable Long id)  {
        return taskService.getTaskByType(id, user.getID());
    }

    @GetMapping("status/{id}")
    public Iterable<TaskModel> getTaskByStatus(@AuthenticationPrincipal User user, @PathVariable Long id)  {
        return taskService.getTaskByStatus(id, user.getID());
    }

    @GetMapping
    public Iterable<TaskModel> getTasks(@AuthenticationPrincipal User user)  {
        return taskService.getTasks(user.getID());
    }

    @GetMapping("/{id}")
    public TaskModel getTask(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return taskService.getTask(id, user.getID());
    }

    @PostMapping
    public ResponseEntity addTask(@AuthenticationPrincipal User user,
                                    @RequestBody TaskModel task){
        try{
            taskService.addTask(user, task);
            return ResponseEntity.ok("Задача успешно создана");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateTask(@AuthenticationPrincipal User user,
                                    @RequestBody TaskModel task){
        try{
            taskService.updateTask(user, task);
            return ResponseEntity.ok("Задача успешно обновлена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        try{
            taskService.deleteTask(id);
            return ResponseEntity.ok("Задача успешно удалена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}