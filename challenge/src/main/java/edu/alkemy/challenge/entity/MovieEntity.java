package edu.alkemy.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "movie")
@Inheritance(strategy = InheritanceType.JOINED)
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


    private String image;


    private String title;

    @Column(name = "movie_year")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;


    private Integer rate;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "movie_character",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private Set<CharacterEntity> characters = new HashSet<>();

    public void addCharacter(CharacterEntity characterEntity) {
        characters.add(characterEntity);
    }

    public void removeCharacter(CharacterEntity characterEntity) {
        characters.remove(characterEntity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MovieEntity other = (MovieEntity) obj;
        return other.id == this.id;

    }
}
