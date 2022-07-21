package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.Company;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.models.CompanyModel;
import com.project.crmpt.service.ClientService;
import com.project.crmpt.service.CompanyService;
import com.project.crmpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private String messageError;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String getCompanies(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Company", companyService.getCompanies(user));
        messageError = null;
        return "company/companyList";
    }

    @GetMapping("/{id}")
    public String getCompany(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Company", companyService.getCompany(id, user));
        model.addAttribute("Client", clientService.getClientsByCompany(id, user));
        messageError = null;
        return "company/companyDetails";
    }

    @GetMapping("/add")
    public String getAddCompany(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Error", messageError);
        return "company/companyAdd";
    }

    @PostMapping("/add")
    public String addCompany(@AuthenticationPrincipal User user,
                            @RequestParam String title, @RequestParam String city,
                            @RequestParam String phone, @RequestParam String email){
        try{
            Company company = new Company(title, city, phone, email);
            companyService.addCompany(company, user);
            messageError = null;
            return "redirect:/company";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/company/add";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateCompany(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Company", companyService.getCompany(id, user));
        model.addAttribute("Error", messageError);
        return "company/companyUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateCompany(@AuthenticationPrincipal User user, @PathVariable Long id,
                             @RequestParam String title, @RequestParam String city,
                             @RequestParam String phone, @RequestParam String email){
        try{
            Company company = new Company(id, title, city, phone, email);
            companyService.updateCompany(company, user);
            messageError = null;
            return "redirect:/company/{id}";
        }
        catch (ExceptionMessage e){
            messageError = e.getMessage();
            return "redirect:/company/update/{id}";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCompany(@AuthenticationPrincipal User user, @PathVariable Long id){
        try{
            companyService.deleteCompany(id, user);
            return "redirect:/company";
        }
        catch (ExceptionMessage e){
            return "redirect:/company/{id}";
        }
    }
}
