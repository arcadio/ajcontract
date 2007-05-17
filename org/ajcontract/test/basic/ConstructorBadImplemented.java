package org.ajcontract.test.basic;

import java.io.IOException;
import java.math.BigInteger;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

public class ConstructorBadImplemented {
    private static int b;

    private int a;

    @Requires(precondition = "b != 0", exception = IllegalStateException.class)
    @Ensures("a == b")
    public ConstructorBadImplemented() {
	a = -b;
    }

    @Requires(precondition = "c != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c")
    public ConstructorBadImplemented(int c) {
	a = -c;
    }

    @Requires(precondition = "c + d != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c + d")
    public ConstructorBadImplemented(int c, int d) {
	a = c + 1;
    }

    @Requires(precondition = "a + c != 0", exception = IllegalArgumentException.class)
    @Ensures("this.a == a + c")
    public ConstructorBadImplemented(short a, short c) {
	this.a = a;
    }

    @Requires(precondition = "b + c != ConstructorBadImplemented.b", exception = IllegalArgumentException.class)
    @Ensures("a == b - c")
    public ConstructorBadImplemented(byte b, byte c) {
	a = c;
    }

    @Requires(precondition = "integer != null", exception = IllegalArgumentException.class)
    @Ensures("a == integer")
    public ConstructorBadImplemented(Integer integer) {
	a = -integer;
    }

    @Requires(precondition = "bigInteger != null", exception = IllegalArgumentException.class)
    @Ensures("a == bigInteger.intValue()")
    public ConstructorBadImplemented(BigInteger bigInteger) {
	a = 0;
    }

    @Requires(precondition = "c != b")
    @Ensures("a == c")
    public ConstructorBadImplemented(byte c) {
	a = -1;
    }

    @Requires(precondition = "c != 0", exception = IllegalArgumentException.class)
    @Ensures("a == c")
    public ConstructorBadImplemented(short c) throws IOException {
	a = 0;
    }

    @Requires(precondition = "array.length > 0", exception = IllegalArgumentException.class)
    @Ensures("a == array[0]")
    public ConstructorBadImplemented(int[] array) {
	a = 0;
    }

    @Requires(precondition = "(array.length > 0) && (array[0].length > 0) && (array[0][0].length > 0)", exception = IllegalArgumentException.class)
    @Ensures("a == array[0][0][0]")
    public ConstructorBadImplemented(int[][][] array) {
	a = 0;
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
