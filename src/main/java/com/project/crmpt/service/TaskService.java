package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.ClientModel;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TypeTaskRepo typeRepo;

    @Autowired
    private StatusTaskRepo statusRepo;

    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private ClientRepo clientRepo;

    public List<TaskModel> loop(List<Task> tasks, Long user_id){
        List<TaskModel> model = new ArrayList<>();
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            Type_task type = new Type_task();
            Status_task status = new Status_task();
            Deal deal = new Deal();
            Client client = new Client();
            timeOut(task, user_id);
            if(taskRepo.findByTask(task.getID(), user_id) != null)
                task = taskRepo.findByTask(task.getID(), user_id);
            if(task.getType_task() != null)
                type = typeRepo.findByTypeTask(task.getType_task().getID(), user_id);
            if(task.getStatus_task() != null)
                status = statusRepo.findStatusTask(task.getStatus_task().getID(), user_id);
            if(task.getDeal() != null)
                deal = dealRepo.findByDeal(task.getDeal().getID() , user_id);
            if(task.getClient() != null)
                client = clientRepo.findByClient(task.getClient().getID(), user_id);
            String name = client.getSurname() + " " + client.getName() + " " + client.getMiddle_name();
            TaskModel taskModel = new TaskModel(task.getID(), task.getTitle(), task.getDate_creates(),
                    task.getDate_end(), type.getTitle(), status.getTitle(), deal.getTitle(), deal.getID(),
                    name, client.getID());
            model.add(taskModel);
        }
        return model;
    }

    public List<TaskModel> getTasks(Long user_id) {
        List<Task> tasks = taskRepo.findStatusTasksAll(user_id);
        return loop(tasks, user_id);
    }

    public List<TaskModel> getTaskByType(Long id, Long user_id) {
        List<Task> tasks = taskRepo.findByTypeTask(id, user_id);
        return loop(tasks, user_id);
    }

    public List<TaskModel> getTaskByStatus(Long id, Long user_id) {
        List<Task> tasks = taskRepo.findByStatusTask(id, user_id);
        return loop(tasks, user_id);
    }

    public List<TaskModel> getTaskByDeal(Long id, Long user_id) {
        List<Task> tasks = taskRepo.findByDealId(id, user_id);
        return loop(tasks, user_id);
    }

    public List<TaskModel> getTaskByClient(Long id, Long user_id){
        List<Task> tasks = taskRepo.findByClientId(id, user_id);
        return loop(tasks, user_id);
    }

    public TaskModel getTask(Long id, Long user_id) {
        Task task = new Task();
        Type_task type = new Type_task();
        Status_task status = new Status_task();
        Deal deal = new Deal();
        Client client = new Client();
        if(taskRepo.findByTask(id, user_id) != null)
            task = taskRepo.findByTask(id, user_id);
        if(task.getType_task() != null)
            type = typeRepo.findByTypeTask(task.getType_task().getID(), user_id);
        if(task.getStatus_task() != null)
            status = statusRepo.findStatusTask(task.getStatus_task().getID(), user_id);
        if(task.getDeal() != null)
            deal = dealRepo.findByDeal(task.getDeal().getID() , user_id);
        if(task.getClient() != null)
            client = clientRepo.findByClient(task.getClient().getID(), user_id);
        String name = client.getSurname() + " " + client.getName() + " " + client.getMiddle_name();
        TaskModel model = new TaskModel(task.getID(), task.getTitle(), task.getDate_creates(),
                task.getDate_end(), type.getTitle(), status.getTitle(), deal.getTitle(), deal.getID(),
                name, client.getID());
        return model;
    }

    public Task timeOut(Task task, Long user_id){
        task = taskRepo.findByTask(task.getID(), user_id);
        Long different = taskRepo.getDifferenceDate(task.getDate_end());
        if(different != null) {
            if (different <= 0)
                task.setStatus_task(statusRepo.findStatusTaskByTitleStatus("Просрочено", user_id));
        }
        return taskRepo.save(task);
    }

    public Task addTask(User user, TaskModel model) throws ExceptionMessage {
        Task task = new Task();
        Type_task type = typeRepo.findByTitleType(model.getType(), user.getID());
        Status_task status = statusRepo.findStatusTaskByTitleStatus(model.getStatus(), user.getID());
        Deal deal = dealRepo.findByDeal(model.getDeal_id(), user.getID());
        Client client = clientRepo.findByClient(model.getClient_id(), user.getID());
        task.setTitle(model.getTitle());
        task.setDate_creates(taskRepo.getByDate());
        task.setDate_end(model.getDate_end());
        task.setStatus_task(status);
        task.setType_task(type);
        task.setDeal(deal);
        if(deal != null) {
            task.setClient(deal.getClient());
        }
        else{
            if(client != null)
                task.setClient(client);
            else
                task.setClient(null);
        }
        task.setUser(user);
        return taskRepo.save(task);
    }

    public Task updateTask(User user, TaskModel model) throws ExceptionMessage {
        Task task = taskRepo.findByTask(model.getID(), user.getID());
        Type_task type = typeRepo.findByTitleType(model.getType(), user.getID());
        Status_task status = statusRepo.findStatusTaskByTitleStatus(model.getStatus(), user.getID());
        Deal deal = dealRepo.findByDeal(model.getDeal_id(), user.getID());
        Client client = clientRepo.findByClient(model.getClient_id(), user.getID());
        task.setTitle(model.getTitle());
        task.setDate_end(model.getDate_end());
        task.setStatus_task(status);
        task.setType_task(type);
        task.setDeal(deal);
        if(deal != null) {
            task.setClient(deal.getClient());
        }
        else{
            if(client != null)
                task.setClient(client);
            else
                task.setClient(null);
        }
        task.setUser(user);
        return taskRepo.save(task);
    }

    public String deleteTask(long id) throws ExceptionMessage {
        taskRepo.deleteById(id);
        return "Задача удалена";
    }
}