package by.itechart.server.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface FieldsInterface {
    static List<Field> getFields(Class cls, final Class<? extends Annotation> ann) {
        List<Field> fields = new ArrayList<>();
        while (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    fields.add(field);
                }
            }
            cls = cls.getSuperclass();
        }
        return fields;
    }
}
