package com.project.crmpt.api_controllers;

import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.entity.User;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.models.UserModel;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserAPIController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User getUser(@AuthenticationPrincipal User user)  {
        return user;
    }

    @PutMapping
    public ResponseEntity updateAccount(@AuthenticationPrincipal User id, @RequestBody User user){
        try{
            userService.updateAccount(id, user);
            return ResponseEntity.ok("Ваши данные успешно обновлен");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/block")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user
            , @RequestParam Boolean active){
        try{
            userService.blockAccount(user, active);
            if(active == false)
                return ResponseEntity.ok("Ваш аккаунт был заблокирован");
            else
                return ResponseEntity.ok("Ваш аккаунт был разблокирован");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity deleteAccount(@AuthenticationPrincipal User user){
        try{
            userService.deleteAccount(user);
            return ResponseEntity.ok("Ваш аккаунт успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}