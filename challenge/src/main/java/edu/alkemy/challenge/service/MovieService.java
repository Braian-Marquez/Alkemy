package edu.alkemy.challenge.service;


import edu.alkemy.challenge.dto.CharacterDTO;
import edu.alkemy.challenge.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO create(MovieDTO movieDTO);
    MovieDTO findById(Long id);
    List<MovieDTO> getAllMovies();
    void delete(Long id);
    MovieDTO update(Long id, MovieDTO movieDTO);
    List<MovieDTO> findByParam(String title,String genre,String order);
    MovieDTO addCharacter(Long idMovie, Long idPj);
    MovieDTO deleteCharacter(Long idMovie, Long idPj);

}
