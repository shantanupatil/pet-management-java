package com.brady.service.vaccination;

import com.brady.entities.vaccination.VaccinationDto;
import com.brady.entities.vaccination.VaccinationEntity;
import jakarta.ejb.Remote;

@Remote
public interface VaccinationServiceRemote {
    VaccinationDto getVaccinationByPetId(Long id);
    void updateVaccination(VaccinationDto vaccination);
}
