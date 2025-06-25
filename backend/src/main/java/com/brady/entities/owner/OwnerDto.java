package com.brady.entities.owner;

import lombok.Data;

import java.io.Serializable;

@Data
public class OwnerDto implements Serializable {
    private Long id;
    private String name;
    private String telephone;
}
