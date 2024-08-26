package com.mscv.qualification.qualification_service.repositories;

import com.mscv.qualification.qualification_service.entities.Qualification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QualificationRepository extends MongoRepository<Qualification, Long> {
    List<Qualification> findByUserId(String userId);
    List<Qualification> findByHotelId(String hotelId);
}
