package org.ajcontract.test.advanced;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class GenericClassTest {
    private GenericClass<List<Integer>> genericClass;

    @After
    public void tearDown() {
        genericClass = null;
    }

    @Test
    public void constructorValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        genericClass = new GenericClass<List<Integer>>(list);
        assertEquals(list, genericClass.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidPrecondition() {
        new GenericClass<List<Integer>>(null);
    }

    @Test
    public void methodValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        List<Integer> list2 = new LinkedList<Integer>();
        genericClass = new GenericClass<List<Integer>>(list);
        genericClass.setList(list2);
        assertEquals(list2, genericClass.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodInvalidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        genericClass = new GenericClass<List<Integer>>(list);
        genericClass.setList(null);
    }
}
