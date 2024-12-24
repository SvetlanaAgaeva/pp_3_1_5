package ru.svetlanaagaeva.rest_api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.svetlanaagaeva.rest_api.service.UserService;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

public class RestApiApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(RestApiApplication.class, args);

        // Получаем UserService из контекста
        UserService userService = context.getBean(UserService.class);

        // Выполняем операции по порядку
        String result1 = userService.getAllUsers();
        System.out.println(result1);  //

        String result2 = userService.addUser();
        System.out.println(result2);  // Добавляем пользователя и выводим в консоль

        String result3 = userService.updateUser();
        System.out.println(result3);  // Обновляем пользователя и выводим в консоль

        String result4 = userService.deleteUser(Long.valueOf(3));
        System.out.println(result4);  // Удаляем пользователя и выводим в консоль

        System.out.println(result2.substring("Пользователь добавлен: ".length()) +
                result3.substring("Пользователь обновлен: ".length()) +
                result4.substring("Пользователь удален: ".length()));
    }
}


