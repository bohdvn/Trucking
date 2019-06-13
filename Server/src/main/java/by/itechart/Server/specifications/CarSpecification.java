package by.itechart.Server.specifications;

import by.itechart.Server.entity.Car;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CarSpecification implements Specification<Car> {
    private SearchCriteria criteria;

    public CarSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Car> root, final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        final String[] query = criteria.getValue().toString().split("\\s+");
        final Predicate predicate = criteriaBuilder.like(root.<String>get(criteria.getKey()),
                "%" + query[0] + "%");
        for(String word: query){
           // predicate.
        }
        return criteriaBuilder.like(
                root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
    }
}
