package by.itechart.Server.specifications;

import java.lang.reflect.Field;
import java.util.List;

public class SearchCriteria {

    private List<Field> fieldList;
    private Object value;

    public SearchCriteria(final List<Field> fieldList, final Object value) {
        this.fieldList = fieldList;
        this.value = value;
    }

    public List<Field> getFieldList() {

        return fieldList;
    }

    public void setFieldList(final List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
