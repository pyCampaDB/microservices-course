package com.mscv.hotel.hotel_service.services.impl;

import com.mscv.hotel.hotel_service.entities.Hotel;
import com.mscv.hotel.hotel_service.exceptions.ResourceNotFoundException;
import com.mscv.hotel.hotel_service.repositories.HotelRepository;
import com.mscv.hotel.hotel_service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel with ID '" + id + "' not found")
        );
    }
}
