package com.project.crmpt.service;

import com.project.crmpt.entity.Client;
import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Type_client;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.ClientRepo;
import com.project.crmpt.repository.StatusClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StatusClientService {

    @Autowired
    private StatusClientRepo statusClientRepo;

    @Autowired
    private ClientRepo clientRepo;

    public List<TypeStatusModel> getStatusClients (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Status_client> status = statusClientRepo.findByStatusClientAll(user.getID());
        for(int i = 0; i < status.size(); i++){
            Status_client statusClient = status.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(statusClient.getID(), statusClient.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getStatusClient (Long id, User user){
        Status_client status_client = statusClientRepo.findByStatusClient(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(status_client.getID(), status_client.getTitle());
        return typeModel;
    }

    public Status_client addStatusClient(Status_client status, User user) throws ExceptionMessage {
        if(statusClientRepo.findByTitleStatus(status.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой статус клиента уже существует");
        status.setUser(user);
        return statusClientRepo.save(status);
    }

    public Status_client updateStatusClient(Status_client status, User user) throws ExceptionMessage {
        Status_client status_client = statusClientRepo.findByStatusClient(status.getID(), user.getID());
        if(!Objects.equals(status_client.getTitle(), status.getTitle())){
            if(statusClientRepo.findByTitleStatus(status.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Такой статус клиента уже существует");
        }
        status_client.setTitle(status.getTitle());
        return statusClientRepo.save(status_client);
    }

    public String deleteStatusClient(Long id, User user) throws  ExceptionMessage{
        List<Client> clients = clientRepo.findByStatusClient(id, user.getID());
        for(int i = 0; i < clients.size(); i++){
            Client client = clients.get(i);
            client.setStatus(null);
            clientRepo.save(client);
        }
        statusClientRepo.deleteById(id);
        return "Статус клиента успешно удален";
    }
}