package edu.alkemy.challenge.controller;


import edu.alkemy.challenge.dto.MovieDTO;
import edu.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO movieDTO){
        MovieDTO movieSaved = movieService.create(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSaved);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getAll() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok().body(movies);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){
        MovieDTO movie = movieService.findById(id);
        return ResponseEntity.ok().body(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping
    public ResponseEntity<List<MovieDTO>> listByFilter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false, defaultValue = "ASC") String order){

        List<MovieDTO> dto = movieService.findByParam(title, genre, order);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> edit(@PathVariable Long id, @RequestParam MovieDTO dto){
        MovieDTO dtoFull = movieService.update(id, dto);
        return ResponseEntity.ok().body(dtoFull);
    }
    @PostMapping("/{id}/character/{idCharacter}")
    public ResponseEntity<Void> addCharacter (@PathVariable Long id, @PathVariable Long idCharacter){

        movieService.addCharacter(id, idCharacter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
