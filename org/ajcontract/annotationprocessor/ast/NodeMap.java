package org.ajcontract.annotationprocessor.ast;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;

/**
 * Map that serves as a bridge between the
 * javac compiling API and the <i>ASTNode</i>
 * hierarchy. 
 */
public abstract class NodeMap<E extends Element, N extends ASTNode<E>> {
    private Map<E, N> map;

    public NodeMap() {
	map = new HashMap<E, N>();
    }

    public N get(E element) {
	N node = map.get(element);
	if (node == null) {
	    node = create(element);
	    map.put(element, node);
	}
	return node;
    }

    public Iterable<N> values() {
	return map.values();
    }

    protected abstract N create(E element);
}
