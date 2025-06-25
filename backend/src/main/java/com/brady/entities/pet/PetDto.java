package com.brady.entities.pet;

import com.brady.entities.owner.OwnerDto;
import com.brady.entities.owner.OwnerEntity;
import com.brady.entities.vaccination.VaccinationDto;
import com.brady.entities.vaccination.VaccinationEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PetDto implements Serializable {
    private Long id;
    private String name;
    private int age;
    private OwnerDto owner;
    private List<VaccinationDto> vaccinations = new ArrayList<>();;
}
