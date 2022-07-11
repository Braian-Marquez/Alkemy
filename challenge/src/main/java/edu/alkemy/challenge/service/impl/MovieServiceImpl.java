package edu.alkemy.challenge.service.impl;
import edu.alkemy.challenge.dto.MovieDTO;
import edu.alkemy.challenge.entity.MovieEntity;
import edu.alkemy.challenge.exception.ParamNotFoundException;
import edu.alkemy.challenge.mapper.CharacterMapper;
import edu.alkemy.challenge.mapper.MovieMapper;
import edu.alkemy.challenge.repository.MovieRepository;
import edu.alkemy.challenge.service.CharacterService;
import edu.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private CharacterMapper characterMapper;
    private CharacterService characterService;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CharacterMapper characterMapper,
                            CharacterService characterService) {

        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.characterMapper = characterMapper;
        this.characterService = characterService;
    }

    @Override
    public MovieDTO create(MovieDTO movieDTO) {

        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO);
        MovieEntity savedMovie = movieRepository.save(movieEntity);

        return movieMapper.movieEntity2DTO(savedMovie, false);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();

        return movieMapper.entitySet2DtoList(movieEntities);
    }

    @Override
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO update(Long id, MovieDTO movieDTO){

        Optional<MovieEntity> entity = movieRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid movie id");
        }

        movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);

        MovieEntity entitySaved = movieRepository.save(entity.get());

        return movieMapper.movieEntity2DTO(entitySaved, false);
    }

}
