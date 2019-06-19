package by.itechart.server.specifications;

import java.util.List;
import java.util.Map;

public class SearchCriteria<T extends GetPathInterface> {

    private Map<List<String>, Object> conditions;
    private Class<T> classType;
    private String query;

    public SearchCriteria(final Map<List<String>, Object> conditions, final Class<T> classType,
                          final String query) {
        this.conditions = conditions;
        this.classType = classType;
        this.query = query;
    }

    public SearchCriteria(final Class<T> classType, final String query) {
        this.classType = classType;
        this.query = query;
    }

    public SearchCriteria(final Map<List<String>, Object> conditions, final Class<T> classType) {
        this.conditions = conditions;
        this.classType = classType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public Class<T> getClassType() {
        return classType;
    }

    public void setClassType(final Class<T> classType) {
        this.classType = classType;
    }

    public Map<List<String>, Object> getConditions() {
        return conditions;
    }

    public void setConditions(final Map<List<String>, Object> conditions) {
        this.conditions = conditions;
    }
}
