package org.ajcontract.test.basic;

import org.ajcontract.annotation.ObjectInvariant;

@ObjectInvariant("a != 0")
public class Klass {
    private int a;

    public Klass() {
	a = 1;
    }

    public Klass(int a) {
	this.a = a;
    }

    public Klass(int a, int b) {
	method1();
	method2();
	this.a += a + b;
    }

    public int getA() {
	return a;
    }

    public void setA(int a) {
	this.a = a;
    }

    public void set() {
	method1();
	method2();
    }

    private void method1() {
	a = 0;
    }
    
    private void method2() {
	a = 1;
    }
}
