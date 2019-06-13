package by.itechart.Server.specifications;

import by.itechart.Server.entity.Car;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarSpecificationBuilder {

    private final List<SearchCriteria> params;

    public CarSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public CarSpecificationBuilder with(String key, Object value) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public Specification<Car> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(CarSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(specs.get(i));
        }
        return result;
    }
}