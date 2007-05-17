package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import java.io.IOException;

import org.aspectj.lang.SoftException;

/**
 * Exception-softener of <i>CodeGeneratorVisitor</i>, a new
 * aspect-oriented pattern.
 */
privileged aspect IOExceptionHandler {
    declare soft: IOException: execution(* CodeGeneratorVisitor.*(..));
	
    after(CodeGeneratorVisitor codeGeneratorVisitor) throwing(SoftException softException): 
        target(codeGeneratorVisitor) &&
        execution(* CodeGeneratorVisitor.*(..)) { 
        	codeGeneratorVisitor.codeWriter.close(); 
        }
}