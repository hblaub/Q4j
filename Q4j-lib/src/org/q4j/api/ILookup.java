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
package org.q4j.api;

/**
 * Interface for creating enumerable collections of IGrouping
 * 
 * @param <K>
 *            Type of key
 * @param <E>
 *            Type of elements
 */
public interface ILookup<K, E> extends Iterable<IGrouping<K, E>> {

	/**
	 * Checks one key
	 * 
	 * @param key
	 *            given key
	 * @return true if key exists
	 */
	boolean contains(K key);

	/**
	 * Retrieves the size
	 * 
	 * @return value as Integer
	 */
	int size();

	/**
	 * Retrieves the associated Iterable object
	 * 
	 * @param key
	 *            given key
	 * @return found Iterable or null
	 */
	Iterable<E> get(K key);
}
