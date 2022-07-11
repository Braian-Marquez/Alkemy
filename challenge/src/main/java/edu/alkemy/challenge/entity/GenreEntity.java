package edu.alkemy.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "genre")
@Inheritance(strategy = InheritanceType.JOINED)

public class GenreEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // Integer


    private String name;


    private String image;

    @OneToMany(fetch = FetchType.EAGER,cascade = ALL)
    @JoinColumn(name = "movie_id")
    private List<MovieEntity> movie;

}
