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
package org.q4j.utils;

import java.util.Comparator;

import org.q4j.api.Func;
import org.q4j.api.Func.F1;

public class APIUtils {

	private APIUtils() {
	}

	public static <T> F1<T, Boolean> getAlwaysFunc() {
		return new Func.F1<T, Boolean>() {
			public Boolean e(T o) {
				return true;
			}
		};
	}

	public static <T> F1<T, Boolean> getAlwaysFunc(Class<T> clazz) {
		Func.F1<T, Boolean> func = getAlwaysFunc();
		return func;
	}

	public static <T> F1<T, T> getIdentityFunc() {
		return new Func.F1<T, T>() {
			public T e(T o) {
				return o;
			}
		};
	}

	public static <T> F1<T, T> getIdentityFunc(Class<T> clazz) {
		Func.F1<T, T> func = getIdentityFunc();
		return func;
	}

	public static <T> Comparator<T> getDefaultComparator() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				int h1 = o1.hashCode();
				int h2 = o2.hashCode();
				return h1 == h2 ? 0 : h1 > h2 ? 1 : -1;
			}
		};
	}
}
