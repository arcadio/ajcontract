package org.ajcontract.test.advanced;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class NestedClassTest {
    private NestedClass.Nested nested;

    @After
    public void tearDown() {
	nested = null;
    }

    @Test
    public void constructorValidPrecondition() {
	int integer = 2;
	nested = new NestedClass.Nested(integer);
	assertEquals(integer, nested.getInteger());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidPrecondition() {
	new NestedClass.Nested(null);
    }

    @Test
    public void methodValidPrecondition() {
	int integer = 2;
	nested = new NestedClass.Nested(integer);
	nested.setInteger(++integer);
	assertEquals(integer, nested.getInteger());
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodInvalidPrecondition() {
	nested = new NestedClass.Nested(2);
	nested.setInteger(null);
    }
}
