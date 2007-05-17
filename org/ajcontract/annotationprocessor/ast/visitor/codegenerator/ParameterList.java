package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import static javax.lang.model.type.TypeKind.VOID;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getName;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getType;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getTypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.lang.model.element.Element;

import org.ajcontract.annotationprocessor.ast.Executable;

/**
 * Data structure that holds a list of parameters of an
 * <i>Executable</i> during code generation, and offers
 * multiple representations of it.
 */
public class ParameterList {
    private Collection<Element> parameters = new ArrayList<Element>();

    private ParameterBuilder typeBuilder = new ParameterBuilder() {
	@Override
	public String getParameterRepresentation(Element element) {
	    return getType(element);
	}
    };

    private ParameterBuilder nameBuilder = new ParameterBuilder() {
	@Override
	public String getParameterRepresentation(Element element) {
	    return getName(element);
	}
    };

    private ParameterBuilder typeNameBuilder = new ParameterBuilder() {
	@Override
	public String getParameterRepresentation(Element element) {
	    return getTypeName(element);
	}
    };

    private ParameterList(Element element) {
	if (element.asType().getKind() != VOID) {
	    parameters.add(element);
	}
    }

    private ParameterList(Collection<? extends Element> elements) {
	parameters.addAll(elements);
    }

    public static ParameterList parameterListFor(Executable<?> executable) {
	return new ParameterList(executable.getElement().getParameters());
    }

    public static ParameterList parameterListFor(Element element,
	    Executable<?> executable) {
	ParameterList parameterList = new ParameterList(element);
	parameterList.add(executable.getElement().getParameters());
	return parameterList;
    }

    public void add(Element element) {
	parameters.add(element);
    }

    public void add(Collection<? extends Element> elements) {
	parameters.addAll(elements);
    }

    public String getTypeList() {
	return buildListRepresentation(typeBuilder);
    }

    public String getNameList() {
	return buildListRepresentation(nameBuilder);
    }

    public String getTypeNameList() {
	return buildListRepresentation(typeNameBuilder);
    }

    private String buildListRepresentation(ParameterBuilder builder) {
	StringBuilder parameterRepresentation = new StringBuilder();
	Iterator<Element> parameterIterator = parameters.iterator();
	while (parameterIterator.hasNext()) {
	    parameterRepresentation.append(builder
		    .getParameterRepresentation(parameterIterator.next()));
	    if (parameterIterator.hasNext()) {
		parameterRepresentation.append(", ");
	    }
	}
	return parameterRepresentation.toString();
    }

    private abstract class ParameterBuilder {
	public abstract String getParameterRepresentation(Element element);
    }
}
