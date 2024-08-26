package com.msvc.user.user_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor //Crea un constructor con todos los parámetros
@NoArgsConstructor // Crea un constructor vacío
@Entity
@Table(name="users")
@Builder
public class User {
    @Id
    @Column(name="id")
    private String userId;
    @Column(name="name", length = 20)
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="info")
    private String info;
    @Transient //No persistirá en la base de datos
    private List<Qualification> qualifications = new ArrayList<>();
}
