package edu.alkemy.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CharacterFilterDTO {
    private String name;
    private Integer age;
    private Long weight;
    private Long id;
    private String order;
    private List<Long> movies;


    public CharacterFilterDTO (String name, Integer age, Long weight, Long id, List<Long> movies, String order ) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.id = id;
        this.movies = movies;
        this.order = order;

    }
    public boolean isASC(){
        return this.order.compareToIgnoreCase("ASC")==0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
