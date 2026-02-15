package org.ajcontract.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used to specify invariants
 * to be satisfied by instances of the
 * decorated class.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ObjectInvariant {
    String value();
}
