package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

/**
 * Generic node that represents a type, which is
 * either a <i>Class</i> or an <i>Interface</i>.
 */
public abstract class Type<E extends Type<E>> extends ASTNode<TypeElement> {
    private E extended;

    private NodeMap<ExecutableElement, Method> methods;

    public Type(TypeElement element) {
        super(element);
        extended = null;
        final Type<?> enclosingType = this;
        methods = new NodeMap<ExecutableElement, Method>() {
            @Override
            public Method create(ExecutableElement element) {
                return new Method(element, enclosingType);
            }
        };
    }

    public E getExtended() {
        return extended;
    }

    public void setExtended(E extended) {
        this.extended = extended;
    }

    public void addContractMethod(ExecutableElement method, Requires requires) {
        Method m = methods.get(method);
        m.setRequires(requires);
    }

    public void addContractMethod(ExecutableElement method, Ensures ensures) {
        Method m = methods.get(method);
        m.setEnsures(ensures);
    }

    public Iterable<Method> getMethods() {
        return methods.values();
    }
}
