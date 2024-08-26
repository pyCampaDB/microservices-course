package com.mscv.qualification.qualification_service.services;

import com.mscv.qualification.qualification_service.entities.Qualification;

import java.util.List;

public interface QualificationService {
    Qualification create(Qualification qualification);
    List<Qualification> getAllQualifications();
    List<Qualification> getQualificationByUserId(String userId);
    List<Qualification> getQualificationByHotelId(String hotelId);
}
