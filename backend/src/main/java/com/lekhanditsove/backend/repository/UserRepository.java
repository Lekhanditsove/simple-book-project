package com.lekhanditsove.backend.repository;

import com.lekhanditsove.backend.model.DAOUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}
