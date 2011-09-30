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

import org.q4j.api.IGrouping;

public class Grouping<K, T> implements IGrouping<K, T> {
	protected K key;
	protected Iterable<T> group;

	public Grouping(K key, Iterable<T> group) {
		this.group = group;
		this.key = key;
	}

	@Override
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public Iterator<T> iterator() {
		return group.iterator();
	}
}
