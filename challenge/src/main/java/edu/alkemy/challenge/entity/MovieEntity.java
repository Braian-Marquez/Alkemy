package edu.alkemy.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id = ?")
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

    @ManyToMany
    @JoinTable(name = "movie_character",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private Set<CharacterEntity> characters = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MovieEntity other = (MovieEntity) obj;
        return other.id == this.id;

    }

    public void addCharacter(CharacterEntity character) {
        this.characters.add(character);
    }

    public void deleteCharacter(CharacterEntity character) {
        this.characters.remove(character);
    }
}
