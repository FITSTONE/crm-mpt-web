package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.DealRepo;
import com.project.crmpt.repository.TypeDealRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TypeDealService {

    @Autowired
    private TypeDealRepo typeDealRepo;

    @Autowired
    private DealRepo dealRepo;

    public List<TypeStatusModel> getTypeDeals (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Type_deal> type = typeDealRepo.findByTypeDealAll(user.getID());
        for(int i = 0; i < type.size(); i++){
            Type_deal typeDeal = type.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(typeDeal.getID(),typeDeal.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getTypeDeal (Long id, User user){
        Type_deal typeDeal = typeDealRepo.findByTypeDeal(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(typeDeal.getID(),typeDeal.getTitle());
        return typeModel;
    }

    public Type_deal addTypeDeal(Type_deal type, User user) throws ExceptionMessage {
        if(typeDealRepo.findByTitleType(type.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой тип сделки уже существует");
        type.setUser(user);
        return typeDealRepo.save(type);
    }

    public Type_deal updateTypeDeal(Type_deal type, User user) throws ExceptionMessage {
        Type_deal type_deal = typeDealRepo.findByTypeDeal(type.getID(), user.getID());
        if(!Objects.equals(type_deal.getTitle(), type.getTitle())){
            if(typeDealRepo.findByTitleType(type.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Такой тип сделки уже существует");
        }
        type_deal.setTitle(type.getTitle());
        return typeDealRepo.save(type_deal);
    }

    public String deleteTypeDeal(Long id, User user) throws  ExceptionMessage{
        List<Deal> deals = dealRepo.findByTypeDeal(id, user.getID());
        for(int i = 0; i < deals.size(); i++){
            Deal deal = deals.get(i);
            deal.setType_deal(null);
            dealRepo.save(deal);
        }
        typeDealRepo.deleteById(id);
        return "Тип сделки успешно удален";
    }
}