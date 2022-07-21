package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TypeDealRepo typeRepo;

    @Autowired
    private StatusDealRepo statusRepo;

    public List<DealModel> loop(List<Deal> deals, Long user_id){
        List<DealModel> model = new ArrayList<>();
        for(int i = 0; i < deals.size(); i++){
            Deal deal = deals.get(i);
            Type_deal type = new Type_deal();
            Status_deal status = new Status_deal();
            Client client = new Client();
            if(dealRepo.findByDeal(deal.getID(), user_id) != null)
                deal = dealRepo.findByDeal(deal.getID(), user_id);
            if(deal.getType_deal() != null)
                type = typeRepo.findByTypeDeal(deal.getType_deal().getID(), user_id);
            if(deal.getStatus_deal() != null)
                status = statusRepo.findStatusDeal(deal.getStatus_deal().getID(), user_id);
            if(deal.getClient() != null)
                client = clientRepo.findByClient(deal.getClient().getID(), user_id);
            String closed;
            if(deal.getClosed() == true)
                closed = "Закрыта";
            else
                closed = "Сделка открыта";
            String name = client.getSurname() + " " + client.getName() + " " + client.getMiddle_name();
            DealModel dealModel = new DealModel(deal.getID(), deal.getTitle(), deal.getDescription(), deal.getTotal(),
                    deal.getDate_creates(), closed, type.getTitle(), status.getTitle(), name, client.getID());
            model.add(dealModel);
        }
        return model;
    }

    public List<DealModel> getDeals(Long user_id) {
        List<Deal> deals = dealRepo.findByDealAll(user_id);
        return loop(deals, user_id);
    }

    public List<DealModel> getDealsByType(Long id, Long user_id) {
        List<Deal> deals = dealRepo.findByTypeDeal(id, user_id);
        return loop(deals, user_id);
    }

    public List<DealModel> getDealsByStatus(Long id, Long user_id) {
        List<Deal> deals = dealRepo.findByStatusDeal(id, user_id);
        return loop(deals, user_id);
    }

    public List<DealModel> getDealsByClient(Long id, Long user_id) {
        List<Deal> deals = dealRepo.findDealByClientId(id, user_id);
        return loop(deals, user_id);
    }

    public DealModel getDeal(Long id, Long user_id) {
        Deal deal = new Deal();
        Type_deal type = new Type_deal();
        Status_deal status = new Status_deal();
        Client client = new Client();
        if(dealRepo.findByDeal(id, user_id) != null)
            deal = dealRepo.findByDeal(id, user_id);
        if(deal.getType_deal() != null)
            type = typeRepo.findByTypeDeal(deal.getType_deal().getID(), user_id);
        if(deal.getStatus_deal() != null)
            status = statusRepo.findStatusDeal(deal.getStatus_deal().getID(), user_id);
        if(deal.getClient() != null)
            client = clientRepo.findByClient(deal.getClient().getID(), user_id);
        String closed;
        if(deal.getClosed() == true)
            closed = "Закрыта";
        else
            closed = "Сделка открыта";
        String name = client.getSurname() + " " + client.getName() + " " + client.getMiddle_name();
        DealModel dealModel = new DealModel(deal.getID(), deal.getTitle(), deal.getDescription(), deal.getTotal(),
                deal.getDate_creates(), closed, type.getTitle(), status.getTitle(), name, client.getID());
        return dealModel;
    }

    public void addDeal(DealModel model, User user) throws ExceptionMessage{
        Deal deal = new Deal();
        Type_deal type = typeRepo.findByTitleType(model.getType_deal(), user.getID());
        Status_deal status = statusRepo.findByTitleStatus(model.getStatus_deal(), user.getID());
        Client client = clientRepo.findByClient(model.getClient_id(), user.getID());
        deal.setTitle(model.getTitle());
        deal.setDescription(model.getDescription());
        deal.setTotal(model.getTotal());
        deal.setDate_creates(dealRepo.getByDate());
        deal.setClosed(false);
        deal.setType_deal(type);
        deal.setStatus_deal(status);
        deal.setClient(client);
        deal.setUser(user);
        dealRepo.save(deal);
    }

    public void addClientTask(Long id, User user, Client client){
        List<Task> tasks = taskRepo.findByDealId(id, user.getID());
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            task.setClient(client);
            taskRepo.save(task);
        }
    }

    public Deal updateDeal(DealModel model, User user) throws ExceptionMessage {
        Deal deal = dealRepo.findByDeal(model.getID(), user.getID());
        if(deal.getClosed() == true)
            throw new ExceptionMessage("Когда сделка закрыта, её нельзя редактировать");
        Type_deal type = typeRepo.findByTitleType(model.getType_deal(), user.getID());
        Status_deal status = statusRepo.findByTitleStatus(model.getStatus_deal(), user.getID());
        Client client = clientRepo.findByClient(model.getClient_id(), user.getID());
        deal.setTitle(model.getTitle());
        deal.setDescription(model.getDescription());
        deal.setTotal(model.getTotal());
        deal.setType_deal(type);
        deal.setStatus_deal(status);
        deal.setClient(client);
        addClientTask(deal.getID(), user, client);
        return dealRepo.save(deal);
    }

    public Boolean isClosed(Long id, User user) {
        Deal deal = dealRepo.findByDeal(id, user.getID());
        if(deal.getClosed() == false)
            deal.setClosed(true);
        else
            deal.setClosed(false);
        dealRepo.save(deal);
        return deal.getClosed();
    }

    public String deleteDeal(Long id) throws ExceptionMessage {
        dealRepo.deleteById(id);
        return "Сделка успешно удалена";
    }
}