package com.project.crmpt.api_controllers;

import com.project.crmpt.entity.Company;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.CompanyModel;
import com.project.crmpt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/company")
public class CompanyAPIController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyModel> getCompanies (@AuthenticationPrincipal User user) {
        return companyService.getCompanies(user);
    }

    @GetMapping("/{id}")
    public CompanyModel getCompany (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return companyService.getCompany(id, user);
    }

    @PostMapping
    public ResponseEntity addCompany (@AuthenticationPrincipal User user, @RequestBody Company company){
        try{
            companyService.addCompany(company, user);
            return ResponseEntity.ok("Компания успешно добавлена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateCompany (@AuthenticationPrincipal User user, @RequestBody Company company){
        try{
            companyService.updateCompany(company, user);
            return ResponseEntity.ok("Компания успешно обновлена");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompany (@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            companyService.deleteCompany(id, user);
            return ResponseEntity.ok("Компания успешно удален");
        }
        catch (ExceptionMessage e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}