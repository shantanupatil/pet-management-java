package com.brady.service.user;

import jakarta.ejb.Remote;

@Remote
public interface UserServiceRemote {
    boolean authenticate(String username, String password);
}