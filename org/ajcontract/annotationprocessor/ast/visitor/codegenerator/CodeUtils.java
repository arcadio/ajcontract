package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.type.TypeKind.DECLARED;
import static javax.lang.model.type.TypeKind.TYPEVAR;
import static javax.lang.model.type.TypeKind.VOID;
import static javax.lang.model.type.TypeKind.WILDCARD;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.TargetType.targetTypeFor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

import org.ajcontract.annotationprocessor.ast.Executable;

/**
 * Utility class that offers many useful methods
 * for code generation tasks.
 */
public class CodeUtils {
    private static Types typeUtils;

    public static void setTypeUtils(Types newTypeUtils) {
	typeUtils = newTypeUtils;
    }

    public static String getUniqueName(ExecutableElement executableElement) {
	StringBuilder uniqueName = new StringBuilder();
	uniqueName.append(getName(executableElement));
	for (VariableElement parameter : executableElement.getParameters()) {
	    uniqueName.append('_');
	    uniqueName.append(parameter.asType().toString());
	}
	uniqueName.append("Contract");
	return eraseGenerics(eraseArraysAndPoints(uniqueName.toString()));
    }

    public static String getUniqueNamePrecondition(
	    ExecutableElement executableElement) {
	return getUniqueName(executableElement) + "Pre";
    }

    public static String getUniqueNamePostcondition(
	    ExecutableElement executableElement) {
	return getUniqueName(executableElement) + "Post";
    }

    public static String eraseGenerics(String name) {
	String erasedName = name.replaceAll("[<>]", "_");
	erasedName = erasedName.replaceAll("\\?", "any");
	erasedName = erasedName.replaceAll(" extends ", "Extends");
	erasedName = erasedName.replaceAll(" super ", "Super");
	return erasedName;
    }

    public static String eraseArraysAndPoints(String name) {
	String erasedName = name.replaceAll("\\.", "_");
	erasedName = erasedName.replaceAll("\\[\\]", "Array");
	return erasedName;
    }

    public static boolean isStatic(Executable<?> executable) {
	return executable.getElement().getModifiers().contains(STATIC);
    }

    public static String getType(Element element) {
	return getType(element.asType());
    }

    public static String getName(Element element) {
	String name = element.getSimpleName().toString();
	return name.replaceAll("\\<init\\>", "new");
    }

    public static String getTypeName(Element element) {
	String typeName = "";
	if (element.asType().getKind() != VOID) {
	    typeName = getType(element) + " " + getName(element);
	}
	return typeName;
    }

    public static String getPrototype(Executable<?> executable) {
	// Ad-hoc prototype with raw types due to AspectJ Compiler Bug
	StringBuilder prototype = new StringBuilder();
	ExecutableElement element = executable.getElement();
	TargetType targetType = targetTypeFor(executable);
	Set<Modifier> modifiers = element.getModifiers();
	List<? extends VariableElement> parameters = element.getParameters();
	for (Modifier modifier : modifiers) {
	    prototype.append(modifier + " ");
	}
	if (element.getKind() != CONSTRUCTOR) {
	    prototype.append(getRawType(element.getReturnType()) + " ");
	}
	prototype.append(getType(targetType) + "." + getName(element));
	prototype.append("(");
	Iterator<? extends VariableElement> iterator = parameters.iterator();
	while (iterator.hasNext()) {
	    prototype.append(getRawType(iterator.next().asType()));
	    if (iterator.hasNext()) {
		prototype.append(", ");
	    }
	}
	prototype.append(")");
	return prototype.toString();
    }

    private static String getRawType(TypeMirror typeMirror) {
	return typeUtils.erasure(typeMirror).toString();
    }

    private static String getType(TypeMirror typeMirror) {
	String type;
	TypeKind typeKind = typeMirror.getKind();
	if (typeKind == TYPEVAR) {
	    type = getType((TypeVariable) typeMirror);
	} else if (typeKind == DECLARED) {
	    type = getType((DeclaredType) typeMirror);
	} else if (typeKind == WILDCARD) {
	    type = getType((WildcardType) typeMirror);
	} else {
	    type = typeMirror.toString();
	}
	return type;
    }

    private static String getType(DeclaredType declaredType) {
	String type = declaredType.toString();
	if (!declaredType.getTypeArguments().isEmpty()) {
	    StringBuilder typeBuilt = new StringBuilder(type.replaceAll(
		    "\\<.+\\>", ""));
	    typeBuilt.append('<');
	    Iterator<? extends TypeMirror> parameters = declaredType
		    .getTypeArguments().iterator();
	    while (parameters.hasNext()) {
		typeBuilt.append(getType(parameters.next()));
		if (parameters.hasNext()) {
		    typeBuilt.append(", ");
		}
	    }
	    typeBuilt.append('>');
	    type = typeBuilt.toString();
	}
	return type;
    }

    private static String getType(TypeVariable typeVariable) {
	return getType(typeVariable.getUpperBound());
    }

    private static String getType(WildcardType wildcardType) {
	String type;
	TypeMirror extendsBound = wildcardType.getExtendsBound();
	if (extendsBound != null) {
	    type = "? extends " + getType(wildcardType.getExtendsBound());
	} else {
	    type = "? extends Object";
	}
	return type;
    }
}
