package by.itechart.server.specifications;

import by.itechart.server.annotations.CriteriaAnnotation;
import by.itechart.server.interfaces.FieldsInterface;

import java.lang.reflect.Field;
import java.util.List;

public class SearchCriteria<T extends FieldsInterface> {
    private Object value;
    private List<Field> fields;

    public SearchCriteria(final Object value, Class<T> classType) {
        this.value = value;
        fields = T.getFields(classType, CriteriaAnnotation.class);
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
