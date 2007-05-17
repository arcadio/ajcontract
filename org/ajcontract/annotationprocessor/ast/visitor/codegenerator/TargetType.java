package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.ajcontract.annotationprocessor.ast.Executable;
import org.ajcontract.annotationprocessor.ast.Klass;

/**
 * <i>Element</i> that represents the target type
 * in order to allow transparent code generation. It
 * is useful to avoid meeding to calculate type erasure.
 */
public class TargetType implements Element {
    private TypeElement typeElement;

    private TypeMirror typeMirror;

    private static Types typeUtils;

    public static void setTypeUtils(Types newTypeUtils) {
	typeUtils = newTypeUtils;
    }

    public TargetType(TypeElement typeElement) {
	this.typeElement = typeElement;
	typeMirror = typeUtils.erasure(typeElement.asType());
    }

    public static TargetType targetTypeFor(Klass klass) {
	return new TargetType(klass.getElement());
    }

    public static TargetType targetTypeFor(Executable<?> executable) {
	return new TargetType(executable.getEnclosingType().getElement());
    }

    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
	return typeElement.accept(v, p);
    }

    public TypeMirror asType() {
	return typeMirror;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
	return typeElement.getAnnotation(annotationType);
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
	return typeElement.getAnnotationMirrors();
    }

    public List<? extends Element> getEnclosedElements() {
	return typeElement.getEnclosedElements();
    }

    public Element getEnclosingElement() {
	return typeElement.getEnclosingElement();
    }

    public ElementKind getKind() {
	return typeElement.getKind();
    }

    public Set<Modifier> getModifiers() {
	return typeElement.getModifiers();
    }

    public Name getSimpleName() {
	return typeElement.getSimpleName();
    }
}
