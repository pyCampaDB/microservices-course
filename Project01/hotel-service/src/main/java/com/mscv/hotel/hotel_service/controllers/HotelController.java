package com.mscv.hotel.hotel_service.controllers;

import com.mscv.hotel.hotel_service.entities.Hotel;
import com.mscv.hotel.hotel_service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }
    @GetMapping
    public ResponseEntity<List<Hotel>> hotelsList(){
        //return ResponseEntity.ok(hotelService.getAllHotels());
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.getHotel(hotelId));
    }
}
