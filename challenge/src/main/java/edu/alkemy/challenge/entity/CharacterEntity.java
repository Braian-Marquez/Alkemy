package edu.alkemy.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE character SET deleted = true WHERE id = ?")
@Table(name = "characters")
@Inheritance(strategy = InheritanceType.JOINED)
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "character_image")
    private String image;

    @Column(name = "character_name")
    private String name;

    @Column(name = "character_age")
    private Integer age;

    @Column(name = "character_weight_kg")
    private Double weight;

    @Column(name = "character_history")
    private String history;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "characters")
    private List<MovieEntity> movies = new ArrayList<>();

}
