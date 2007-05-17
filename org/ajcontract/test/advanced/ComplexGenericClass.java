package org.ajcontract.test.advanced;

import java.util.List;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;

@ObjectInvariant("list != null && !list.isEmpty()")
public class ComplexGenericClass<E extends List<? extends Number>> {
    protected E list; // Not private due to bug in AspectJ

    @Requires(precondition = "list != null && !list.isEmpty()")
    @Ensures("this.list == list")
    public ComplexGenericClass(E list) {
	this.list = list;
    }

    public E getList() {
	return list;
    }

    @Requires(precondition = "list != null && !list.isEmpty()")
    @Ensures("this.list == list")
    public void setList(E list) {
	this.list = list;
    }
}
