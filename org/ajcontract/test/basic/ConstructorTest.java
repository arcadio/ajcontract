package org.ajcontract.test.basic;

import static java.math.BigInteger.TEN;
import static org.ajcontract.test.basic.Constructor.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConstructorTest {

    private Constructor constructorExample;

    @Before
    public void setUpClass() {
        setB(1);
    }

    @After
    public void tearDown() {
        constructorExample = null;
    }

    @Test
    public void constructor0ValidPrecondition() {
        constructorExample = new Constructor();
        assertEquals(getB(), constructorExample.getA());
    }

    @Test(expected = IllegalStateException.class)
    public void constructor0InvalidPrecondition() {
        setB(0);
        constructorExample = new Constructor();
        assertEquals(getB(), constructorExample.getA());
    }

    @Test
    public void constructor1ValidPrecondition() {
        int c = 1;
        constructorExample = new Constructor(c);
        assertEquals(c, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor1InvalidPrecondition() {
        new Constructor(0);
    }

    @Test
    public void constructor2ValidPrecondition() {
        int c = -1, d = 2;
        constructorExample = new Constructor(c, d);
        assertEquals(c + d, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor2InvalidPrecondition() {
        constructorExample = new Constructor(1, -1);
    }

    @Test
    public void constructor3ValidPrecondition() {
        short a = 2, c = 3;
        constructorExample = new Constructor(a, c);
        assertEquals(a + c, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor3InvalidPrecondition() {
        short a = 0, c = 0;
        new Constructor(a, c);
    }

    @Test
    public void constructor4ValidPrecondition() {
        byte b = 1, c = 2;
        constructorExample = new Constructor(b, c);
        assertEquals(b - c, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor4InvalidPrecondition() {
        byte b = 0, c = 1;
        new Constructor(b, c);
    }

    @Test
    public void constructor5ValidPrecondition() {
        Integer integer = 1;
        constructorExample = new Constructor(integer);
        assertEquals(integer, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor5InvalidPrecondition() {
        Integer integer = null;
        new Constructor(integer);
    }

    @Test
    public void constructor6ValidPrecondition() {
        BigInteger bigInteger = TEN;
        constructorExample = new Constructor(bigInteger);
        assertEquals(TEN.intValue(), constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor6InvalidPrecondition() {
        BigInteger bigInteger = null;
        new Constructor(bigInteger);
    }

    @Test
    public void constructor7ValidPrecondition() {
        byte c = 2;
        constructorExample = new Constructor(c);
        assertEquals(2, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor7InvalidPrecondition() {
        byte c = 1;
        new Constructor(c);
    }

    @Test
    public void constructor8ValidPrecondition() throws IOException {
        short c = 1;
        constructorExample = new Constructor(c);
        assertEquals(1, constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor8InvalidPrecondition() throws IOException {
        short c = 0;
        new Constructor(c);
    }

    @Test
    public void constructor9ValidPrecondition() {
        int[] array = new int[] {1, 2};
        constructorExample = new Constructor(array);
        assertEquals(array[0], constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor9InvalidPrecondition() {
        int[] array = new int[] {};
        new Constructor(array);
    }

    @Test
    public void constructor10ValidPrecondition() {
        int[][][] array = new int[][][] {{{1}}, {{2}}, {{3}}};
        constructorExample = new Constructor(array);
        assertEquals(array[0][0][0], constructorExample.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor10InvalidPrecondition() {
        int[][][] array = new int[][][] {{{}}, {{}}, {{}}};
        new Constructor(array);
    }
}
