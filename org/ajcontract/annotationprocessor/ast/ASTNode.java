package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.Element;

/**
 * Generic node of the Abstract Syntax Tree
 * of AJContract.
 */
public abstract class ASTNode<E extends Element> {
    private final E element;

    public ASTNode(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }
}
