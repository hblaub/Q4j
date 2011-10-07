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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReadOnlyMap<K, V> implements Map<K, V> {
	protected final Map<K, V> map;

	public ReadOnlyMap(Map<K, V> map) {
		this.map = map;
	}

	public ReadOnlyMap() {
		this(new HashMap<K, V>());
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey(Object obj) {
		return map.containsKey(obj);
	}

	@Override
	public boolean containsValue(Object obj) {
		return map.containsValue(obj);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public V put(K key, V value) {
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> source) {
	}

	@Override
	public V remove(Object key) {
		return map.get(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}
}
