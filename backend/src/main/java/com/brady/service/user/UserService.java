package com.brady.service.user;

import com.brady.entities.user.UserEntity;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
@Remote(UserServiceRemote.class)
public class UserService implements UserServiceRemote {

    @PersistenceContext(unitName = "bradyPU")
    private EntityManager mEntityManager;

    @Override
    public boolean authenticate(String username, String password) {
        // Note: We never store the passwords as plain text. Hashing can be used to store the password
        // But as this is a part of assignment I am not implementing hashing just to get 2 seconds for other
        // implementations :p
        TypedQuery<UserEntity> query = mEntityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password",
                UserEntity.class
        );
        query.setParameter("username", username);
        query.setParameter("password", password);
        return !query.getResultList().isEmpty();
    }
}