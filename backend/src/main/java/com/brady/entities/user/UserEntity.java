package com.brady.entities.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_account")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
