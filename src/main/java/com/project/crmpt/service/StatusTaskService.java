package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.DealRepo;
import com.project.crmpt.repository.StatusTaskRepo;
import com.project.crmpt.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StatusTaskService {

    @Autowired
    private StatusTaskRepo statusRepo;

    @Autowired
    private TaskRepo taskRepo;

    public List<TypeStatusModel> getStatusTasks (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Status_task> status = statusRepo.findStatusTasksAll(user.getID());
        for(int i = 0; i < status.size(); i++){
            Status_task statusTask = status.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(statusTask.getID(), statusTask.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getStatusTask (Long id, User user){
        Status_task status = statusRepo.findStatusTask(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(status.getID(), status.getTitle());
        return typeModel;
    }

    public Status_task addStatusTask (Status_task status, User user) throws ExceptionMessage {
        if(statusRepo.findStatusTaskByTitleStatus(status.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой статус задачи уже существует");
        status.setUser(user);
        return statusRepo.save(status);
    }

    public Status_task updateStatusTask (Status_task status, User user) throws ExceptionMessage {
        Status_task statusTask = statusRepo.findStatusTask(status.getID(), user.getID());
        Status_task taskStatus = statusRepo.findStatusTaskByTitleStatus("Просрочено", user.getID());
        if(!Objects.equals(statusTask.getID(), taskStatus.getID())) {
            if (!Objects.equals(statusTask.getTitle(), status.getTitle())) {
                if (statusRepo.findStatusTaskByTitleStatus(status.getTitle(), user.getID()) != null)
                    throw new ExceptionMessage("Такой статус задачи уже существует");
            }
            statusTask.setTitle(status.getTitle());
            return statusRepo.save(statusTask);
        }
        else
            throw new ExceptionMessage("Такой статус задачи нельзя изменять");
    }

    public String deleteStatusTask(Long id, User user) throws  ExceptionMessage{
        Status_task status = statusRepo.findStatusTask(id, user.getID());
        if(!Objects.equals(status.getTitle(), "Просрочено")) {
            List<Task> tasks = taskRepo.findByStatusTask(id, user.getID());
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                task.setStatus_task(null);
                taskRepo.save(task);
            }
            statusRepo.deleteById(id);
            return "Статус задачи успешно удален";
        }
        else
            throw new ExceptionMessage("Этот статус нельзя удалить");
    }
}