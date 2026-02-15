package org.ajcontract.test.basic;

import java.io.IOException;
import java.math.BigInteger;
import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

public class Method {
    private int a;

    public Method(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Requires(precondition = "a != 0", exception = IllegalStateException.class)
    @Ensures("a == 0")
    public void method1() {
        a = 0;
    }

    @Requires(precondition = "a != 0", exception = IllegalStateException.class)
    @Ensures("a == 0")
    public void method1BadImplemented() {
        a = 1;
    }

    @Requires(precondition = "b >= 0", exception = IllegalArgumentException.class)
    @Ensures("a == b")
    public void method2(int b) {
        a = b;
    }

    @Requires(precondition = "b >= 0", exception = IllegalArgumentException.class)
    @Ensures("a == b")
    public void method2BadImplemented(int b) {
        a = 0;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("a == b + c")
    public void method3(int b, int c) {
        a = b + c;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("a == b + c")
    public void method3BadImplemented(int b, int c) {
        a = b;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method4(int b, int c) {
        return a = b + c;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method4BadImplemented(int b, int c) {
        return a = c;
    }

    @Requires(precondition = "this.a < a + b", exception = IllegalArgumentException.class)
    @Ensures("(this.a == a + b) && (this.a == returnval)")
    public int method5(int a, int b) {
        return this.a = a + b;
    }

    @Requires(precondition = "this.a < a + b", exception = IllegalArgumentException.class)
    @Ensures("(this.a == a + b) && (this.a == returnval)")
    public int method5BadImplemented(int a, int b) {
        this.a = a + b;
        return 0;
    }

    @Requires(precondition = "integer != null", exception = IllegalArgumentException.class)
    @Ensures("returnval == integer.intValue() + a")
    public Integer method6(Integer integer) {
        return Integer.valueOf(integer.intValue() + a);
    }

    @Requires(precondition = "integer != null", exception = IllegalArgumentException.class)
    @Ensures("returnval == integer.intValue() + a")
    public Integer method6BadImplemented(Integer integer) {
        return Integer.valueOf(integer.intValue() - a);
    }

    @Requires(precondition = "bigInteger != null", exception = IllegalArgumentException.class)
    @Ensures("returnval.equals(bigInteger.add(java.math.BigInteger.valueOf(a)))")
    public BigInteger method7(BigInteger bigInteger) {
        return bigInteger.add(BigInteger.valueOf(a));
    }

    @Requires(precondition = "bigInteger != null", exception = IllegalArgumentException.class)
    @Ensures("returnval.equals(bigInteger.add(java.math.BigInteger.valueOf(a)))")
    public BigInteger method7BadImplemented(BigInteger bigInteger) {
        return BigInteger.valueOf(a);
    }

    @Requires(precondition = "b + c >= 0")
    @Ensures("(a == b + c) && (returnval == a)")
    public int method8(int b, int c) {
        return a = b + c;
    }

    @Requires(precondition = "b + c >= 0")
    @Ensures("(a == b + c) && (returnval == a)")
    public int method8BadImplemented(int b, int c) {
        return a = b - 1;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method8(short b, short c) {
        return a = b + c;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method8BadImplemented(short b, short c) {
        return a = b * c;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method9(short b, short c) throws IOException {
        return a = b + c;
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method9Exception(short b, short c) throws IOException {
        throw new IOException();
    }

    @Requires(precondition = "b + c >= 0", exception = IllegalArgumentException.class)
    @Ensures("(a == b + c) && (returnval == a)")
    public int method9BadImplemented(short b, short c) throws IOException {
        return a = b * c;
    }

    @Requires(precondition = "array.length > 0", exception = IllegalArgumentException.class)
    @Ensures("array.length == returnval.length")
    public int[] method10(int[] array) {
        return array;
    }

    @Requires(precondition = "array.length > 0", exception = IllegalArgumentException.class)
    @Ensures("array.length == returnval.length")
    public int[] method10BadImplemented(int[] array) {
        return new int[] {};
    }

    @Requires(
            precondition = "(array.length > 0) && (array[0].length > 0) && (array[0][0].length > 0)",
            exception = IllegalArgumentException.class)
    @Ensures("(returnval.length == array.length) && (returnval[0].length == array[0].length) && (array[0][0].length =="
            + " returnval[0][0].length)")
    public int[][][] method11(int[][][] array) {
        return array;
    }

    @Requires(
            precondition = "(array.length > 0) && (array[0].length > 0) && (array[0][0].length > 0)",
            exception = IllegalArgumentException.class)
    @Ensures("(returnval.length == array.length) && (returnval[0].length == array[0].length) && (array[0][0].length =="
            + " returnval[0][0].length)")
    public int[][][] method11BadImplemented(int[][][] array) {
        return new int[][][] {{{}}};
    }

    @Requires(precondition = "a > -1", exception = IllegalArgumentException.class)
    @Ensures("returnval == a + 1")
    public static int method12(int a) {
        return a + 1;
    }

    @Requires(precondition = "a > -1", exception = IllegalArgumentException.class)
    @Ensures("returnval == a + 1")
    public static int method12BadImplemented(int a) {
        return a + 2;
    }
}
