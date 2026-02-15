package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.ExecutableElement;
import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

/**
 * Generic node that represents an executable:
 * a <i>Constructor</i> or a <i>Method</i>.
 */
public abstract class Executable<T extends Type<T>> extends ASTNode<ExecutableElement> {
    private T enclosingType;

    private Requires requires;

    private Ensures ensures;

    public Executable(ExecutableElement element, T enclosingType) {
        super(element);
        this.enclosingType = enclosingType;
        requires = null;
        ensures = null;
    }

    public T getEnclosingType() {
        return enclosingType;
    }

    public Requires getRequires() {
        return requires;
    }

    public Ensures getEnsures() {
        return ensures;
    }

    public void setRequires(Requires requires) {
        this.requires = requires;
    }

    public void setEnsures(Ensures ensures) {
        this.ensures = ensures;
    }
}
