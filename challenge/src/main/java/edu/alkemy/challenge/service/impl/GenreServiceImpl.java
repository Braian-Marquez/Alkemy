package edu.alkemy.challenge.service.impl;

import edu.alkemy.challenge.dto.GenreDTO;
import edu.alkemy.challenge.entity.GenreEntity;
import edu.alkemy.challenge.exception.ParamNotFoundException;
import edu.alkemy.challenge.mapper.GenreMapper;
import edu.alkemy.challenge.repository.GenreRepository;
import edu.alkemy.challenge.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreMapper genreMapper;
    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreMapper genreMapper, GenreRepository genreRepository) {
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }

    @Override
    public GenreDTO save(GenreDTO genreDTO) {

        GenreEntity entity = genreMapper.genreDTO2Entity(genreDTO);
        GenreEntity  entitySaved = genreRepository.save(entity);

        return genreMapper.genreEntity2DTO(entitySaved);
    }

    @Override
    public List<GenreDTO> getAllGenres() {

        List<GenreEntity> entities = genreRepository.findAll();

        return genreMapper.genreEntityList2DTOList(entities);
    }

    @Override
    public void delete(Long id) {
        this.genreRepository.deleteById(id);
    }

    @Override
    public GenreDTO update(Long id, GenreDTO genreDTO){

        Optional<GenreEntity> entity = genreRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid character id");
        }

        genreMapper.genreEntityRefreshValues(entity.get(), genreDTO);

        GenreEntity entitySaved = genreRepository.save(entity.get());

        return genreMapper.genreEntity2DTO(entitySaved);
    }
}
