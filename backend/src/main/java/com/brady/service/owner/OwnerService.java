package com.brady.service.owner;

import com.brady.entities.owner.OwnerDto;
import com.brady.entities.owner.OwnerEntity;

import com.brady.utils.ObjectMapperUtils;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@Remote(OwnerServiceRemote.class)
public class OwnerService implements OwnerServiceRemote {

    @PersistenceContext(unitName = "bradyPU")
    private EntityManager mEntityManager;

    @Override
    public OwnerDto getOwnerById(Long id) {
        OwnerEntity ownerEntity = mEntityManager.find(OwnerEntity.class, id);
        return ObjectMapperUtils.toOwnerDto(ownerEntity);
    }

    @Override
    public void updateOwner(OwnerDto ownerDto) {
        OwnerEntity ownerEntity = ObjectMapperUtils.toOwnerEntity(ownerDto);
        mEntityManager.merge(ownerEntity);
    }
}
