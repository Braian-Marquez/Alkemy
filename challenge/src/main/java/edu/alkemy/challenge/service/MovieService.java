package edu.alkemy.challenge.service;


import edu.alkemy.challenge.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO create(MovieDTO movieDTO);
    List<MovieDTO> getAllMovies();
    void delete(Long id);
    MovieDTO update(Long id, MovieDTO movieDTO);

}
