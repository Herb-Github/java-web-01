package com.zz.startup.annotation;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Documented
@NotNull
@Size(min = 1, max = 30)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
public @interface ValidatorId {

    String message() default "{invalid.id}";

    Class[] groups() default {};

    Class[] payload() default {};
}
