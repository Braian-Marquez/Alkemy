package edu.alkemy.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "genre")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE genre SET deleted = true WHERE id = ?")
@Inheritance(strategy = InheritanceType.JOINED)

public class GenreEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // Integer
    private String name;
    private String image;
    private boolean deleted;


}
