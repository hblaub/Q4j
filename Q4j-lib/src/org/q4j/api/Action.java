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
 * Action encapsulates methods which do not return a value, but can have
 * parameters. All parameter types are generic to allow every single Java class
 * to be used.
 */
public interface Action {

	/**
	 * Action without any parameters
	 */
	static interface A0 {
		void e();
	}

	/**
	 * Action with one parameter
	 * 
	 * @param <T1>
	 *            Type
	 */
	static interface A1<T> {
		void e(T o);
	}

	/**
	 * Action with 2 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 */
	static interface A2<T1, T2> {
		void e(T1 o1, T2 o2);
	}

	/**
	 * Action with 3 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 */
	static interface A3<T1, T2, T3> {
		void e(T1 o1, T2 o2, T3 o3);
	}

	/**
	 * Action with 4 parameters
	 * 
	 * @param <T1>
	 *            Type 1
	 * @param <T2>
	 *            Type 2
	 * @param <T3>
	 *            Type 3
	 * @param <T4>
	 *            Type 4
	 */
	static interface A4<T1, T2, T3, T4> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4);
	}

	/**
	 * Action with 5 parameters
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
	 */
	static interface A5<T1, T2, T3, T4, T5> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5);
	}

	/**
	 * Action with 6 parameters
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
	 */
	static interface A6<T1, T2, T3, T4, T5, T6> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6);
	}

	/**
	 * Action with 7 parameters
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
	 */
	static interface A7<T1, T2, T3, T4, T5, T6, T7> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7);
	}

	/**
	 * Action with 8 parameters
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
	 */
	static interface A8<T1, T2, T3, T4, T5, T6, T7, T8> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7, T8 o8);
	}
}
