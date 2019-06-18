package by.itechart.server.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public CustomSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {

        final String[] query = criteria.getValue().toString().split("\\s+");
        final List<Predicate> predicates = new ArrayList<>();
        final List<Field> fields = criteria.getFields();

        for (String word : query) {
            Predicate predicate = criteriaBuilder.like(root.<String>get(fields.get(0).getName()),
                    "%" + word + "%");
            for (int i = 1; i < fields.size(); i++) {
                predicate = criteriaBuilder.or(predicate,
                        criteriaBuilder.like(root.<String>get(fields.get(i).getName()), "%" + word + "%"));
            }
            predicates.add(predicate);
        }

        Predicate finalPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            finalPredicate = criteriaBuilder.and(finalPredicate, predicates.get(i));
        }

        if (criteria.getCompanyId() != -1) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(
                    root.<String>get("clientCompany"), criteria.getCompanyId()));
        }

        if (Objects.nonNull(criteria.getRole())) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.isMember(criteria.getRole(), root.get("roles")));
        }
        return finalPredicate;
    }
}
