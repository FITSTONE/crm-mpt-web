package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class RegistrationAPIController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registrationAccount(@RequestBody User user){
        try{
            Long id = userService.registrationAccount(user);
            userService.registrationDataByAccount(user);
            return ResponseEntity.ok("Ваш аккаунт успешно создан. Для активации аккаунта пожалуйста, проверьте почту");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}