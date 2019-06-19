package by.itechart.server.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
public @interface SearchCriteriaAnnotation {
    String path() default "";
}
