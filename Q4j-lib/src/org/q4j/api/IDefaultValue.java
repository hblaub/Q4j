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
 * Interface for default values of primitives and objects
 * 
 * @param <T>
 *            Type of the default value
 */
public interface IDefaultValue<T> {

	/**
	 * Checks if a given class if compatible with this default value
	 * 
	 * @param type
	 *            Java type to check
	 * @return true if given type is compatible, e.g. in case of primitives
	 */
	boolean isCompatible(Class<?> type);

	/**
	 * Gets the default value of the associated type
	 * 
	 * @return new object of type T
	 */
	T getDefault();
}
