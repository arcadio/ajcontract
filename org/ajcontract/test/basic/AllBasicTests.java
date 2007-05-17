package org.ajcontract.test.basic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    	MethodTest.class, 
    	ConstructorTest.class,
	ConstructorBadImplementedTest.class, 
	ClassTest.class})
public class AllBasicTests {
}
