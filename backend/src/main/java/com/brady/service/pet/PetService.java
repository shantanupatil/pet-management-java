package com.brady.service.pet;

import com.brady.entities.pet.PetDto;
import com.brady.entities.pet.PetEntity;
import com.brady.utils.ObjectMapperUtils;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;


@Stateless
@Remote(PetServiceRemote.class)
public class PetService implements PetServiceRemote {

    @PersistenceContext(unitName = "bradyPU")
    private EntityManager mEntityManager;

    public List<PetDto> getAllPets() {
        final List<PetEntity> petEntities = mEntityManager
                .createQuery("SELECT p FROM PetEntity p", PetEntity.class)
                .getResultList();
        return ObjectMapperUtils.toPetDtoList(petEntities);
    }

    public List<PetDto> searchPetsByName(String name) {
        TypedQuery<PetEntity> query = mEntityManager.createQuery(
                "SELECT p FROM PetEntity p WHERE LOWER(p.name) LIKE :name", PetEntity.class
        );
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        final List<PetEntity> petEntities = query.getResultList();
        return ObjectMapperUtils.toPetDtoList(petEntities);
    }

    public PetDto getPetById(Long id) {
        final PetEntity petEntity = mEntityManager.find(PetEntity.class, id);
        return ObjectMapperUtils.toPetDto(petEntity);
    }

    public void updatePet(PetDto petDto) {
        PetEntity petEntity = ObjectMapperUtils.toPetEntity(petDto);
        mEntityManager.merge(petEntity);
    }
}
