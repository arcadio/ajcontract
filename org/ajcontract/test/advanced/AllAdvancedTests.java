package org.ajcontract.test.advanced;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    	NestedClassTest.class, 
    	GenericClassTest.class,
	ComplexGenericClassTest.class, 
	ComplexGenericClass2Test.class,
	GenericMethodsTest.class})
public class AllAdvancedTests {
}
