package com.project.crmpt.service;

import com.project.crmpt.entity.Deal;
import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Status_deal;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.DealRepo;
import com.project.crmpt.repository.StatusDealRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StatusDealService {

    @Autowired
    private StatusDealRepo statusRepo;

    @Autowired
    private DealRepo dealRepo;

    public List<TypeStatusModel> getStatusDeals (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Status_deal> status = statusRepo.findStatusDealsAll(user.getID());
        for(int i = 0; i < status.size(); i++){
            Status_deal statusDeal = status.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(statusDeal.getID(), statusDeal.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getStatusDeal (Long id, User user){
        Status_deal status = statusRepo.findStatusDeal(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(status.getID(), status.getTitle());
        return typeModel;
    }

    public Status_deal addStatusDeal (Status_deal status, User user) throws ExceptionMessage {
        if(statusRepo.findByTitleStatus(status.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой статус сделки уже существует");
        status.setUser(user);
        return statusRepo.save(status);
    }

    public Status_deal updateStatusDeal (Status_deal status, User user) throws ExceptionMessage {
        Status_deal statusDeal = statusRepo.findStatusDeal(status.getID(), user.getID());
        if(!Objects.equals(statusDeal.getTitle(), status.getTitle())){
            if(statusRepo.findByTitleStatus(status.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Такой статус сделки уже существует");
        }
        statusDeal.setTitle(status.getTitle());
        return statusRepo.save(statusDeal);
    }

    public String deleteStatusDeal(Long id, User user) throws  ExceptionMessage{
        List<Deal> deals = dealRepo.findByStatusDeal(id, user.getID());
        for(int i = 0; i < deals.size(); i++){
            Deal deal = deals.get(i);
            deal.setStatus_deal(null);
            dealRepo.save(deal);
        }
        statusRepo.deleteById(id);
        return "Статус сделки успешно удален";
    }
}