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
package org.q4j.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	public Lookup(Map<K, List<E>> map, List<E> nullKeyElements) {
		// TODO Auto-generated constructor stub
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
