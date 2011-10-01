package org.q4j.api;

public interface ILookup<K, E> extends Iterable<IGrouping<K, E>> {

	boolean contains(K key);

	int size();

	Iterable<E> get(K key);
}
