package com.brady.service.owner;

import com.brady.entities.owner.OwnerDto;
import com.brady.entities.owner.OwnerEntity;
import jakarta.ejb.Remote;

@Remote
public interface OwnerServiceRemote {
    OwnerDto getOwnerById(Long id);

    void updateOwner(OwnerDto owner);

}
