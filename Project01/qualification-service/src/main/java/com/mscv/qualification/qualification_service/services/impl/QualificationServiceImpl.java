package com.mscv.qualification.qualification_service.services.impl;

import com.mscv.qualification.qualification_service.entities.Qualification;
import com.mscv.qualification.qualification_service.repositories.QualificationRepository;
import com.mscv.qualification.qualification_service.services.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QualificationServiceImpl implements QualificationService {
    @Autowired
    private QualificationRepository qualificationRepository;
    @Override
    public Qualification create(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Override
    public List<Qualification> getAllQualifications() {
        return qualificationRepository.findAll();
    }

    @Override
    public List<Qualification> getQualificationByUserId(String userId) {
        return qualificationRepository.findByUserId(userId);
    }

    @Override
    public List<Qualification> getQualificationByHotelId(String hotelId) {
        return qualificationRepository.findByHotelId(hotelId);
    }
}
