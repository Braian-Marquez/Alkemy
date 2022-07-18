package edu.alkemy.challenge.service;


import edu.alkemy.challenge.dto.CharacterBasicDTO;
import edu.alkemy.challenge.dto.CharacterDTO;
import lombok.NonNull;

import java.util.List;

public interface CharacterService {
    CharacterDTO save(CharacterDTO characterDTO);
    CharacterDTO update(Long id, CharacterDTO characterDTO);
    void delete(@NonNull Long id);
    List<CharacterBasicDTO> getAll();
    CharacterDTO findById(@NonNull Long id);
    List<CharacterDTO> getByFilters(String name, Long age, Long weight, List<Long> movies);



}
