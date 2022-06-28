package com.jetbrains.libraryservice.repository;

import com.jetbrains.libraryservice.model.Manga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface MangaRepository extends MongoRepository<Manga, Integer> {

  @Query("{isbn :?0}")
  Optional<Manga> findByIsbn(String isbn);

  @Query("{_id :?0}")
  Optional<Manga> findById(String id);

}
