package org.ajcontract.test;

import org.ajcontract.test.basic.AllBasicTests;
import org.ajcontract.test.advanced.AllAdvancedTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AllBasicTests.class, AllAdvancedTests.class})
public class AllTests {
}
