package org.q4j.data;

import java.util.Iterator;
import java.util.LinkedList;

import org.q4j.api.IGrouping;
import org.q4j.api.ILookup;

public class Lookup<K, E> implements ILookup<K, E>, Iterable<IGrouping<K, E>> {
	protected Iterable<IGrouping<K, E>> iterable;

	public Lookup(Iterable<IGrouping<K, E>> iterable) {
		this.iterable = iterable;
	}

	public Lookup() {
		this(new LinkedList<IGrouping<K, E>>());
	}

	@Override
	public Iterator<IGrouping<K, E>> iterator() {
		return iterable.iterator();
	}

	@Override
	public boolean contains(K key) {
		for (IGrouping<K, E> grouping : iterable)
			if (grouping.getKey().equals(key))
				return true;
		return false;
	}

	@Override
	public int size() {
		Iterator<IGrouping<K, E>> iterator = iterable.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

	@Override
	public Iterable<E> get(K key) {
		for (IGrouping<K, E> grouping : iterable)
			if (grouping.getKey().equals(key))
				return grouping;
		return null;
	}
}
