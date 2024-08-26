package com.mscv.qualification.qualification_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("qualifications")
public class Qualification {
    @Id
    private String qualificationId;
    private String userId;
    private String hotelId;
    private int qualification;
    private String remarks;
}
