package by.itechart.server.specifications;

import by.itechart.server.entity.Car;
import by.itechart.server.entity.Request;
import by.itechart.server.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

public class RequestSpecification implements Specification<Request> {
    private SearchCriteria criteria;

    public RequestSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Request> root, final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {

        final String[] query = criteria.getValue().toString().split("\\s+");
        final List<Predicate> predicates = new ArrayList<>();

        for (String word : query) {
            final Subquery<Request> requestCarSubquery = criteriaQuery.subquery(Request.class);
            final Root<Car> carRoot = requestCarSubquery.from(Car.class);
            final Join<Car, Request> carRequestJoin = carRoot.join("requests");
            requestCarSubquery.select(carRequestJoin).where(criteriaBuilder.like(carRoot.get("name"),
                    "%" + word + "%"));
            final Predicate carPredicate = criteriaBuilder.in(root).value(requestCarSubquery);

            final Subquery<Request> requestDriverSubquery = criteriaQuery.subquery(Request.class);
            final Root<User> userRoot = requestDriverSubquery.from(User.class);
            final Join<User, Request> userRequestJoin = userRoot.join("requests");
            requestDriverSubquery.select(userRequestJoin).where(criteriaBuilder.or(
                    criteriaBuilder.like(userRoot.get("name"), "%" + word + "%"),
                    criteriaBuilder.like(userRoot.get("surname"), "%" + word + "%"),
                    criteriaBuilder.like(userRoot.get("patronymic"), "%" + word + "%"))
            );
            final Predicate userPredicate = criteriaBuilder.in(root).value(requestDriverSubquery);

            predicates.add(criteriaBuilder.or(carPredicate, userPredicate));
        }
        Predicate finalPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            finalPredicate = criteriaBuilder.and(finalPredicate, predicates.get(i));
        }
        return finalPredicate;
    }
}
