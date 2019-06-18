package by.itechart.server.specifications;

import by.itechart.server.entity.WayBill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class WayBillSpecification implements Specification<WayBill> {
    private SearchCriteria criteria;

    public WayBillSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(final Root<WayBill> root, final CriteriaQuery<?> criteriaQuery, final CriteriaBuilder criteriaBuilder) {
        final String[] query = criteria.getValue().toString().split("\\s+");
        final List<Predicate> predicates = new ArrayList<>();

        for (String word : query) {
            final Predicate predicate1 = criteriaBuilder.like(
                    root.get("invoice").get("request").get("car").get("name"), "%" + word + "%");
            final Predicate predicate2 = criteriaBuilder.like(
                    root.get("invoice").get("request").get("driver").get("name"), "%" + word + "%");
            final Predicate predicate3 = criteriaBuilder.like(
                    root.get("invoice").get("request").get("driver").get("surname"), "%" + word + "%");
            final Predicate predicate4 = criteriaBuilder.like(
                    root.get("invoice").get("request").get("driver").get("patronymic"), "%" + word + "%");
            predicates.add(criteriaBuilder.or(predicate1, predicate2, predicate3, predicate4));
        }
        Predicate finalPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            finalPredicate = criteriaBuilder.and(finalPredicate, predicates.get(i));
        }
        return finalPredicate;
    }
}
