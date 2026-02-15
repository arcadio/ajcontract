package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.TypeElement;

/**
 * Node that represents an interface.
 */
public class Interface extends Type<Interface> {
    public Interface(TypeElement element) {
        super(element);
    }
}
