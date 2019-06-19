package by.itechart.server.specifications;

import by.itechart.server.annotations.SearchCriteriaAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface GetPathInterface {
    static List<List<String>> getFields(Class cls, final Class<? extends Annotation> ann) {
        List<List<String>> result = new ArrayList<>();
        while (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    SearchCriteriaAnnotation searchCriteriaAnnotation =
                            field.getAnnotation(SearchCriteriaAnnotation.class);
                    List<String> tempPaths = new ArrayList<>();
                    if(!searchCriteriaAnnotation.path().equals(""))
                        tempPaths = Arrays.asList(searchCriteriaAnnotation.path().split(";"));

                    for(String str: tempPaths){
                        final List<String> list = new ArrayList<>();
                        list.add(field.getName());
                        list.addAll(Arrays.asList(str.trim().split(" ")));
                        result.add(list);
                    }

                    if(tempPaths.size() == 0){
                        result.add(Arrays.asList(field.getName()));
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return result;
    }
}
