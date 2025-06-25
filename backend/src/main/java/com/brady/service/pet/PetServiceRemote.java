package com.brady.service.pet;

import com.brady.entities.pet.PetDto;
import com.brady.entities.pet.PetEntity;

import java.util.List;

import jakarta.ejb.Remote;

@Remote
public interface PetServiceRemote {
    List<PetDto> getAllPets();
    List<PetDto> searchPetsByName(String name);
    PetDto getPetById(Long id);
    void updatePet(PetDto petEntity);
}
