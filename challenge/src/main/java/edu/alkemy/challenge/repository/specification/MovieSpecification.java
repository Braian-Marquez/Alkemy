package edu.alkemy.challenge.repository.specification;
import edu.alkemy.challenge.dto.MovieFilterDTO;
import edu.alkemy.challenge.entity.GenreEntity;
import edu.alkemy.challenge.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class MovieSpecification {
    public Specification<MovieEntity> getByFilters(MovieFilterDTO filterDto) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(filterDto.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filterDto.getTitle() + "%"
                        )
                );
            }
            if (filterDto.getGenreId() != null) {
                Join<GenreEntity, MovieEntity> join = root.join("genre", JoinType.INNER);
                Expression<String> genreId = join.get("id");
                predicates.add(genreId.in(filterDto.getGenreId()));
            }
            if (StringUtils.hasLength(filterDto.getCreationDate())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(filterDto.getCreationDate(), formatter);

                predicates.add(
                        criteriaBuilder.equal(root.<LocalDate>get("creationDate"), date)
                );
            }

            // Remove duplicates
            query.distinct(true);

            // Order resolver
            String orderByField = "creationDate";


            query.orderBy(
                    filterDto.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    };
}}





