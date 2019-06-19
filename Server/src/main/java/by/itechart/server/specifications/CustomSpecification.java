package by.itechart.server.specifications;

import by.itechart.server.annotations.SearchCriteriaAnnotation;
import by.itechart.server.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomSpecification<T extends GetPathInterface> implements Specification<T> {

    private SearchCriteria<T> newSearchCriteria;


    public CustomSpecification(final SearchCriteria<T> newSearchCriteria) {
        this.newSearchCriteria = newSearchCriteria;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> criteriaQuery, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(newSearchCriteria.getConditions())) {
            for (Map.Entry<List<String>, Object> entry : newSearchCriteria.getConditions().entrySet()) {
                final List<String> names = entry.getKey();
                Path path = root.get(names.get(0));
                for (int i = 1; i < names.size(); i++) {
                    path = path.get(names.get(i));
                }
                if (entry.getValue().getClass().equals(User.Role.class)) {
                    predicates.add(criteriaBuilder.isMember(entry.getValue(), path));
                } else {
                    predicates.add(criteriaBuilder.equal(path, entry.getValue()));
                }
            }
        }

        if (Objects.nonNull(newSearchCriteria.getQuery())) {
            final String[] query = newSearchCriteria.getQuery().split("\\s+");
            final List<List<String>> fields = T.getFields(newSearchCriteria.getClassType(), SearchCriteriaAnnotation.class);

            for (String word : query) {
                final List<Predicate> orPredicates = new ArrayList<>();
                for (List<String> field : fields) {
                    Path objectPath = root.get(field.get(0));
                    for (int i = 1; i < field.size(); i++) {
                        objectPath = objectPath.get(field.get(i));
                    }
                    orPredicates.add(criteriaBuilder.like(objectPath, "%" + word + "%"));
                }
                Predicate orPredicate = orPredicates.get(0);
                for (int i = 1; i < orPredicates.size(); i++) {
                    orPredicate = criteriaBuilder.or(orPredicate, orPredicates.get(i));
                }
                predicates.add(orPredicate);
            }

        }
        Predicate finalPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            finalPredicate = criteriaBuilder.and(finalPredicate, predicates.get(i));
        }
        return finalPredicate;
    }
}
