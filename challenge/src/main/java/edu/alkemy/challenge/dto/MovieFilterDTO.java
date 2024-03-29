package edu.alkemy.challenge.dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MovieFilterDTO {
    private String title;
    private String creationDate;
    private Long genreId;
    private String order;

    public MovieFilterDTO(String title, String creationDate, Long genreId, String order) {
        this.title = title;
        this.creationDate = creationDate;
        this.genreId = genreId;
        this.order = order;
    }





    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
