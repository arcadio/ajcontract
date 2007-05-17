package org.ajcontract.annotationprocessor.ast;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;

/**
 * Node that represents a class.
 */
public class Klass extends Type<Klass> {

    private ObjectInvariant objectInvariant;

    private NodeMap<ExecutableElement, Constructor> constructors;

    public Klass(TypeElement element) {
	super(element);
	objectInvariant = null;
	final Klass enclosingClass = this;
	constructors = new NodeMap<ExecutableElement, Constructor>() {
	    @Override
	    public Constructor create(ExecutableElement element) {
		return new Constructor(element, enclosingClass);
	    }
	};
    }

    public ObjectInvariant getObjectInvariant() {
	return objectInvariant;
    }

    public void setObjectInvariant(ObjectInvariant objectInvariant) {
	this.objectInvariant = objectInvariant;
    }

    public void addContractConstructor(ExecutableElement constructor,
	    Requires requires) {
	Constructor c = getConstructor(constructor);
	c.setRequires(requires);
    }

    public void addContractConstructor(ExecutableElement constructor,
	    Ensures ensures) {
	Constructor c = getConstructor(constructor);
	c.setEnsures(ensures);
    }

    public Iterable<Constructor> getConstructors() {
	return constructors.values();
    }

    private Constructor getConstructor(ExecutableElement constructor) {
	return constructors.get(constructor);
    }
}
