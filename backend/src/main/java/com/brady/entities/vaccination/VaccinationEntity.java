package com.brady.entities.vaccination;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "vaccination")
@Data
public class VaccinationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "vaccination_date")
    private LocalDateTime vaccinationDate;
    @Column(name = "pet_id")
    private Long petId;
    @Column(name = "description")
    private String vaccineDescription;
}
