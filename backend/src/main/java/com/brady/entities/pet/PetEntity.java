package com.brady.entities.pet;

import com.brady.entities.owner.OwnerEntity;
import com.brady.entities.vaccination.VaccinationEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet")
@Data
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", unique = true)
    private OwnerEntity owner;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private List<VaccinationEntity> vaccinations = new ArrayList<>();
}
