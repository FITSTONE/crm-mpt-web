package com.project.crmpt.web_controllers;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.DealModel;
import com.project.crmpt.models.TaskModel;
import com.project.crmpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private UserService userService;

    @Autowired
    private DealService dealService;

    @Autowired
    private TypeTaskService typeService;

    @Autowired
    private StatusTaskService statusService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String getTasks(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Task", taskService.getTasks(user.getID()));
        model.addAttribute("Type_task", typeService.getTypeTasks(user));
        model.addAttribute("Status_task", statusService.getStatusTasks(user));
        return "task/taskList";
    }

    @GetMapping("/search")
    public String getTasksBySearch(@AuthenticationPrincipal User user,
                                   @RequestParam Long type, @RequestParam Long status,
                                   Model model)  {
        if(type != 0 && status != 0)
            model.addAttribute("Task", taskService.getTasks(user.getID()));
        if(type != 0 && status == 0)
            model.addAttribute("Task", taskService.getTaskByType(type, user.getID()));
        if(type == 0 && status != 0)
            model.addAttribute("Task", taskService.getTaskByStatus(status, user.getID()));
        if(type == 0 && status == 0)
            model.addAttribute("Task", taskService.getTasks(user.getID()));
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Type_task", typeService.getTypeTasks(user));
        model.addAttribute("Status_task", statusService.getStatusTasks(user));
        return "task/taskList";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Task", taskService.getTask(id, user.getID()));
        return "task/taskDetails";
    }

    @GetMapping("/add")
    public String getAddTask(@AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Deal", dealService.getDeals(user.getID()));
        model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("Type_task", typeService.getTypeTasks(user));
        model.addAttribute("Status_task", statusService.getStatusTasks(user));
        return "task/taskAdd";
    }

    @PostMapping("/add")
    public String addDeal(@AuthenticationPrincipal User user,
                          @RequestParam String title, @RequestParam String date,
                          @RequestParam String type, @RequestParam String status,
                          @RequestParam Long deal, @RequestParam Long client){
        try{
            TaskModel model = new TaskModel(title, date, type, status, deal, client);
            taskService.addTask(user, model);
            return "redirect:/task";
        }
        catch (ExceptionMessage e){
            return "redirect:/task/add";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateTask(@PathVariable Long id, @AuthenticationPrincipal User user, Model model)  {
        model.addAttribute("User", userService.getUser(user));
        model.addAttribute("Task", taskService.getTask(id, user.getID()));
        model.addAttribute("Deal", dealService.getDeals(user.getID()));
        model.addAttribute("Client", clientService.getClients(user.getID()));
        model.addAttribute("Type_task", typeService.getTypeTasks(user));
        model.addAttribute("Status_task", statusService.getStatusTasks(user));
        return "task/taskUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@AuthenticationPrincipal User user, @PathVariable Long id,
                          @RequestParam String title, @RequestParam String date,
                          @RequestParam String type, @RequestParam String status,
                          @RequestParam Long deal, @RequestParam Long client){
        try{
            TaskModel model = new TaskModel(id, title, date, type, status, deal, client);
            taskService.updateTask(user, model);
            return "redirect:/task/{id}";
        }
        catch (ExceptionMessage e){
            return "redirect:/task/update/{id}";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        try{
            taskService.deleteTask(id);
            return "redirect:/task";
        }
        catch (ExceptionMessage e){
            return "redirect:/task/{id}";
        }
    }
}
