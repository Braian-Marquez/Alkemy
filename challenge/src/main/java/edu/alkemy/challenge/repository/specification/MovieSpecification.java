package edu.alkemy.challenge.repository.specification;
import edu.alkemy.challenge.dto.MovieFilterDTO;
import edu.alkemy.challenge.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Component
public class MovieSpecification {
    public Specification<MovieEntity> getByFilters(MovieFilterDTO filterDto){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>();
            if (StringUtils.hasLength(filterDto.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%"+filterDto.getName()+"%"
                        )
                );
            }
            if (StringUtils.hasLength(filterDto.getGenre())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("genre")),
                                "%"+filterDto.getGenre()+"%"
                        )
                );
            }
            query.distinct(true);

            String orderByField="order";
            query.orderBy(
                    filterDto.isASC()?
                            criteriaBuilder.asc(root.get(orderByField)):
                            criteriaBuilder.desc(root.get(orderByField))
            );


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
