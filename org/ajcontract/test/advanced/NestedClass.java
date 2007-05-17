package org.ajcontract.test.advanced;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;

public class NestedClass {
    @ObjectInvariant("integer != null")
    static class Nested {
	private Integer integer;

	@Requires(precondition = "integer != null")
	@Ensures("this.integer == integer")
	public Nested(Integer integer) {
	    this.integer = integer;
	}

	public Integer getInteger() {
	    return integer;
	}

	@Requires(precondition = "integer != null")
	@Ensures("this.integer == integer")
	public void setInteger(Integer integer) {
	    this.integer = integer;
	}
    }
}
