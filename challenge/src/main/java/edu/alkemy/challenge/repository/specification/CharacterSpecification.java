package edu.alkemy.challenge.repository.specification;
import edu.alkemy.challenge.dto.CharacterFilterDTO;
import edu.alkemy.challenge.entity.CharacterEntity;
import edu.alkemy.challenge.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Component
public class CharacterSpecification {
    public Specification<CharacterEntity> getByFilters(CharacterFilterDTO filterDto){
        return (root, query, criteriaBuilder) ->{


            List<Predicate> predicates= new ArrayList<>();

            if(StringUtils.hasLength(filterDto.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" +filterDto.getName().toLowerCase() +"%"
                        )
                );
            }

            if(filterDto.getWeight()!= null){
                predicates.add(
                        criteriaBuilder.equal( (root.get("weight")),filterDto.getWeight())
                );

            }
            if(filterDto.getAge()!= null){
                predicates.add(
                        criteriaBuilder.equal( (root.get("age")),filterDto.getAge())
                );

            }

            if(!CollectionUtils.isEmpty(filterDto.getMovies())){
                Join<MovieEntity, CharacterEntity> join= root.join("movies", JoinType.INNER);
                Expression<String> peliculasId= join.get("id");
                predicates.add(peliculasId.in(filterDto.getMovies()));
            };
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
