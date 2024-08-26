package com.mscv.hotel.hotel_service.repositories;

import com.mscv.hotel.hotel_service.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
