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
 * Func encapsulates methods which do return a value and can have parameters.
 * All parameter types are generic to allow every single Java class to be used.
 */
public interface Func {

	/**
	 * Function without any parameters, which returns R
	 * 
	 * @param <R>
	 *            Return type
	 */
	static interface F0<R> {
		R e();
	}

	/**
	 * Function, which returns R and has one parameter
	 * 
	 * @param <T>
	 *            Type
	 * @param <R>
	 *            Return type
	 */
	static interface F1<T, R> {
		R e(T o);
	}

	/**
	 * Function, which returns R and has 2 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <R>
	 *            Return type
	 */
	static interface F2<T1, T2, R> {
		R e(T1 o1, T2 o2);
	}

	/**
	 * Function, which returns R and has 3 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <R>
	 *            Return type
	 */
	static interface F3<T1, T2, T3, R> {
		R e(T1 o1, T2 o2, T3 o3);
	}

	/**
	 * Function, which returns R and has 4 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 * @param <R>
	 *            Return type
	 */
	static interface F4<T1, T2, T3, T4, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4);
	}

	/**
	 * Function, which returns R and has 5 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 * @param <T5>
	 *            Type 5
	 * @param <R>
	 *            Return type
	 */
	static interface F5<T1, T2, T3, T4, T5, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5);
	}

	/**
	 * Function, which returns R and has 6 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 * @param <T5>
	 *            Type 5
	 * @param <T6>
	 *            Type 6
	 * @param <R>
	 *            Return type
	 */
	static interface F6<T1, T2, T3, T4, T5, T6, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6);
	}

	/**
	 * Function, which returns R and has 7 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 * @param <T5>
	 *            Type 5
	 * @param <T6>
	 *            Type 6
	 * @param <T7>
	 *            Type 7
	 * @param <R>
	 *            Return type
	 */
	static interface F7<T1, T2, T3, T4, T5, T6, T7, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7);
	}

	/**
	 * Function, which returns R and has 8 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 * @param <T5>
	 *            Type 5
	 * @param <T6>
	 *            Type 6
	 * @param <T7>
	 *            Type 7
	 * @param <T8>
	 *            Type 8
	 * @param <R>
	 *            Return type
	 */
	static interface F8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7, T8 o8);
	}
}
