package org.ajcontract.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(SOURCE)
@Target({CONSTRUCTOR, METHOD})
public @interface Requires {
    String precondition();

    Class<? extends RuntimeException> exception() 
    	default IllegalArgumentException.class;
}
