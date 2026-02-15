package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getName;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getPrototype;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getType;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getTypeName;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getUniqueName;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getUniqueNamePostcondition;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.getUniqueNamePrecondition;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeUtils.isStatic;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeWriter.createCodeWriter;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.CodeWriter.setFiler;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.ParameterList.parameterListFor;
import static org.ajcontract.annotationprocessor.ast.visitor.codegenerator.TargetType.targetTypeFor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import org.ajcontract.annotation.Requires;
import org.ajcontract.annotationprocessor.AJContractAnnotationProcessor;
import org.ajcontract.annotationprocessor.ast.Constructor;
import org.ajcontract.annotationprocessor.ast.Executable;
import org.ajcontract.annotationprocessor.ast.Interface;
import org.ajcontract.annotationprocessor.ast.Klass;
import org.ajcontract.annotationprocessor.ast.Method;
import org.ajcontract.annotationprocessor.ast.visitor.ASTVisitor;

/**
 * <i>Visitor</i> that traverses the <i>ASTNode</i> hierarchy
 * for the last time generating code.
 */
public class CodeGeneratorVisitor extends ASTVisitor<Object, Object> {
    private Elements elementUtils;

    private CodeWriter codeWriter;

    public CodeGeneratorVisitor(ProcessingEnvironment processingEnvironment) {
        elementUtils = processingEnvironment.getElementUtils();
        CodeUtils.setTypeUtils(processingEnvironment.getTypeUtils());
        ReturnValue.setElementUtils(elementUtils);
        TargetType.setTypeUtils(processingEnvironment.getTypeUtils());
        setFiler(processingEnvironment.getFiler());
        codeWriter = null;
    }

    @Override
    public Object visit(Constructor constructor, Object p) {
        if (constructor.getRequires() != null) {
            generateStaticPointcut(constructor);
            generateBeforeStaticAdvice(constructor);
            generateIntertypePrecondition(constructor);
        }
        if (constructor.getEnsures() != null) {
            generateNonStaticPointcut(constructor);
            generateAfterNonStaticAdvice(constructor);
            generateIntertypePostcondition(constructor);
        }
        return null;
    }

    @Override
    public Object visit(Method method, Object p) {
        generatePointcut(method);
        if (method.getRequires() != null) {
            generateBeforeAdvice(method);
            generateIntertypePrecondition(method);
        }
        if (method.getEnsures() != null) {
            generateAfterAdvice(method);
            generateIntertypePostcondition(method);
        }
        return null;
    }

    @Override
    public Object visit(Klass klass, Object p) {
        codeWriter = createCodeWriter(getPackageName(klass), getAspectName(klass) + ".aj");
        generateAspectDeclarationHeader(klass);
        if (klass.getObjectInvariant() != null) {
            generateObjectInvariantPointcut(klass);
            generateObjectInvariantAdviceIntertype(klass);
        }
        for (Constructor constructor : klass.getConstructors()) {
            constructor.accept(this, null);
        }
        for (Method method : klass.getMethods()) {
            method.accept(this, null);
        }
        generateAspectDeclarationEnd();
        codeWriter.close();
        return null;
    }

    @Override
    public Object visit(Interface interfaze, Object p) {
        throw new UnsupportedOperationException();
    }

    private void generateAspectDeclarationHeader(Klass klass) {
        codeWriter.writeSentence("package " + getPackageName(klass));
        codeWriter.writeln();
        codeWriter.writeln(getGeneratedAnnotation(klass));
        codeWriter.write("public privileged aspect " + getAspectName(klass));
        codeWriter.beginBlock();
    }

    private void generateAspectDeclarationEnd() {
        codeWriter.endBlock();
    }

    private void generatePointcut(Method method) {
        if (isStatic(method)) {
            generateStaticPointcut(method);
        } else {
            generateNonStaticPointcut(method);
        }
    }

    private void generateStaticPointcut(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        ParameterList parameters = parameterListFor(executable);
        String condition = isStatic(executable) ? "execution" : "preinitialization";
        codeWriter.writeln();
        codeWriter.writeln(
                "private pointcut " + getUniqueNamePrecondition(element) + "(" + parameters.getTypeNameList() + "):");
        codeWriter.increaseIndent();
        codeWriter.writeln(condition + "(" + getPrototype(executable) + ") &&");
        codeWriter.writeSentence("args(" + parameters.getNameList() + ")");
        codeWriter.decreaseIndent();
    }

    private void generateObjectInvariantPointcut(Klass klass) {
        TargetType targetType = targetTypeFor(klass);
        codeWriter.write("private pointcut objectInvariant(" + getTypeName(targetType) + "):");
        codeWriter.writeln();
        codeWriter.increaseIndent();
        codeWriter.writeln("target(" + getName(targetType) + ") &&");
        codeWriter.writeln("within(" + getType(targetType) + ") &&");
        codeWriter.writeln("(execution(public * " + getType(targetType) + ".*(..)) ||");
        codeWriter.writeSentence("execution(" + getType(targetType) + ".new(..)))");
        codeWriter.decreaseIndent();
    }

    private void generateNonStaticPointcut(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ParameterList objectParameters = parameterListFor(targetType, executable);
        ParameterList parameters = parameterListFor(executable);
        codeWriter.writeln();
        codeWriter.writeln(
                "private pointcut " + getUniqueName(element) + "(" + objectParameters.getTypeNameList() + "):");
        codeWriter.increaseIndent();
        codeWriter.writeln("target(" + getName(targetType) + ") &&");
        codeWriter.writeln("args(" + parameters.getNameList() + ") &&");
        codeWriter.writeln("within(" + getType(targetType) + ") &&");
        codeWriter.writeSentence("execution(" + getPrototype(executable) + ")");
        codeWriter.decreaseIndent();
    }

    private void generateBeforeAdvice(Method method) {
        if (isStatic(method)) {
            generateBeforeStaticAdvice(method);
        } else {
            generateBeforeNonStaticAdvice(method);
        }
    }

    private void generateBeforeStaticAdvice(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ParameterList parameters = parameterListFor(executable);
        codeWriter.writeln();
        codeWriter.write("before(" + parameters.getTypeNameList() + "): "
                + getUniqueNamePrecondition(element) + "("
                + parameters.getNameList() + ")");
        codeWriter.beginBlock();
        codeWriter.writeSentence(
                getType(targetType) + "." + getUniqueNamePrecondition(element) + "(" + parameters.getNameList() + ")");
        codeWriter.endBlock();
    }

    private void generateBeforeNonStaticAdvice(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ParameterList objectParameters = parameterListFor(targetType, executable);
        ParameterList parameters = parameterListFor(executable);
        codeWriter.writeln();
        codeWriter.write("before(" + objectParameters.getTypeNameList() + "): "
                + getUniqueName(element) + "(" + objectParameters.getNameList()
                + ")");
        codeWriter.beginBlock();
        codeWriter.writeSentence(
                getName(targetType) + "." + getUniqueNamePrecondition(element) + "(" + parameters.getNameList() + ")");
        codeWriter.endBlock();
    }

    private void generateAfterAdvice(Method method) {
        if (isStatic(method)) {
            generateAfterStaticAdvice(method);
        } else {
            generateAfterNonStaticAdvice(method);
        }
    }

    private void generateAfterStaticAdvice(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ReturnValue returnValue = new ReturnValue(element.getReturnType());
        ParameterList parameters = parameterListFor(executable);
        ParameterList returnParameters = parameterListFor(returnValue, executable);
        codeWriter.writeln();
        codeWriter.write("after(" + parameters.getTypeNameList()
                + ") returning (" + getTypeName(returnValue) + "): "
                + getUniqueNamePrecondition(element) + "("
                + parameters.getNameList() + ")");
        codeWriter.beginBlock();
        codeWriter.writeSentence(getType(targetType) + "."
                + getUniqueNamePostcondition(element) + "("
                + returnParameters.getNameList() + ")");
        codeWriter.endBlock();
    }

    private void generateAfterNonStaticAdvice(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ReturnValue returnValue = new ReturnValue(element.getReturnType());
        ParameterList objectParameters = parameterListFor(targetType, executable);
        ParameterList returnParameters = parameterListFor(returnValue, executable);
        codeWriter.writeln();
        codeWriter.write("after(" + objectParameters.getTypeNameList()
                + ") returning (" + getTypeName(returnValue) + "): "
                + getUniqueName(element) + "(" + objectParameters.getNameList()
                + ")");
        codeWriter.beginBlock();
        codeWriter.writeSentence(getName(targetType) + "."
                + getUniqueNamePostcondition(element) + "("
                + returnParameters.getNameList() + ")");
        codeWriter.endBlock();
    }

    private void generateIntertypePrecondition(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ParameterList parameters = parameterListFor(executable);
        Requires requires = executable.getRequires();
        codeWriter.writeln();
        String header = "private ";
        if (element.getKind() == CONSTRUCTOR || isStatic(executable)) {
            header += "static ";
        }
        header += "void " + getType(targetType) + "."
                + getUniqueNamePrecondition(element) + "("
                + parameters.getTypeNameList() + ")";
        codeWriter.write(header);
        codeWriter.beginBlock();
        codeWriter.write("if (!(" + requires.precondition() + "))");
        codeWriter.beginBlock();
        codeWriter.writeSentence("throw new " + getException(executable) + "()");
        codeWriter.endBlock();
        codeWriter.endBlock();
    }

    private void generateIntertypePostcondition(Executable<?> executable) {
        ExecutableElement element = executable.getElement();
        TargetType targetType = targetTypeFor(executable);
        ParameterList parameters;
        if (element.getKind() == METHOD) {
            parameters = parameterListFor(new ReturnValue(element.getReturnType()), executable);
        } else {
            parameters = parameterListFor(executable);
        }
        codeWriter.writeln();
        String header = "private ";
        if (isStatic(executable)) {
            header += "static ";
        }
        header += "void " + getType(targetType) + "."
                + getUniqueNamePostcondition(element) + "("
                + parameters.getTypeNameList() + ")";
        codeWriter.write(header);
        codeWriter.beginBlock();
        codeWriter.writeSentence("assert " + executable.getEnsures().value());
        codeWriter.endBlock();
    }

    private void generateObjectInvariantAdviceIntertype(Klass klass) {
        TargetType targetType = targetTypeFor(klass);
        codeWriter.writeln();
        codeWriter.write("after(" + getTypeName(targetType) + "): " + "objectInvariant(" + getName(targetType) + ")");
        codeWriter.beginBlock();
        codeWriter.writeSentence(getName(targetType) + ".objectInvariant()");
        codeWriter.endBlock();
        codeWriter.writeln();
        codeWriter.write("private void " + getType(targetType) + "." + "objectInvariant()");
        codeWriter.beginBlock();
        codeWriter.writeSentence("assert " + klass.getObjectInvariant().value());
        codeWriter.endBlock();
    }

    private String getPackageName(Klass klass) {
        return elementUtils.getPackageOf(klass.getElement()).toString();
    }

    private String getAspectName(Klass klass) {
        return klass.getElement().getSimpleName() + "Contracts";
    }

    private String getGeneratedAnnotation(Klass klass) {
        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");
        String generator = AJContractAnnotationProcessor.class.getCanonicalName();
        String source = klass.getElement().getQualifiedName().toString();
        String date = iso8601.format(new Date());
        return "@Generated(\n\tvalue = \"" + generator + "\",\n\tcomments = \"" + source + "\",\n\tdate = \"" + date
                + "\")";
    }

    private String getException(Executable<?> executable) {
        List<? extends AnnotationMirror> annotationMirrors =
                executable.getElement().getAnnotationMirrors();
        String exception = "";
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType().toString().equals(Requires.class.getCanonicalName())) {
                for (ExecutableElement element : methodsIn(
                        annotationMirror.getAnnotationType().asElement().getEnclosedElements())) {
                    if (element.getSimpleName().toString().equals("exception")) {
                        exception = elementUtils
                                .getElementValuesWithDefaults(annotationMirror)
                                .get(element)
                                .getValue()
                                .toString();
                    }
                }
            }
        }
        return exception;
    }
}
