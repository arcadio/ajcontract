package org.ajcontract.test.advanced;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class ComplexGenericClassTest {
    private ComplexGenericClass<List<Integer>> genericClass;

    @After
    public void tearDown() {
        genericClass = null;
    }

    @Test
    public void constructorValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        genericClass = new ComplexGenericClass<List<Integer>>(list);
        assertEquals(list, genericClass.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidPrecondition() {
        new ComplexGenericClass<List<Integer>>(null);
    }

    @Test
    public void methodValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        list.add(4);
        List<Integer> list2 = new LinkedList<Integer>();
        list2.add(5);
        genericClass = new ComplexGenericClass<List<Integer>>(list);
        genericClass.setList(list2);
        assertEquals(list2, genericClass.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodInvalidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        genericClass = new ComplexGenericClass<List<Integer>>(list);
        genericClass.setList(null);
    }
}
