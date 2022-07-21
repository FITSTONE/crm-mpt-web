package com.project.crmpt.service;

import com.project.crmpt.entity.*;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.models.TypeStatusModel;
import com.project.crmpt.models.UserModel;
import com.project.crmpt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TypeTaskService typeTask;

    @Autowired
    private TypeDealService typeDeal;

    @Autowired
    private TypeClientService typeClient;

    @Autowired
    private StatusTaskService statusTask;

    @Autowired
    private StatusDealService statusDeal;

    @Autowired
    private StatusClientService statusClient;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public UserModel getUser(User user){
        UserModel model = new UserModel();
        model.setID(user.getID());
        model.setNickname(user.getSurname() + " " + user.getName() + " " + user.getMiddle_name());
        model.setPhone(user.getPhone());
        model.setEmail(user.getEmail());
        if(user.getActive() == true)
            model.setActive("Активирован");
        else
            model.setActive("Не активирован");
        model.setLogin(user.getLogin());
        model.setPassword(user.getPassword());
        return model;
    }

    public Long registrationAccount(User user) throws ExceptionMessage {
        if(user.getEmail() != "") {
            if (userRepo.findUserByEmail(user.getEmail()) != null)
                throw new ExceptionMessage("Пользователь с такой почтой уже существует");
        }
        if(user.getPhone() != "") {
            if (userRepo.findUserByPhone(user.getPhone()) != null)
                throw new ExceptionMessage("Пользователь с таким номер телефона уже существует");
        }
        user.setLogin(user.getEmail());
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        if(user.getEmail() != null){
            String message = String.format("Здраствуйте, %s! \n" +
                    "Добро пожаловать в CRM-MPT, мы рады что вы выбрали нас. \n" +
                    "Для активации аккаунта, пожалуйста перейдите по ссылке http://crm-mpt.com/activate/%s",
                    user.getName(), user.getActivationCode());
            mailSenderService.sendMessage(user.getEmail(), "Активация аккаунта в CRM-MPT",
                    message);
        }
        return user.getID();
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if(user == null)
            return false;
        user.setActive(true);
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public void recoveryPassword(String email) throws ExceptionMessage {
        if(userRepo.findUserByEmail(email) == null)
            throw new ExceptionMessage("Пользователь с такой почтой не существует");
        User user = userRepo.findUserByEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        if(user.getEmail() != null){
            String message = String.format("Здраствуйте, %s! \n" +
                            "Для восстановления пароля, пожалуйста перейдите по ссылке http://crm-mpt.com/reset/%s",
                    user.getName(), user.getActivationCode());
            mailSenderService.sendMessage(user.getEmail(), "Восстановление пароля от аккаунта в CRM-MPT",
                    message);
        }
    }

    public void support(String email, String message) throws ExceptionMessage{
        if(userRepo.findUserByEmail(email) == null)
            throw new ExceptionMessage("Пользователь с такой почтой не существует");
        User user = userRepo.findUserByEmail(email);
        mailSenderService.sendMessage("crm-mpt@yandex.ru", "Проблемы у пользователя: " + user.getEmail(),
                message);
    }

    public boolean resetPassword(String code, String password){
        User user = userRepo.findByActivationCode(code);
        if(user == null)
            return false;
        user.setPassword(password);
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public String registrationDataByAccount(User user) throws ExceptionMessage{
        User userAdd = userRepo.findById(user.getID()).orElseThrow();
        Type_client clientType1 = new Type_client();
        clientType1.setTitle("Клиент");
        typeClient.addTypeClient(clientType1, userAdd);
        Type_client clientType2 = new Type_client();
        clientType2.setTitle("Посредник");
        typeClient.addTypeClient(clientType2, userAdd);
        Type_client clientType3 = new Type_client();
        clientType3.setTitle("Партнер");
        typeClient.addTypeClient(clientType3, userAdd);
        Type_deal type_deal1 = new Type_deal();
        type_deal1.setTitle("Заказ");
        typeDeal.addTypeDeal(type_deal1, userAdd);
        Type_deal type_deal2 = new Type_deal();
        type_deal2.setTitle("Поставка");
        typeDeal.addTypeDeal(type_deal2, userAdd);
        Type_task type_task1 = new Type_task();
        type_task1.setTitle("Личная задача");
        typeTask.addTypeTask(type_task1, userAdd);
        Type_task type_task2 = new Type_task();
        type_task2.setTitle("Встреча");
        typeTask.addTypeTask(type_task2, userAdd);
        Type_task type_task3 = new Type_task();
        type_task3.setTitle("Звонок");
        typeTask.addTypeTask(type_task3, userAdd);
        Status_client status_client1 = new Status_client();
        status_client1.setTitle("Активен");
        statusClient.addStatusClient(status_client1, userAdd);
        Status_client status_client2 = new Status_client();
        status_client2.setTitle("Не активен");
        statusClient.addStatusClient(status_client2, userAdd);
        Status_deal status_deal1 = new Status_deal();
        status_deal1.setTitle("Выяснений условий сделки");
        statusDeal.addStatusDeal(status_deal1, userAdd);
        Status_deal status_deal2 = new Status_deal();
        status_deal2.setTitle("В процессе завершения сделки");
        statusDeal.addStatusDeal(status_deal2, userAdd);
        Status_deal status_deal3 = new Status_deal();
        status_deal3.setTitle("Сделка провалена");
        statusDeal.addStatusDeal(status_deal3, userAdd);
        Status_deal status_deal4 = new Status_deal();
        status_deal4.setTitle("Сделка успешно завершена");
        statusDeal.addStatusDeal(status_deal4, userAdd);
        Status_task status_task1 = new Status_task();
        status_task1.setTitle("Просрочено");
        statusTask.addStatusTask(status_task1, user);
        Status_task status_task2 = new Status_task();
        status_task2.setTitle("Срочно");
        statusTask.addStatusTask(status_task2, user);
        Status_task status_task3 = new Status_task();
        status_task3.setTitle("Важно");
        statusTask.addStatusTask(status_task3, user);
        return "Все данные успешно добавлены";
    }

    public void updateAccount(User id, User user) throws ExceptionMessage {
        if(user.getEmail() != "") {
            if (!Objects.equals(id.getEmail(), user.getEmail())) {
                if (userRepo.findUserByEmail(user.getEmail()) != null)
                    throw new ExceptionMessage("Пользователь с такой почтой уже существует");
            }
        }
        if(user.getPhone() != "") {
            if (!Objects.equals(id.getPhone(), user.getPhone())) {
                if (userRepo.findUserByPhone(user.getPhone()) != null)
                    throw new ExceptionMessage("Пользователь с таким номер телефона уже существует");
            }
        }
        User updateUser = userRepo.findById(id.getID()).orElseThrow();
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setMiddle_name(user.getMiddle_name());
        updateUser.setPhone(user.getPhone());
        updateUser.setEmail(user.getEmail());
        updateUser.setLogin(user.getEmail());
        updateUser.setPassword(user.getPassword());
        userRepo.save(updateUser);
        User userPrincipal = userRepo.findById(id.getID()).orElseThrow();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User blockAccount(User user, Boolean active) throws ExceptionMessage {
        User updateUser = userRepo.findById(user.getID()).orElseThrow();
        updateUser.setActive(active);
        return userRepo.save(updateUser);
    }

    public Long deleteAccount(User user) throws ExceptionMessage {
        userRepo.deleteById(user.getID());
        return user.getID();
    }
}