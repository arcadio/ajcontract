package org.ajcontract.test.basic;

import org.junit.Test;

public class ClassTest {

    @Test
    public void constructor1() {
        new Klass();
    }

    @Test(expected = AssertionError.class)
    public void constructor2() {
        new Klass(0);
    }

    @Test
    public void constructor3() {
        new Klass(2, 3);
    }

    @Test
    public void method1() {
        new Klass().getA();
    }

    @Test(expected = AssertionError.class)
    public void method2() {
        new Klass().setA(0);
    }

    @Test
    public void method3() {
        new Klass().set();
    }
}
