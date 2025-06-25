package com.brady.utils;

import com.brady.entities.owner.OwnerDto;
import com.brady.entities.owner.OwnerEntity;
import com.brady.entities.pet.PetDto;
import com.brady.entities.pet.PetEntity;
import com.brady.entities.vaccination.VaccinationDto;
import com.brady.entities.vaccination.VaccinationEntity;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static PetDto toPetDto(PetEntity petEntity) {
        return modelMapper.map(petEntity, PetDto.class);
    }

    public static PetEntity toPetEntity(PetDto petDto) {
        return modelMapper.map(petDto, PetEntity.class);
    }

    public static List<PetDto> toPetDtoList(List<PetEntity> pets) {
        return pets.stream().map(ObjectMapperUtils::toPetDto).collect(Collectors.toList());
    }

    public static OwnerDto toOwnerDto(OwnerEntity ownerEntity) {
        return modelMapper.map(ownerEntity, OwnerDto.class);
    }

    public static OwnerEntity toOwnerEntity(OwnerDto ownerDto) {
        return modelMapper.map(ownerDto, OwnerEntity.class);
    }

    public static VaccinationDto toVaccinationDto(VaccinationEntity vaccinationEntity) {
        return modelMapper.map(vaccinationEntity, VaccinationDto.class);
    }

    public static VaccinationEntity toVaccinationEntity(VaccinationDto vaccinationDto) {
        return modelMapper.map(vaccinationDto, VaccinationEntity.class);
    }
}
