package com.example.apperrorhandling;

import com.example.apperrorhandling.models.BodyStyle;
import com.example.apperrorhandling.models.Car;
import com.example.apperrorhandling.models.Engine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;



// DefaultHandlerExceptionResolver
// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html
// https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.java

// ResponseEntityExceptionHandler
// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html
// https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ErrorHandlingTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHttpRequestMethodNotSupportedException() {
        ResponseEntity<String> response = restTemplate.
                exchange("/cars", HttpMethod.DELETE,null, String.class);

        print(response);
    }

    @Test
    public void testHttpMediaTypeNotSupportedException() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>("car", headers);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", request, String.class);

        print(response);
    }


    @Test
    public void testHttpMediaTypeNotAcceptableException() {
        Car car = new Car(3, "VW Polo", LocalDateTime.now(), BodyStyle.Hatchback);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(
                Collections.singletonList(MediaType.TEXT_PLAIN));
        HttpEntity<Car> request = new HttpEntity<>(car, headers);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", request, String.class);

        print(response);
    }


    // 500 INTERNAL_SERVER_ERROR
    // MissingPathVariableException extends ServletRequestBindingException
    @Test
    public void testMissingPathVariableException() {

        ResponseEntity<String> response = restTemplate.
                exchange("/cars/path", HttpMethod.GET,null, String.class);

        print(response);
    }

    // 400 BAD_REQUEST
    // MissingServletRequestParameterException extends ServletRequestBindingException
    @Test
    public void testMissingServletRequestParameterException() {

        ResponseEntity<String> response = restTemplate.
                exchange("/cars/request", HttpMethod.GET,null, String.class);

        print(response);
    }

    // 400 BAD_REQUEST
    // MissingRequestHeaderException extends ServletRequestBindingException
    @Test
    public void testMissingRequestHeaderException() {

        ResponseEntity<String> response = restTemplate.
                exchange("/cars/header", HttpMethod.GET,null, String.class);

        print(response);
    }


    // 400 BAD_REQUEST
    // MethodArgumentTypeMismatchException extends TypeMismatchException
    @Test
    public void testMethodArgumentTypeMismatchException() {

        ResponseEntity<String> response = restTemplate.
                exchange("/cars/abc", HttpMethod.GET,null, String.class);

        print(response);
    }

    // 404 NOT_FOUND
    @Test
    public void testCarNotFoundException() {
        ResponseEntity<String> response = restTemplate.
                exchange("/cars/99", HttpMethod.GET,null, String.class);

        print(response);
    }


    // 500 INTERNAL_SERVER_ERROR
    @Test
    public void testCarDuplicatedIdException() {
        ResponseEntity<String> response = restTemplate.
                exchange("/cars/1", HttpMethod.GET,null, String.class);

        print(response);
    }


    // 400 BAD_REQUEST
    // HttpMessageNotReadableException
    @Test
    public void testHttpMessageNotReadableException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("car", headers);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", request, String.class);

        print(response);
    }


    // 400 BAD_REQUEST
    // MethodArgumentNotValidException
    @Test
    public void testMethodArgumentNotValidException() {
        Car car = new Car(3, null, LocalDateTime.now(), BodyStyle.Limousine);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", car, String.class);

        print(response);
    }


    // 400 BAD_REQUEST
    // MethodArgumentNotValidException
    @Test
    public void testJavaBeanValidationAnnotationInController() {
        Engine engine = new Engine(-1, "Thermal");
        Car car = new Car(3, "VW Beatle", LocalDateTime.now(), BodyStyle.Limousine, engine);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", car, String.class);

        print(response);
    }


    // 500 INTERNAL_SERVER_ERROR
    // ConstraintViolationException
    @Test
    public void testJavaBeanValidationAnnotationInService() {

        Engine engine = new Engine(1000, "Thermal");
        Car car = new Car(3, "VW Beatle", LocalDateTime.now(), BodyStyle.Limousine, engine);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars/service", car, String.class);

        print(response);
    }

    // 400 BAD_REQUEST
    // MethodArgumentNotValidException
    @Test
    public void testSpringValidatorBinderInController() {
        Car car = new Car(3, "VW Tiguan", LocalDateTime.now(), BodyStyle.VAN);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars", car, String.class);

        print(response);
    }

    // 200 OK
    @Test
    public void testSpringValidatorBinderInService() {
        Car car = new Car(3, "VW Tiguan", LocalDateTime.now(), BodyStyle.VAN);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars/service", car, String.class);

        print(response);
    }

    // 400 BAD_REQUEST
    // BindException
    @Test
    public void testSpringValidatorProgrammaticInService() {
        Car car = new Car(3, "Fiat Uno", LocalDateTime.now(), BodyStyle.SUV);

        ResponseEntity<String> response = restTemplate.
                postForEntity("/cars/service", car, String.class);

        print(response);
    }


    private void print(ResponseEntity<String> response) {
        log.info("Response status code:\n{}", response.getStatusCode());
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonResponse = mapper.readValue(response.getBody(), Object.class);

            log.info("Response body:\n{}", mapper.
                    writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse));

        } catch (JsonProcessingException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }
}
