package edu.alkemy.challenge.controller;

import edu.alkemy.challenge.dto.CharacterBasicDTO;
import edu.alkemy.challenge.dto.CharacterDTO;
import edu.alkemy.challenge.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO characterDTO) {
        CharacterDTO characterSaved = characterService.save(characterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterBasicDTO>> getAll() {
        List<CharacterBasicDTO> characters = characterService.getAll();
        return ResponseEntity.ok().body(characters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> characterFullId(@PathVariable Long id) {
        CharacterDTO characterDTO = this.characterService.findById(id);
        return ResponseEntity.ok().body(characterDTO);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CharacterDTO>> getByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long age,
            @RequestParam(required = false) Long weight,
            @RequestParam(required = false) List<Long> movies
    ) {
        List<CharacterDTO> dtos = this.characterService.getByFilters(name, age, weight, movies);
        return ResponseEntity.ok(dtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> edit(@PathVariable Long id, @RequestBody CharacterDTO dto) {

        CharacterDTO dtoFull = characterService.update(id, dto);
        return ResponseEntity.ok().body(dtoFull);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
