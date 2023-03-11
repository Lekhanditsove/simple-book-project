package com.lekhanditsove.client.repository;

import com.lekhanditsove.client.dto.TokenDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<TokenDTO,Integer> {
}
