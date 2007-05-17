package org.ajcontract.test.basic;

import java.io.IOException;
import java.math.BigInteger;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

public class Constructor {
    private static int b;

    private int a;

    @Requires(precondition = "b != 0", exception = IllegalStateException.class)
    @Ensures("a == b")
    public Constructor() {
	a = b;
    }

    @Requires(precondition = "c != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c")
    public Constructor(int c) {
	a = c;
    }

    @Requires(precondition = "c + d != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c + d")
    public Constructor(int c, int d) {
	a = c + d;
    }

    @Requires(precondition = "a + c != 0", exception = IllegalArgumentException.class)
    @Ensures("this.a == a + c")
    public Constructor(short a, short c) {
	this.a = a + c;
    }

    @Requires(precondition = "b + c != Constructor.b", exception = IllegalArgumentException.class)
    @Ensures("a == b - c")
    public Constructor(byte b, byte c) {
	a = b - c;
    }

    @Requires(precondition = "integer != null", exception = IllegalArgumentException.class)
    @Ensures("a == integer")
    public Constructor(Integer integer) {
	a = integer;
    }

    @Requires(precondition = "bigInteger != null", exception = IllegalArgumentException.class)
    @Ensures("a == bigInteger.intValue()")
    public Constructor(BigInteger bigInteger) {
	a = bigInteger.intValue();
    }

    @Requires(precondition = "c != b")
    @Ensures("a == c")
    public Constructor(byte c) {
	a = c;
    }

    @Requires(precondition = "c != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c")
    public Constructor(short c) throws IOException {
	a = c;
    }

    @Requires(precondition = "array.length > 0", exception = IllegalArgumentException.class)
    @Ensures("a == array[0]")
    public Constructor(int[] array) {
	a = array[0];
    }

    @Requires(precondition = "(array.length > 0) && (array[0].length > 0) && (array[0][0].length > 0)", exception = IllegalArgumentException.class)
    @Ensures("a == array[0][0][0]")
    public Constructor(int[][][] array) {
	a = array[0][0][0];
    }

    public static void setB(int value) {
	b = value;
    }

    public static int getB() {
	return b;
    }

    public int getA() {
	return a;
    }
}
