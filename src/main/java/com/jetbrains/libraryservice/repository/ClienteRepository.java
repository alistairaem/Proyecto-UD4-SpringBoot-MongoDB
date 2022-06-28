package com.jetbrains.libraryservice.repository;

import com.jetbrains.libraryservice.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends MongoRepository<Cliente, Integer> {
  @Query("{dni :?0}")
  Optional<Cliente> findByDni(String dni);

  @Query("{_id :?0}")
  Optional<Cliente> findById(String id);


}
