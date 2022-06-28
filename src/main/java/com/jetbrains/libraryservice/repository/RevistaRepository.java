package com.jetbrains.libraryservice.repository;

import com.jetbrains.libraryservice.model.Revista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RevistaRepository extends MongoRepository<Revista, Integer> {

  @Query("{isbn :?0}")
  Optional<Revista> findByIsbn(String isbn);

  @Query("{_id :?0}")
  Optional<Revista> findById(String id);
}
