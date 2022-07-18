package edu.alkemy.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CharacterFilterDTO {
    private String name;
    private Long age;
    private Long weight ;
    private List<Long> movies;

    public CharacterFilterDTO(String name, Long age, Long weight, List<Long> movies) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.movies = movies;
    }


}
