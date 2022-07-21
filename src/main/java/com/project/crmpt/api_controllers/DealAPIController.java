package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.service.DealService;
import com.project.crmpt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/deal")
public class DealAPIController {

    @Autowired
    private DealService dealService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Iterable<DealModel> getDeals(@AuthenticationPrincipal User user)  {
        return dealService.getDeals(user.getID());
    }

    @GetMapping("/{id}")
    public DealModel getDeal(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return dealService.getDeal(id, user.getID());
    }

    @GetMapping("/type/{id}")
    public Iterable<DealModel> getDealByType(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return dealService.getDealsByType(id, user.getID());
    }

    @GetMapping("/status/{id}")
    public Iterable<DealModel> getDealByStatus(@PathVariable Long id, @AuthenticationPrincipal User user)  {
        return dealService.getDealsByStatus(id, user.getID());
    }

    @GetMapping("task/{id}")
    public Iterable<TaskModel> getTaskByDeal(@AuthenticationPrincipal User user, @PathVariable Long id)  {
        return taskService.getTaskByDeal(id, user.getID());
    }

    @PostMapping
    public ResponseEntity addDeal(@AuthenticationPrincipal User user,
                                  @RequestBody DealModel deal){
        try{
            dealService.addDeal(deal, user);
            return ResponseEntity.ok("Сделка успешно создана");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateDeal(@AuthenticationPrincipal User user,
                                     @RequestBody DealModel deal){
        try{
            dealService.updateDeal(deal, user);
            return ResponseEntity.ok("Сделка успешно обновлена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity closeDeal(@PathVariable Long id, @AuthenticationPrincipal User user){
        try{
            Boolean check = dealService.isClosed(id, user);
            if(check)
                return ResponseEntity.ok("Сделка успешно закрыта");
            else
                return ResponseEntity.ok("Сделка успешно открыта");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeal(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            dealService.deleteDeal(id);
            return ResponseEntity.ok("Сделка успешно удалена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}