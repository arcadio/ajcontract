package org.ajcontract.test.basic;

import static java.math.BigInteger.TEN;
import static org.ajcontract.test.basic.ConstructorBadImplemented.getB;
import static org.ajcontract.test.basic.ConstructorBadImplemented.setB;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConstructorBadImplementedTest {
    private ConstructorBadImplemented constructorBadImplemented;

    @Before
    public void setUpClass() {
	setB(1);
    }

    @After
    public void tearDown() {
	constructorBadImplemented = null;
    }

    @Test(expected = AssertionError.class)
    public void constructor0() {
	constructorBadImplemented = new ConstructorBadImplemented();
	assertEquals(getB(), constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor1() {
	int c = 1;
	constructorBadImplemented = new ConstructorBadImplemented(c);
	assertEquals(c, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor2() {
	int c = -1, d = 2;
	constructorBadImplemented = new ConstructorBadImplemented(c, d);
	assertEquals(c + d, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor3() {
	short a = 2, c = 3;
	constructorBadImplemented = new ConstructorBadImplemented(a, c);
	assertEquals(a + c, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor4() {
	byte b = 1, c = 2;
	constructorBadImplemented = new ConstructorBadImplemented(b, c);
	assertEquals(b - c, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor5() {
	Integer integer = 1;
	constructorBadImplemented = new ConstructorBadImplemented(integer);
	assertEquals(integer, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor6() {
	BigInteger bigInteger = TEN;
	constructorBadImplemented = new ConstructorBadImplemented(bigInteger);
	assertEquals(TEN.intValue(), constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor7() {
	byte c = 2;
	constructorBadImplemented = new ConstructorBadImplemented(c);
	assertEquals(2, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor8() throws IOException {
	short c = 1;
	constructorBadImplemented = new ConstructorBadImplemented(c);
	assertEquals(1, constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor9() {
	int[] array = new int[] { 1, 2 };
	constructorBadImplemented = new ConstructorBadImplemented(array);
	assertEquals(array[0], constructorBadImplemented.getA());
    }

    @Test(expected = AssertionError.class)
    public void constructor10() {
	int[][][] array = new int[][][] { { { 1 } }, { { 2 } }, { { 3 } } };
	constructorBadImplemented = new ConstructorBadImplemented(array);
	assertEquals(array[0][0][0], constructorBadImplemented.getA());
    }
}
