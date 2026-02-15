package org.ajcontract.test.advanced;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.junit.After;
import org.junit.Test;

public class ComplexGenericClass2Test {
    private ComplexGenericClass2<Integer> complexGenericClass2;

    @After
    public void tearDown() {
        complexGenericClass2 = null;
    }

    @Test
    public void constructorValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        complexGenericClass2 = new ComplexGenericClass2<Integer>(list);
        assertEquals(list, complexGenericClass2.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidPrecondition() {
        new ComplexGenericClass2<Integer>(null);
    }

    @Test
    public void methodValidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        List<Integer> list2 = new Stack<Integer>();
        complexGenericClass2 = new ComplexGenericClass2<Integer>(list);
        complexGenericClass2.setList(list2);
        assertEquals(list2, complexGenericClass2.getList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodInvalidPrecondition() {
        List<Integer> list = new LinkedList<Integer>();
        complexGenericClass2 = new ComplexGenericClass2<Integer>(list);
        complexGenericClass2.setList(null);
    }
}
