package org.ajcontract.test.basic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MethodTest {
    private Method method;

    private final int initialValue = 3;

    @Before
    public void setUp() {
	method = new Method(initialValue);
    }

    @After
    public void tearDown() {
	method = null;
    }

    @Test
    public void method1ValidPrecondition() {
	method.method1();
	assertEquals(0, method.getA());
    }

    @Test(expected = IllegalStateException.class)
    public void method1InvalidPrecondition() {
	method.setA(0);
	method.method1();
    }

    @Test(expected = AssertionError.class)
    public void method1BadImplemented() {
	method.method1BadImplemented();
    }

    @Test
    public void method2ValidPrecondition() {
	int b = 2;
	method.method2(b);
	assertEquals(b, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method2InvalidPrecondition() {
	method.method2(-1);
    }

    @Test(expected = AssertionError.class)
    public void method2BadImplemented() {
	method.method2BadImplemented(2);
    }

    @Test
    public void method3ValidPrecondition() {
	int b = -1, c = 2;
	method.method3(b, c);
	assertEquals(b + c, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method3InvalidPrecondition() {
	method.method3(-2, 1);
    }

    @Test(expected = AssertionError.class)
    public void method3BadImplemented() {
	method.method3BadImplemented(-1, 2);
    }

    @Test
    public void method4ValidPrecondition() {
	int b = -1, c = 2;
	int returnValue = method.method4(b, c);
	assertEquals(b + c, returnValue);
	assertEquals(b + c, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method4InvalidPrecondition() {
	method.method4(-1, 0);
    }

    @Test(expected = AssertionError.class)
    public void method4BadImplemented() {
	method.method4BadImplemented(-1, 2);
    }

    @Test
    public void method5ValidPrecondition() {
	int a = 2, b = 2;
	int returnValue = method.method5(a, b);
	assertEquals(a + b, returnValue);
	assertEquals(a + b, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method5InvalidPrecondition() {
	method.method5(2, 1);
    }

    @Test(expected = AssertionError.class)
    public void method5BadImplemented() {
	method.method5BadImplemented(2, 2);
    }

    @Test
    public void method6ValidPrecondition() {
	Integer integer = Integer.valueOf(2);
	Integer returnValue = method.method6(integer);
	assertEquals(initialValue + integer, returnValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void method6InvalidPrecondition() {
	method.method6(null);
    }

    @Test(expected = AssertionError.class)
    public void method6BadImplemented() {
	method.method6BadImplemented(2);
    }

    @Test
    public void method7ValidPrecondition() {
	int bigIntegerValue = 2;
	BigInteger returnValue = method.method7(BigInteger
		.valueOf(bigIntegerValue));
	assertEquals(BigInteger.valueOf(initialValue + bigIntegerValue),
		returnValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void method7InvalidPrecondition() {
	method.method7(null);
    }

    @Test(expected = AssertionError.class)
    public void method7BadImplemented() {
	method.method7BadImplemented(BigInteger.valueOf(2));
    }

    @Test
    public void method8intValidPrecondition() {
	int b = 1, c = 1;
	int returnValue = method.method8(b, c);
	assertEquals(b + c, returnValue);
	assertEquals(b + c, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method8intInvalidPrecondition() {
	method.method8(-1, 0);
    }

    @Test(expected = AssertionError.class)
    public void method8intBadImplemented() {
	method.method8BadImplemented(1, 1);
    }

    @Test
    public void method8shortValidPrecondition() {
	short b = 1, c = 0;
	int returnValue = method.method8(b, c);
	assertEquals(1, returnValue);
	assertEquals(1, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method8shortInvalidPrecondition() {
	short b = -2, c = -1;
	method.method8(b, c);
    }

    @Test(expected = AssertionError.class)
    public void method8shortBadImplemented() {
	short b = 1, c = 0;
	method.method8BadImplemented(b, c);
    }

    @Test
    public void method9ValidPrecondition() throws Exception {
	short b = 1, c = 0;
	int returnValue = method.method9(b, c);
	assertEquals(1, returnValue);
	assertEquals(1, method.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void method9InvalidPrecondition() throws IOException {
	short b = -2, c = -1;
	method.method9(b, c);
    }

    @Test(expected = IOException.class)
    public void method9Exception() throws IOException {
	short b = 1, c = 0;
	method.method9Exception(b, c);
    }

    @Test(expected = AssertionError.class)
    public void method9BadImplemented() throws IOException {
	short b = 1, c = 0;
	method.method9BadImplemented(b, c);
    }

    @Test
    public void method10ValidPrecondition() {
	int[] array = new int[] { 1, 2 };
	int[] returnval = method.method10(array);
	assertEquals(array.length, returnval.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void method10InvalidPrecondition() {
	int[] array = new int[] {};
	method.method10(array);
    }

    @Test(expected = AssertionError.class)
    public void method10BadImplemented() {
	int[] array = new int[] { 1, 2 };
	method.method10BadImplemented(array);
    }

    @Test
    public void method11ValidPrecondition() {
	int[][][] array = new int[][][] { { { 1 } } };
	int[][][] returnval = method.method11(array);
	assertEquals(array.length, returnval.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void method11InvalidPrecondition() {
	int[][][] array = new int[][][] { { {} } };
	method.method11(array);
    }

    @Test(expected = AssertionError.class)
    public void method11BadImplemented() {
	int[][][] array = new int[][][] { { { 1 } } };
	method.method11BadImplemented(array);
    }

    @Test
    public void method12ValidPrecondition() {
	int a = 0;
	assertEquals(a + 1, Method.method12(a));
    }

    @Test(expected = IllegalArgumentException.class)
    public void method12InvalidPrecondition() {
	Method.method12(-1);
    }

    @Test(expected = AssertionError.class)
    public void method12BadImplemented() {
	int a = 0;
	Method.method12BadImplemented(a);
    }
}
