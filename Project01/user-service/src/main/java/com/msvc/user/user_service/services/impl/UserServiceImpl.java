package com.msvc.user.user_service.services.impl;

import com.msvc.user.user_service.entities.Hotel;
import com.msvc.user.user_service.entities.Qualification;
import com.msvc.user.user_service.entities.User;
import com.msvc.user.user_service.exceptions.ResourceNotFoundException;
import com.msvc.user.user_service.external.services.HotelService;
import com.msvc.user.user_service.external.services.QualificationService;
import com.msvc.user.user_service.repositories.UserRepository;
import com.msvc.user.user_service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private QualificationService qualificationService;
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString(); //GENERA CUALQUIER VALOR TIPO STRING
        user.setUserId((randomUserId));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User with ID '" + userId + "' not found")
        );
        Qualification[]/*ArrayList<Qualification>*/ qualificationsByUser = restTemplate.getForObject(
                "http://QUALIFICATION-SERVICE/qualifications/users/" + user.getUserId(),
                Qualification[].class
        );
        logger.info("{}", qualificationsByUser);
        List<Qualification> qualifications = Arrays.stream(qualificationsByUser).collect(Collectors.toList());
        List<Qualification> qualificationsList = qualifications.stream().map(
                qualification -> {
                    Hotel hotel = hotelService.getHotel(qualification.getHotelId());
                    qualification.setHotel(hotel);
                    return qualification;
                }
        ).collect(Collectors.toList());

        user.setQualifications(qualificationsList);
        return user;
    }



    // Only RestTemplate
    /*@Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User with ID '" + userId + "' not found")
        );
        Qualification[] qualificationsByUser = restTemplate.getForObject(
                //al añadir LoadBalanced en MyConfig:
                "http://QUALIFICATION-SERVICE/qualifications/users/" + user.getUserId(),
                //"http://localhost:8083/qualifications/users/" + user.getUserId(),
                Qualification[].class//ArrayList.class
        );
        logger.info("{}", qualificationsByUser);
        List<Qualification> qualifications = Arrays.stream(qualificationsByUser).collect(Collectors.toList());
        List<Qualification> qualificationsList = qualifications.stream().map(
                qualification -> {
                    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(
                            "http://HOTEL-SERVICE/hotels/"+ qualification.getHotelId(),
                            //"http://localhost:8082/hotels/" + qualification.getHotelId(),
                            Hotel.class
                    );
                    Hotel hotel = forEntity.getBody();
                    logger.info("Response with status code: {}", forEntity.getStatusCode());
                    qualification.setHotel(hotel);
                    return qualification;
                }
        ).collect(Collectors.toList());

        user.setQualifications(qualificationsList);
        return user;
    }*/
}
