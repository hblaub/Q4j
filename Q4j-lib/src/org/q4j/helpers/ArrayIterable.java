package org.q4j.helpers;

import java.util.Iterator;

public class ArrayIterable<T> implements Iterable<T> {
	protected final T[] array;

	public ArrayIterable(T[] array) {
		this.array = array;
	}

	public static <T> ArrayIterable<T> create(T[] array) {
		return new ArrayIterable<T>(array);
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<T>(array);
	}

	static class ArrayIterator<T> implements Iterator<T> {
		protected T[] array;
		protected int index;

		public ArrayIterator(T[] array) {
			this.array = array;
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < array.length;
		}

		@Override
		public T next() {
			return array[index++];
		}

		@Override
		public void remove() {
		}
	}
}
