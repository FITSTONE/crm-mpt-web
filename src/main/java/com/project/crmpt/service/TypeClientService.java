package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.ClientRepo;
import com.project.crmpt.repository.TypeClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TypeClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private TypeClientRepo typeClientRepo;

    public List<TypeStatusModel> getTypeClients (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Type_client> type = typeClientRepo.findByTypeClientAll(user.getID());
        for(int i = 0; i < type.size(); i++){
            Type_client typeClient = type.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(typeClient.getID(), typeClient.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getTypeClient (Long id, User user){
        Type_client type_client = typeClientRepo.findByTypeClient(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(type_client.getID(), type_client.getTitle());
        return typeModel;
    }

    public Type_client addTypeClient(Type_client type, User user) throws ExceptionMessage {
        if(typeClientRepo.findByTitleType(type.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой тип клиента уже существует");
        type.setUser(user);
        return typeClientRepo.save(type);
    }

    public Type_client updateTypeClient(Type_client type, User user) throws ExceptionMessage {
        Type_client type_check_repeat = typeClientRepo.findByTypeClient(type.getID(), user.getID());
        if(!Objects.equals(type_check_repeat.getTitle(), type.getTitle())){
            if(typeClientRepo.findByTitleType(type.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Такой тип клиента уже существует");
        }
        Type_client type_client = typeClientRepo.findByTypeClient(type.getID(), user.getID());
        type_client.setTitle(type.getTitle());
        return typeClientRepo.save(type_client);
    }

    public String deleteTypeClient(Long id, User user) throws  ExceptionMessage{
        List<Client> clients = clientRepo.findByTypeClient(id, user.getID());
        for(int i = 0; i < clients.size(); i++){
            Client client = clients.get(i);
            client.setType(null);
            clientRepo.save(client);
        }
        typeClientRepo.deleteById(id);
        return "Тип клиента успешно удален";
    }
}