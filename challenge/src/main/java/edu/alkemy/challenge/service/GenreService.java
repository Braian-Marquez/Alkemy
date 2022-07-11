package edu.alkemy.challenge.service;

import edu.alkemy.challenge.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO save(GenreDTO genreDTO);
    List<GenreDTO> getAllGenres();
    void delete(Long id);
    GenreDTO update(Long id, GenreDTO genreDTO);
}
