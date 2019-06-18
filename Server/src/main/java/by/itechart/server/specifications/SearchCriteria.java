package by.itechart.server.specifications;

import by.itechart.server.annotations.CriteriaAnnotation;
import by.itechart.server.entity.User;
import by.itechart.server.interfaces.FieldsInterface;

import java.lang.reflect.Field;
import java.util.List;

public class SearchCriteria<T extends FieldsInterface> {
    private Object value;

    private List<Field> fields;

    private Class<T> classType;

    private User.Role role;

    private int companyId;

    public User.Role getRole() {
        return role;
    }

    public SearchCriteria<T> setRole(final User.Role role) {
        this.role = role;
        return this;
    }

    public int getCompanyId() {
        return companyId;
    }

    public SearchCriteria<T> setCompanyId(final int companyId) {
        this.companyId = companyId;
        return this;
    }

    public SearchCriteria(final Object value, final User.Role role, final int companyId, Class<T> classType) {
        this.value = value;
        fields = T.getFields(classType, CriteriaAnnotation.class);
        this.classType = classType;
        this.companyId = companyId;
        this.role = role;


    }

    public Class<T> getClassType() {
        return classType;
    }

    public SearchCriteria<T> setClassType(final Class<T> classType) {
        this.classType = classType;
        return this;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
