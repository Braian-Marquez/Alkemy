package edu.alkemy.challenge.service.impl;

import edu.alkemy.challenge.dto.MovieDTO;
import edu.alkemy.challenge.dto.MovieFilterDTO;
import edu.alkemy.challenge.entity.CharacterEntity;
import edu.alkemy.challenge.entity.MovieEntity;
import edu.alkemy.challenge.exception.ParamNotFoundException;
import edu.alkemy.challenge.mapper.CharacterMapper;
import edu.alkemy.challenge.mapper.MovieMapper;
import edu.alkemy.challenge.repository.CharacterRepository;
import edu.alkemy.challenge.repository.MovieRepository;
import edu.alkemy.challenge.repository.specification.MovieSpecification;
import edu.alkemy.challenge.service.CharacterService;
import edu.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private MovieSpecification movieSpecification;
    private CharacterMapper characterMapper;
    private CharacterService characterService;
    private CharacterRepository characterRepository;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CharacterMapper characterMapper,
                            CharacterService characterService,
                            MovieSpecification movieSpecification,
                            CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
        this.movieSpecification = movieSpecification;
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
    public MovieDTO update(Long id, MovieDTO movieDTO) {

        Optional<MovieEntity> entity = movieRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid movie id");
        }

        movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);

        MovieEntity entitySaved = movieRepository.save(entity.get());

        return movieMapper.movieEntity2DTO(entitySaved, false);
    }

    public MovieDTO findById(Long id) {
        Optional<MovieEntity> movie = movieRepository.findById(id);
        if (!movie.isPresent()) {

            throw new ParamNotFoundException("movie not found");
        }
        MovieEntity movieEntity = movie.get();
        MovieDTO dtoFull = movieMapper.movieEntity2DTO(movieEntity, true);

        return dtoFull;
    }

    @Override
    public List<MovieDTO> getMovieByFilters(String title, String creationDate, Long genreId, String order) {

        MovieFilterDTO filtersDTO = new MovieFilterDTO(title, creationDate, genreId, order);

        List<MovieEntity> movies = this.movieRepository.findAll(
                this.movieSpecification.getByFilters(filtersDTO)
        );

        return this.movieMapper.movieEntityList2DTOList(movies, true);
    }


    public MovieDTO addCharacter(Long idMovie, Long idCharacter) {
        Optional<MovieEntity> find = movieRepository.findById(idMovie);
        if (!find.isPresent()) {

            throw new ParamNotFoundException("Movie not Found");
        }
        Optional<CharacterEntity> findCharacter = characterRepository.findById(idCharacter);
        if (!findCharacter.isPresent()) {

            throw new ParamNotFoundException("Character not Found");
        }

        CharacterEntity character = findCharacter.get();
        MovieEntity movie = find.get();
        movie.addCharacter(character);
        MovieDTO dto = movieMapper.movieEntity2DTO(movie, true);
        return dto;
    }


    public MovieDTO deleteCharacter(Long idMovie, Long idCharacter) {
        return null;
    }


}
