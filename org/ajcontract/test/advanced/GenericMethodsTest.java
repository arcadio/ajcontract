package org.ajcontract.test.advanced;

import static org.ajcontract.test.advanced.GenericMethods.wildcardMethod;
import static org.ajcontract.test.advanced.GenericMethods.wildcardMethod2;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class GenericMethodsTest {
    private GenericMethods genericMethods;

    @After
    public void tearDown() {
	genericMethods = null;
    }

    @Test
    public void genericConstructorValidPrecondition() {
	Double d = 2.3;
	genericMethods = new GenericMethods(d);
	assertEquals(d.intValue(), genericMethods.getA());
    }

    @Test(expected = IllegalArgumentException.class)
    public void genericConstructorInvalidPrecondition() {
	new GenericMethods(-4.5F);
    }

    @Test
    public void genericMethodValidPrecondition() {
	int number1 = 2;
	genericMethods = new GenericMethods();
	Number returnedNumber = genericMethods.genericMethod(number1, 2.3);
	assertEquals(number1, returnedNumber.intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void genericMethodInvalidPrecondition() {
	genericMethods = new GenericMethods();
	genericMethods.genericMethod(-1, 5);
    }

    @Test
    public void wildcardMethodValidPrecondition() {
	int a = 3;
	List<Integer> list = new LinkedList<Integer>();
	list.add(a);
	List<Integer> returnedList = wildcardMethod(list);
	assertEquals(a + 1, returnedList.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wildcardMethodInvalidPrecondition() {
	wildcardMethod(null);
    }

    @Test
    public void wildcardMethod2ValidPrecondition() {
	List<Boolean> list = new ArrayList<Boolean>();
	list.add(true);
	List<?> list2 = wildcardMethod2(list);
	assertEquals(null, list, list2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wildcardMethod2InvalidPrecondition() {
	wildcardMethod2(new LinkedList<Character>());
    }
}
