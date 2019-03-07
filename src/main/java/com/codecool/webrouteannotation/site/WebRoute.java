package com.codecool.webrouteannotation.site;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebRoute {
//    String value() default "";
    HttpMethod method() default HttpMethod.GET;
    String path() default "";
}
