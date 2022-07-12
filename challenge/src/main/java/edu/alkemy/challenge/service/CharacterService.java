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
    List<CharacterDTO> findByParam(String name, Integer age,Long weight, Long id,
                                              List<Long> movies, String order);


}
