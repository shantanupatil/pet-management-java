package com.brady.service.vaccination;

import com.brady.entities.vaccination.VaccinationDto;
import com.brady.entities.vaccination.VaccinationEntity;
import com.brady.utils.ObjectMapperUtils;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;


@Stateless
@Remote(VaccinationServiceRemote.class)
public class VaccinationService implements VaccinationServiceRemote{

    @PersistenceContext(unitName = "bradyPU")
    private EntityManager mEntityManager;

    @Override
    public VaccinationDto getVaccinationByPetId(Long petId) {
        TypedQuery<VaccinationEntity> query = mEntityManager.createQuery(
                "SELECT v FROM VaccinationEntity v WHERE v.petId = :petId ORDER BY v.vaccinationDate DESC",
                VaccinationEntity.class
        );
        query.setParameter("petId", petId);
        query.setMaxResults(1);

        List<VaccinationEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return ObjectMapperUtils.toVaccinationDto(results.get(0));
    }

    @Override
    public void updateVaccination(VaccinationDto vaccination) {
        VaccinationEntity vaccinationEntity = ObjectMapperUtils.toVaccinationEntity(vaccination);
        mEntityManager.merge(vaccinationEntity);
    }
}
