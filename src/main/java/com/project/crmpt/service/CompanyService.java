package com.project.crmpt.service;

import com.project.crmpt.entity.Client;
import com.project.crmpt.entity.Company;
import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.CompanyModel;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.ClientRepo;
import com.project.crmpt.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private ClientRepo clientRepo;

    public List<CompanyModel> getCompanies (User user){
        List<CompanyModel> model = new ArrayList<CompanyModel>();
        List<Company> companies = companyRepo.findByCompanyAll(user.getID());
        for(int i = 0; i < companies.size(); i++){
            Company company = companies.get(i);
            CompanyModel companyModel = new CompanyModel(company.getID(),company.getTitle(), company.getCity(), company.getPhone(),
                    company.getEmail(), company.getDate_creates());
            model.add(companyModel);
        }
        return model;
    }

    public CompanyModel getCompany (Long id, User user){
        Company company = companyRepo.findByCompany(id, user.getID());
        CompanyModel companyModel = new CompanyModel(company.getID(), company.getTitle(), company.getCity(), company.getPhone(),
                company.getEmail(), company.getDate_creates());
        return companyModel;
    }

    public Company addCompany(Company company, User user) throws ExceptionMessage {
        if(companyRepo.findByTitleCompany(company.getTitle(), user.getID())!= null)
            throw new ExceptionMessage("Компания с таким названием уже существует");
        if(company.getPhone() != "") {
            if (companyRepo.findByPhoneCompany(company.getPhone(), user.getID()) != null)
                throw new ExceptionMessage("Компания с таким телефоном уже существует");
        }
        if(company.getEmail() != "") {
            if (companyRepo.findByEmailCompany(company.getEmail(), user.getID()) != null)
                throw new ExceptionMessage("Компания с таким email уже существует");
        }
        company.setDate_creates(companyRepo.getByDate());
        company.setUser(user);
        return companyRepo.save(company);
    }

    public Company updateCompany(Company company, User user) throws ExceptionMessage {
        Company companyUse = companyRepo.findByCompany(company.getID(), user.getID());
        if(!Objects.equals(companyUse.getTitle(), company.getTitle())) {
            if (companyRepo.findByTitleCompany(company.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Компания с таким названием уже существует");
        }
        if(!Objects.equals(companyUse.getPhone(), company.getPhone())) {
            if(company.getPhone() != "") {
                if (companyRepo.findByPhoneCompany(company.getPhone(), user.getID()) != null)
                    throw new ExceptionMessage("Компания с таким телефоном уже существует");
            }
        }
        if(!Objects.equals(companyUse.getEmail(), company.getEmail())) {
            if(company.getEmail() != "") {
                if (companyRepo.findByEmailCompany(company.getEmail(), user.getID()) != null)
                    throw new ExceptionMessage("Компания с таким email уже существует");
            }
        }
        companyUse.setTitle(company.getTitle());
        companyUse.setCity(company.getCity());
        companyUse.setPhone(company.getPhone());
        companyUse.setEmail(company.getEmail());
        return companyRepo.save(companyUse);
    }

    public String deleteCompany(Long id, User user) throws  ExceptionMessage{
        List<Client> clients = clientRepo.findByCompany(id, user.getID());
        for(int i = 0; i < clients.size(); i++){
            Client client = clients.get(i);
            client.setCompany(null);
            clientRepo.save(client);
        }
        companyRepo.deleteById(id);
        return "Компания успешно удалена";
    }
}