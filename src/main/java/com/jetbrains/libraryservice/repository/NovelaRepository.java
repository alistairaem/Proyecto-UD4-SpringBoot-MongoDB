package com.jetbrains.libraryservice.repository;

import com.jetbrains.libraryservice.model.Novela;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface NovelaRepository extends MongoRepository<Novela,Integer> {

  @Query("{isbn :?0}")
  Optional<Novela> findByIsbn(String isbn);

  @Query("{_id :?0}")
  Optional<Novela> findById(String id);
}


