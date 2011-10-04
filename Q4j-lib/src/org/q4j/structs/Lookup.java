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
package org.q4j.structs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.q4j.api.Func;
import org.q4j.api.IGrouping;
import org.q4j.api.ILookup;

public class Lookup<K, E> implements Iterable<IGrouping<K, E>>, ILookup<K, E> {
	protected IGrouping<K, E> nullGrouping;
	protected Map<K, IGrouping<K, E>> groups;

	public Lookup(Map<K, List<E>> lookup, Iterable<E> nullKeyElements) {
		groups = new HashMap<K, IGrouping<K, E>>();
		for (K slotKey : lookup.keySet())
			groups.put(slotKey,
					new Grouping<K, E>(slotKey, lookup.get(slotKey)));
		if (nullKeyElements != null)
			nullGrouping = new Grouping<K, E>(null, nullKeyElements);
	}

	public Iterable<E> get(K key) {
		if (key == null && nullGrouping != null)
			return nullGrouping;
		else if (key != null) {
			return groups.containsKey(key) ? groups.get(key) : null;
		}
		return null;
	}

	public <R> Iterable<R> applyResultSelector(
			Func.F2<K, Iterable<E>, R> selector) {
		List<R> list = new LinkedList<R>();
		if (nullGrouping != null)
			list.add(selector.e(nullGrouping.getKey(), nullGrouping));
		for (IGrouping<K, E> group : groups.values())
			list.add(selector.e(group.getKey(), group));
		return list;
	}

	public boolean contains(K key) {
		return (key != null) ? groups.containsKey(key) : nullGrouping != null;
	}

	@Override
	public int size() {
		return (nullGrouping == null) ? groups.size() : groups.size() + 1;
	}

	@Override
	public Iterator<IGrouping<K, E>> iterator() {
		return new LookupIterator<K, E>(nullGrouping, groups);
	}

	static class LookupIterator<K, E> implements Iterator<IGrouping<K, E>> {
		protected IGrouping<K, E> nullGrouping;
		protected Map<K, IGrouping<K, E>> groups;
		protected Iterator<IGrouping<K, E>> groupIterator;
		protected int index;

		public LookupIterator(IGrouping<K, E> nullGrouping,
				Map<K, IGrouping<K, E>> groups) {
			this.nullGrouping = nullGrouping;
			this.groups = groups;
			this.groupIterator = groups.values().iterator();
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < groups.size();
		}

		@Override
		public IGrouping<K, E> next() {
			if (index == 0 && nullGrouping != null) {
				index++;
				return nullGrouping;
			}
			index++;
			return groupIterator.next();
		}

		@Override
		public void remove() {
		}
	}
}
