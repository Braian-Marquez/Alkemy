package edu.alkemy.challenge.service;



import edu.alkemy.challenge.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MovieService {
    MovieDTO create(MovieDTO movieDTO);
    MovieDTO findById(Long id);
    List<MovieDTO> getAllMovies();
    void delete(Long id);
    MovieDTO update(Long id, MovieDTO movieDTO);
    List<MovieDTO> getMovieByFilters(String title, String creationDate, Long genreId, String order);

    MovieDTO addCharacter(Long idMovie, Long idPj);
    MovieDTO deleteCharacter(Long idMovie, Long idPj);

}
