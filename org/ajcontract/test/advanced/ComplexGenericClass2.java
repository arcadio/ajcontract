package org.ajcontract.test.advanced;

import java.io.Serializable;
import java.util.List;

import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.ObjectInvariant;
import org.ajcontract.annotation.Requires;

@ObjectInvariant("list != null")
public class ComplexGenericClass2<E extends Serializable> {
    protected List<E> list;

    @Requires(precondition = "list != null")
    @Ensures("this.list == list")
    public ComplexGenericClass2(List<E> list) {
	this.list = list;
    }

    public List<E> getList() {
	return list;
    }

    @Requires(precondition = "list != null")
    @Ensures("this.list == list")
    public void setList(List<E> list) {
	this.list = list;
    }
}
