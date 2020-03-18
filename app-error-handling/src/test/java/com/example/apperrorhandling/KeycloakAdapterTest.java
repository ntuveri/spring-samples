package com.example.apperrorhandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("keycloak")
public class KeycloakAdapterTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testNoBearerToken() {
        ResponseEntity<String> response = restTemplate.
                exchange("/cars/auth", HttpMethod.GET,null, String.class);

        print(response);
    }

    @Test
    public void testExpiredBearerToken() {
        String expiredToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaUkx6bE45OTVPeHBESml0dDZEeHIweFNuWG42Y3ZTdkJoSkJrNEtjRm1BIn0.eyJqdGkiOiI3MzZmOWYwNy05MGE1LTRhYjUtODUxNS1jNzIwOWVmM2RjMTkiLCJleHAiOjE1ODAzOTM2NDcsIm5iZiI6MCwiaWF0IjoxNTgwMzkxODQ3LCJpc3MiOiJodHRwczovL2tleWNsb2FrLWRldmVsb3AuaW5wZWNvdHBtLmNvbS9hdXRoL3JlYWxtcy9UUE0iLCJzdWIiOiIzODFhNzc4Ny1hNDE0LTRhYzQtYjdlNC0zMGE1OGJkODc5MjgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0cG1DbGllbnQiLCJub25jZSI6IjYzNjU4MDEzNjU5NTA0MTIxMS5ZVFJqWlRWbE5qTXRaamRsTUMwMFltRTFMV0ZrTnpJdFpEUXdNVGRrWTJSa09XUXpZV1pqTnpOa01qTXRabVprTVMwME9HSXhMVGcxWm1RdFlUazVaV1kxT1RsbE9HVTEiLCJhdXRoX3RpbWUiOjE1ODAzOTE4NDYsInNlc3Npb25fc3RhdGUiOiI1MmQ5Zjg3NS1kOGUwLTQzN2QtYjAyYS1lMmY0NTVhOWU2NDkiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImh0dHBzOi8vYXBwLXByZXByb2QuaW5wZWNvdHBtLmNvbSIsImh0dHBzOi8vYXBwLWRldmVsb3AuaW5wZWNvdHBtLmNvbSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiY2l0aXplbiJdfSwic2NvcGUiOiJvcGVuaWQgdHBtQXBpIiwiUGFzc0lkIjoiMzgxYTc3ODctYTQxNC00YWM0LWI3ZTQtMzBhNThiZDg3OTI4In0.QFYSHFU8cBgEuEXAnkI3PFybg7OnePE5L0MIg9kmn4BTlMNDRQbKUOK3uQ5JFL25jZknUksZw8-Sohu5eFsaKSLuWuQlAxl3V8OyvqaSV_6ydBRJOtQ3dPYYhM9QhLSrmpqiYe85GN9PrjiLx8BDcM0oFgQaU7XwNFZ1J8xDS7ST9sqp4PKXkE9rETHjvIZnPzn0fGyzb3Pmd8OV3uaNe1D2n24IXWBgy473QXOypdoIjo1DlZUuE1r4GDH7RO0ObNlSyfcaSAck2Q_uzMcyEo02dr-yrv9N1ly9f3au7taE2Uwdqm7aHG8tzmtw9u6e1jon2mCKaVhv0GE-_W0luA";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(expiredToken);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.
                exchange("/cars/auth", HttpMethod.GET, request, String.class);

        print(response);
    }


    private void print(ResponseEntity<String> response) {
        log.info("Response status code:\n{}", response.getStatusCode());
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonResponse = mapper.readValue(response.getBody(), Object.class);

            log.info("Response body:\n{}", mapper.
                    writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse));

        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

}


