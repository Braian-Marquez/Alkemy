package edu.alkemy.challenge.service.impl;

import edu.alkemy.challenge.dto.CharacterBasicDTO;
import edu.alkemy.challenge.dto.CharacterDTO;
import edu.alkemy.challenge.dto.CharacterFilterDTO;
import edu.alkemy.challenge.entity.CharacterEntity;
import edu.alkemy.challenge.exception.ParamNotFoundException;
import edu.alkemy.challenge.mapper.CharacterMapper;
import edu.alkemy.challenge.repository.CharacterRepository;
import edu.alkemy.challenge.repository.specification.CharacterSpecification;
import edu.alkemy.challenge.service.CharacterService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterMapper characterMapper;
    private CharacterRepository characterRepository;
    private CharacterSpecification characterSpecification;

    @Autowired
    public CharacterServiceImpl(CharacterMapper characterMapper,
                                CharacterRepository characterRepository, CharacterSpecification characterSpecification) {
        this.characterSpecification = characterSpecification;
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
    }

    public CharacterDTO save(CharacterDTO characterDTO) {

        CharacterEntity entity = characterMapper.characterDTO2Entity(characterDTO);
        CharacterEntity entitySaved = characterRepository.save(entity);

        return characterMapper.characterEntity2DTO(entitySaved, false);
    }

    public CharacterDTO update(Long id, CharacterDTO characterDTO) {

        Optional<CharacterEntity> entity = characterRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid character id");
        }

        characterMapper.characterEntityRefreshValues(entity.get(), characterDTO);
        CharacterEntity entitySaved = characterRepository.save(entity.get());

        return characterMapper.characterEntity2DTO(entitySaved, false);

    }

    public void delete(@NonNull Long id) {
        characterRepository.deleteById(id);
    }

    public CharacterDTO findById(@NonNull Long id) {

        Optional<CharacterEntity> entity = characterRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid character id.");
        }

        return characterMapper.characterEntity2DTO(entity.get(), true);
    }

    public List<CharacterBasicDTO> getAll() {

        List<CharacterEntity> entities = characterRepository.findAll();

        return this.characterMapper.characterEntitySet2BasicDTOList(entities);
    }

    @Override
    public List<CharacterDTO> getByFilters(String name, Long age, Long weight, List<Long> movies) {

        CharacterFilterDTO filtersDTO = new CharacterFilterDTO(name, age, weight, movies);

        List<CharacterEntity> entityList = this.characterRepository.findAll(
                this.characterSpecification.getByFilters(filtersDTO)
        );

        return this.characterMapper.characterEntitySet2DTOList(entityList, true);
    }


}
