package org.ajcontract.annotationprocessor;

import static java.util.Collections.emptySet;
import static javax.lang.model.SourceVersion.RELEASE_6;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;
import org.ajcontract.annotationprocessor.ast.Interface;
import org.ajcontract.annotationprocessor.ast.Klass;
import org.ajcontract.annotationprocessor.ast.NodeMap;
import org.ajcontract.annotationprocessor.ast.visitor.ASTVisitor;
import org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeGeneratorVisitor;

/**
 * Annotation processor that plugs-in <i>javac</i>
 * and serves as a compiler of AJContract.
 */
@SupportedAnnotationTypes({"org.ajcontract.annotation.*"})
@SupportedOptions({})
@SupportedSourceVersion(RELEASE_6)
public class AJContractAnnotationProcessor extends AbstractProcessor {
    private NodeMap<TypeElement, Klass> classes;

    @SuppressWarnings("unused")
    private NodeMap<TypeElement, Interface> interfaces;

    public AJContractAnnotationProcessor() {
	classes = new NodeMap<TypeElement, Klass>() {
	    @Override
	    public Klass create(TypeElement element) {
		return new Klass(element);
	    }
	};
	interfaces = new NodeMap<TypeElement, Interface>() {
	    @Override
	    public Interface create(TypeElement element) {
		return new Interface(element);
	    }
	};
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
	    RoundEnvironment roundEnv) {
	if (annotations.size() != 0) {
	    processAnnotations(annotations, roundEnv);
	} else {
	    generateCode();
	}
	return true;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element,
	    AnnotationMirror annotation, ExecutableElement member,
	    String userText) {
	return emptySet();
    }

    @SuppressWarnings("unchecked")
    private void processAnnotations(Set<? extends TypeElement> annotations,
	    RoundEnvironment roundEnv) {
	for (TypeElement te : annotations) {
	    Set<? extends Element> annotated = roundEnv
		    .getElementsAnnotatedWith(te);
	    for (Element e : annotated) {
		try {
		    Class<?> c = Class
			    .forName(te.getQualifiedName().toString());
		    Annotation a = e
			    .getAnnotation((Class<? extends Annotation>) c);
		    process(e, a);
		} catch (ClassNotFoundException ex) {
		    assert false;
		}
	    }
	}
    }

    private void process(Element element, Annotation annotation) {
	ElementKind elementKind = element.getKind();
	switch (elementKind) {
	case CONSTRUCTOR:
	    processConstructor((ExecutableElement) element, annotation);
	    break;
	case METHOD:
	    processMethod((ExecutableElement) element, annotation);
	    break;
	case CLASS:
	    processClass((TypeElement) element, annotation);
	    break;
	case INTERFACE:
	    // TODO
	    break;
	default:
	    assert false;
	    break;
	}
    }

    private void processConstructor(ExecutableElement constructor,
	    Annotation annotation) {
	Klass klass = getClassFor(constructor);
	if (annotation.annotationType() == Requires.class) {
	    klass.addContractConstructor(constructor, (Requires) annotation);
	} else if (annotation.annotationType() == Ensures.class) {
	    klass.addContractConstructor(constructor, (Ensures) annotation);
	} else {
	    assert false;
	}
    }

    private void processMethod(ExecutableElement method, Annotation annotation) {
	Klass klass = getClassFor(method);
	if (annotation.annotationType() == Requires.class) {
	    klass.addContractMethod(method, (Requires) annotation);
	} else if (annotation.annotationType() == Ensures.class) {
	    klass.addContractMethod(method, (Ensures) annotation);
	} else {
	    assert false;
	}
    }

    private void processClass(TypeElement klass, Annotation annotation) {
	Klass k = classes.get(klass);
	if (annotation.annotationType() == ObjectInvariant.class) {
	    k.setObjectInvariant((ObjectInvariant) annotation);
	} else {
	    assert false;
	}
    }

    private Klass getClassFor(ExecutableElement element) {
	TypeElement enclosingElement = (TypeElement) element
		.getEnclosingElement();
	return classes.get(enclosingElement);
    }

    private void generateCode() {
	ASTVisitor<Object, Object> cg = new CodeGeneratorVisitor(processingEnv);
	for (Klass k : classes.values()) {
	    cg.visit(k, null);
	}
    }
}
