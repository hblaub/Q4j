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

public interface Action {

	static interface v1<T> {
		void e(T o);
	}

	static interface v2<T1, T2> {
		void e(T1 o1, T2 o2);
	}

	static interface v3<T1, T2, T3> {
		void e(T1 o1, T2 o2, T3 o3);
	}

	static interface v4<T1, T2, T3, T4> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4);
	}

	static interface v5<T1, T2, T3, T4, T5> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5);
	}

	static interface v6<T1, T2, T3, T4, T5, T6> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6);
	}

	static interface v7<T1, T2, T3, T4, T5, T6, T7> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7);
	}

	static interface v8<T1, T2, T3, T4, T5, T6, T7, T8> {
		void e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7, T8 o8);
	}
}
