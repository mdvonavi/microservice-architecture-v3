package ru.skillbox.demo;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserHandler {
    private final String userServiceUrl;

    public UserHandler(String userServiceUrl) {
        this.userServiceUrl = userServiceUrl;
    }


    public boolean existsById(Long userId){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        System.out.println(userServiceUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                userServiceUrl + "/users/" + userId,
                HttpMethod.GET, // или GET, PUT, DELETE в зависимости от вашего случая
                requestEntity,
                String.class
        );

        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}
