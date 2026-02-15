package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.ExecutableElement;

/**
 * Node that represents a constructor.
 */
public class Constructor extends Executable<Klass> {
    public Constructor(ExecutableElement element, Klass enclosingClass) {
        super(element, enclosingClass);
    }
}
