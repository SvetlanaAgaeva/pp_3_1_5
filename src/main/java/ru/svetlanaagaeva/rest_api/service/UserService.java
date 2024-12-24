package ru.svetlanaagaeva.rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private String sessionId = null;
    private final RestTemplate restTemplate;



    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAllUsers() {

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, String.class);
        String sessionCookie = response.getHeaders().getFirst("Set-Cookie");
        sessionId = sessionCookie;  // Сохраняем sessionId
        System.out.println("Session ID: " + sessionId);

        return "Получены все пользователи: " + response.getBody();
    }

    public String addUser() {
        String userJson = "{\"id\": 3, \"name\": \"James\", \"lastName\": \"Brown\", \"age\": 30}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);

        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, String.class);

        System.out.println("Response for POST (add): " + response.getBody());
        return "Пользователь добавлен: " + response.getBody();
    }


    public String updateUser() {
        String userJson = "{\"id\": 3, \"name\": \"Thomas\", \"lastName\": \"Shelby\", \"age\": 30}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);

        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        // Логируем запрос, чтобы увидеть, что мы отправляем
        System.out.println("Sending PUT request to: " + BASE_URL);
        System.out.println("Request Body: " + userJson);
        System.out.println("Request Headers: " + headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class);

            return "Пользователь обновлен: " + response.getBody();

    }



//         Метод для удаления пользователя
    public String deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.set("Cookie", sessionId);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + userId, HttpMethod.DELETE, entity, String.class);
        System.out.println("Response for DELETE: " + response.getBody());


        return "Пользователь удален: " + response.getBody();

    }


}

