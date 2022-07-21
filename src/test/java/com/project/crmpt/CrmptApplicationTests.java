package com.project.crmpt;

import com.project.crmpt.entity.User;
import com.project.crmpt.exception.ExceptionMessage;
import com.project.crmpt.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrmptApplicationTests {
    @Test
    public void testRegistrationUser() throws ExceptionMessage {
        UserService service = new UserService();
        User user = new User();
        user.setName("Антон");
        user.setSurname("Курлов");
        user.setMiddle_name("Семенович");
        user.setPhone("+79178906764");
        user.setEmail("anton@mail.ru");
        user.setLogin("anton@mail.ru");
        user.setPassword("1q2w3e");
        long id = service.registrationAccount(user);
        Assert.assertEquals(id, 3);
    }
}
