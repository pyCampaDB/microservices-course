package com.msvc.user.user_service.controllers;

import com.msvc.user.user_service.entities.User;
import com.msvc.user.user_service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User userRequest){
        System.out.println("Llegó una solicitud POST a /users con los datos: " + userRequest);
        User user = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> usersList(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    int numberRetries = 1;
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        log.info("List a single user: UserController");
        log.info("Number of retries: {}", numberRetries);
        numberRetries++;
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    public ResponseEntity<User> ratingHotelFallback (String userId, Exception exception){
        log.info("The backup is executed because the service is inactive: ", exception.getMessage());
        User user = User.builder()
                .email("root@gmail.com")
                .name("Zetsu")
                .info("This user is created by default when a service goes down")
                .userId("1234")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
