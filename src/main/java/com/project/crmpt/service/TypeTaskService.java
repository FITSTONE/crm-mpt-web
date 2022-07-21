package com.project.crmpt.service;

import com.project.crmpt.entity.Task;
import com.project.crmpt.entity.Type_task;
import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.repository.TaskRepo;
import com.project.crmpt.repository.TypeTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TypeTaskService {

    @Autowired
    private TypeTaskRepo typeRepo;

    @Autowired
    private TaskRepo taskRepo;

    public List<TypeStatusModel> getTypeTasks (User user){
        List<TypeStatusModel> model = new ArrayList<TypeStatusModel>();
        List<Type_task> type = typeRepo.findByTypeTaskAll(user.getID());
        for(int i = 0; i < type.size(); i++){
            Type_task typeTask = type.get(i);
            TypeStatusModel typeModel = new TypeStatusModel(typeTask.getID(),typeTask.getTitle());
            model.add(typeModel);
        }
        return model;
    }

    public TypeStatusModel getTypeTask (Long id, User user){
        Type_task typeTask = typeRepo.findByTypeTask(id, user.getID());
        TypeStatusModel typeModel = new TypeStatusModel(typeTask.getID(),typeTask.getTitle());
        return typeModel;
    }

    public Type_task addTypeTask (Type_task type, User user) throws ExceptionMessage {
        if(typeRepo.findByTitleType(type.getTitle(), user.getID()) != null)
            throw new ExceptionMessage("Такой тип задачи уже существует");
        type.setUser(user);
        return typeRepo.save(type);
    }

    public Type_task updateTypeTask (Type_task type, User user) throws ExceptionMessage {
        Type_task type_task = typeRepo.findByTypeTask(type.getID(), user.getID());
        if(!Objects.equals(type_task.getTitle(), type.getTitle())){
            if(typeRepo.findByTitleType(type.getTitle(), user.getID()) != null)
                throw new ExceptionMessage("Такой тип задачи уже существует");
        }
        type_task.setTitle(type.getTitle());
        return typeRepo.save(type_task);
    }

    public String deleteTypeTask(Long id, User user) throws  ExceptionMessage{
        List<Task> tasks = taskRepo.findByTypeTask(id, user.getID());
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            task.setType_task(null);
            taskRepo.save(task);
        }
        typeRepo.deleteById(id);
        return "Тип задачи успешно удален";
    }
}