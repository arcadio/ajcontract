package org.ajcontract.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used to specify post-conditions.
 */
@Documented
@Retention(SOURCE)
@Target({CONSTRUCTOR, METHOD})
public @interface Ensures {
    String value();
}
