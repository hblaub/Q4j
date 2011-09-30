package org.q4j.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ReadOnlyCollection<T> implements Collection<T> {
	protected Collection<T> collection;

	public ReadOnlyCollection(Iterable<T> iterable) {
		this();
		for (T item : iterable)
			collection.add(item);
	}

	public ReadOnlyCollection(Collection<T> collection) {
		this.collection = collection;
	}

	public ReadOnlyCollection(T[] array) {
		this(Arrays.asList(array));
	}

	public ReadOnlyCollection() {
		this(new LinkedList<T>());
	}

	@Override
	public boolean add(T e) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
	}
}
