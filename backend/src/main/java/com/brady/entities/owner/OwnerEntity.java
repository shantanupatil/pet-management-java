package com.brady.entities.owner;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "owner")
@Data
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
}
