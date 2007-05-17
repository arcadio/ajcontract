package org.ajcontract.test.advanced;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;

@ObjectInvariant("list != null")
public class GenericClass<E> {
    private E list;

    @Requires(precondition = "list != null")
    @Ensures("this.list == list")
    public GenericClass(E list) {
	this.list = list;
    }

    @Ensures("returnval == list")
    public E getList() {
	return list;
    }

    @Requires(precondition = "list != null")
    @Ensures("this.list == list")
    public void setList(E list) {
	this.list = list;
    }
}
