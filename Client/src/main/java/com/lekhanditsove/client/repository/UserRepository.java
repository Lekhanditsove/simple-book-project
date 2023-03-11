package com.lekhanditsove.client.repository;

import com.lekhanditsove.client.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Integer> {

}
