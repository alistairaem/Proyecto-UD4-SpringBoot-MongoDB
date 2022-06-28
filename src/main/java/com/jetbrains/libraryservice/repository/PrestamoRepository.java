package com.jetbrains.libraryservice.repository;

import com.jetbrains.libraryservice.model.Prestamo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends MongoRepository<Prestamo, Integer> {

  @Query("{_id :?0}")
  Optional<Prestamo> findById(String id);


  @Aggregation(pipeline = {
          "{$match : {devuelto :{$eq : false}}}",
          "{$match : {fechaFin :{$gte : ?0}}}",
          "{$match : {fechaFin :{lt : ?1}}}",

  })
  List<Prestamo> findByFechaDevolucion(LocalDate fecha, LocalDate fecha2);

  @Aggregation(pipeline = {
          "{$match : {devuelto :{$eq : false}}}",
  })
  List<Prestamo> findByNoDevuelto();

}
