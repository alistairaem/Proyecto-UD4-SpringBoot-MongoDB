package com.jetbrains.libraryservice.service;


import com.jetbrains.libraryservice.exception.MangaNotFoundException;
import com.jetbrains.libraryservice.model.Manga;
import com.jetbrains.libraryservice.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MangaService {

  private MangaRepository mangaRepository;

  @Autowired
  public void MangaRepository(MangaRepository mangaRepository) {
    this.mangaRepository = mangaRepository;
  }

  public boolean existeManga(String isbn) {
    return mangaRepository.findByIsbn(isbn).isPresent();
  }

  @Transactional
  public Manga nuevoManga(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion,
                          String editorial, String autor, String demografia, String genero, boolean color) {
    Manga manga = new Manga(isbn, titulo, fechaPublicacion, longitudImpresion, editorial, autor, demografia, genero
            , color);
    mangaRepository.save(manga);
    return manga;
  }

  @Transactional(readOnly = true)
  public List<Manga> allMangas() {
    List<Manga> mangas = new ArrayList<>();
    mangaRepository.findAll().forEach(mangas::add);
    return mangas;
  }


  @Transactional(readOnly = true)
  public Manga findById(String id) {
    return mangaRepository.findById(id).orElse(null);
  }

  @Transactional
  public Manga modificaManga(String id, String titulo, LocalDate fechaPublicacion, int longitudImpresion,
                             String editorial, String autor, String demografia, String genero, boolean color) {
    Manga manga = mangaRepository.findById(id).orElse(null);

    if (manga == null) {
      throw new MangaNotFoundException("No existe manga con id " + id);
    }

    manga.setTitulo(titulo);
    manga.setFechaPublicacion(fechaPublicacion);
    manga.setLongitudImpresion(longitudImpresion);
    manga.setEditorial(editorial);
    manga.setAutor(autor);
    manga.setDemografia(demografia);
    manga.setGenero(genero);
    manga.setColor(color);
    mangaRepository.save(manga);
    return manga;
  }

  @Transactional
  public void borraManga(String idManga) {
    Manga manga = mangaRepository.findById(idManga).orElse(null);
    if (manga == null) {
      throw new MangaNotFoundException("No existe manga con id " + idManga);
    }
    mangaRepository.delete(manga);
  }

  @Transactional(readOnly = true)
  public Manga findByIsbn(String isbn) {
    return mangaRepository.findByIsbn(isbn).orElse(null);
  }

  @Transactional(readOnly = true)
  public Manga findByIdManga(String s) {
    return mangaRepository.findById(s).orElse(null);
  }
}
