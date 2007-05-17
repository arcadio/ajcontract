package org.ajcontract.annotationprocessor.ast.visitor;

import org.ajcontract.annotationprocessor.ast.*;

/**
 * Generic crosscutting implementation of the Visitor
 * design pattern.
 */
public abstract class ASTVisitor<R, P> {
   public abstract R visit(Constructor constructor, P p);

   public abstract R visit(Method method, P p);
 
   public abstract R visit(Klass klass, P p);
   
   public abstract R visit(Interface interfaze, P p);
 
   static abstract aspect ASTVisitorProtocol { 
       public <R, P> R Constructor.accept(ASTVisitor<R, P> visitor, P p) { 
	   return visitor.visit(this, p); 
       }
  
       public <R, P> R Method.accept(ASTVisitor<R, P> visitor, P p) { 
	   return visitor.visit(this, p); 
       }
  
       public <R, P> R Klass.accept(ASTVisitor<R, P> visitor, P p) { 
	   return visitor.visit(this, p); 
       }
 
       public <R, P> R Interface.accept(ASTVisitor<R, P> visitor, P p) { 
	   return visitor.visit(this, p); 
       } 
   } 
}
