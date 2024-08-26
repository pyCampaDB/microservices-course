package com.mscv.hotel.hotel_service.services;

import com.mscv.hotel.hotel_service.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create(Hotel hotel);
    List<Hotel> getAllHotels();
    Hotel getHotel(String id);
}
