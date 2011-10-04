/*******************************************************************************
 * Copyright (C) 2011 Harry Blauberg
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
