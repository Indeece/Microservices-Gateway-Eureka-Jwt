package ru.indeece.authservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.indeece.authservice.enums.ERole;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;
}
