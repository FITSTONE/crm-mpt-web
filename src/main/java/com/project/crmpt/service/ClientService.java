package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private TypeClientRepo typeClientRepo;

    @Autowired
    private StatusClientRepo statusClientRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private TaskRepo taskRepo;

    public List<ClientModel> loop(List<Client> clients, Long user_id){
        List<ClientModel> model = new ArrayList<ClientModel>();
        for(int i = 0; i < clients.size(); i++){
            Client client = clients.get(i);
            Type_client type = new Type_client();
            Status_client status = new Status_client();
            Company company = new Company();
            if(clientRepo.findByClient(client.getID(), user_id) != null)
                client = clientRepo.findByClient(client.getID(), user_id);
            if(client.getType() != null)
                type = typeClientRepo.findByTypeClient(client.getType().getID(), user_id);
            if(client.getStatus() != null)
                status = statusClientRepo.findByStatusClient(client.getStatus().getID(), user_id);
            if(client.getCompany() != null)
                company = companyRepo.findByCompany(client.getCompany().getID(), user_id) ;
            ClientModel clientModel = new ClientModel(client.getID(), client.getName(),client.getSurname(),client.getMiddle_name(),
                    client.getPhone(), client.getEmail(),type.getTitle(), status.getTitle(), company.getTitle());
            model.add(clientModel);
        }
        return model;
    }

    public List<ClientModel> getClientsByTypeClient(Long type_id, User user) {
        List<Client> clients = clientRepo.findByTypeClient(type_id, user.getID());
        return loop(clients, user.getID());
    }

    public List<ClientModel> getClientsByStatusClient(Long status_id, User user) {
        List<Client> clients = clientRepo.findByStatusClient(status_id, user.getID());
        return loop(clients, user.getID());
    }

    public List<ClientModel> getClientsByCompany(Long company_id, User user) {
        List<Client> clients = clientRepo.findByCompany(company_id, user.getID());
        return loop(clients, user.getID());
    }

    public List<ClientModel> getClients(Long user_id) {
        List<Client> clients = clientRepo.findByUserId(user_id);
        return loop(clients, user_id);
    }

    public ClientModel getClient(Long id, Long user_id) {
        Client client = new Client();
        Type_client type = new Type_client();
        Status_client status = new Status_client();
        Company company = new Company();
        if(clientRepo.findByClient(id, user_id) != null)
            client = clientRepo.findByClient(id, user_id);
        if(client.getType() != null)
            type = typeClientRepo.findByTypeClient(client.getType().getID(), user_id);
        if(client.getStatus() != null)
            status = statusClientRepo.findByStatusClient(client.getStatus().getID(), user_id);
        if(client.getCompany() != null)
            company = companyRepo.findByCompany(client.getCompany().getID(), user_id) ;
        ClientModel model = new ClientModel(id,client.getName(),client.getSurname(),client.getMiddle_name(),
                client.getPhone(), client.getEmail(),type.getTitle(), status.getTitle(), company.getTitle());
        return model;
    }

    public Client addClient(User user, ClientModel client) throws ExceptionMessage {
        if(client.getPhone() != "") {
            if (clientRepo.findClientByPhone(client.getPhone(), user.getID()) != null)
                throw new ExceptionMessage("Клиент с таким телефон уже существует");
        }
        if(client.getEmail() != "") {
            if (clientRepo.findClientByEmail(client.getEmail(), user.getID()) != null)
                throw new ExceptionMessage("Клиент с таким email уже существует");
        }
        Client entity = new Client();
        Type_client type_client = typeClientRepo.findByTitleType(client.getType_client(), user.getID());
        Status_client status_client = statusClientRepo.findByTitleStatus(client.getStatus_client(), user.getID());
        Company com = companyRepo.findByTitleCompany(client.getCompany(), user.getID());
        entity.setName(client.getName());
        entity.setSurname(client.getSurname());
        entity.setMiddle_name(client.getMiddle_name());
        entity.setPhone(client.getPhone());
        entity.setEmail(client.getEmail());
        entity.setType(type_client);
        entity.setStatus(status_client);
        entity.setCompany(com);
        entity.setUser(user);
        return clientRepo.save(entity);
    }

    public Client updateClient(User user, ClientModel client) throws ExceptionMessage {
        if(clientRepo.findByClient(client.getID(), user.getID()) == null)
            throw new ExceptionMessage("Такого клиента не существует");
        Client client_check_repeat = clientRepo.findByClient(client.getID(), user.getID());
        if(!Objects.equals(client_check_repeat.getPhone(), client.getPhone())){
            if(client.getPhone() != "") {
                if (clientRepo.findClientByPhone(client.getPhone(), user.getID()) != null)
                    throw new ExceptionMessage("Клиент с таким телефон уже существует");
            }
        }
        if(!Objects.equals(client_check_repeat.getEmail(), client.getEmail())) {
            if(client.getEmail() != "") {
                if (clientRepo.findClientByEmail(client.getEmail(), user.getID()) != null)
                    throw new ExceptionMessage("Клиент с таким email уже существует");
            }
        }
        Client entity = clientRepo.findByClient(client.getID(), user.getID());
        Type_client type_client = typeClientRepo.findByTitleType(client.getType_client(), user.getID());
        Status_client status_client = statusClientRepo.findByTitleStatus(client.getStatus_client(), user.getID());
        Company com = companyRepo.findByTitleCompany(client.getCompany(), user.getID());
        entity.setName(client.getName());
        entity.setSurname(client.getSurname());
        entity.setMiddle_name(client.getMiddle_name());
        entity.setPhone(client.getPhone());
        entity.setEmail(client.getEmail());
        entity.setType(type_client);
        entity.setStatus(status_client);
        entity.setCompany(com);
        return clientRepo.save(entity);
    }

    public String deleteOnlyClient(Long id, User user) throws  ExceptionMessage{
        List<Deal> deals = dealRepo.findDealByClientId(id, user.getID());
        for(int i = 0; i < deals.size(); i++){
            Deal deal = deals.get(i);
            deal.setClient(null);
            dealRepo.save(deal);
        }
        List<Task> tasks = taskRepo.findByClientId(id, user.getID());
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.getDeal() != null)
                task.setClient(null);
            taskRepo.save(task);
        }
        clientRepo.deleteById(id);
        return "Клиент удален";
    }

    public String deleteClient(long id) throws ExceptionMessage {
        clientRepo.deleteById(id);
        return "Клиент удален";
    }
}