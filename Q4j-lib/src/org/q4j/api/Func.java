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

public interface Func {

	static interface v0<R> {
		R e();
	}

	static interface v1<T, R> {
		R e(T o);
	}

	static interface v2<T1, T2, R> {
		R e(T1 o1, T2 o2);
	}

	static interface v3<T1, T2, T3, R> {
		R e(T1 o1, T2 o2, T3 o3);
	}

	static interface v4<T1, T2, T3, T4, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4);
	}

	static interface v5<T1, T2, T3, T4, T5, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5);
	}

	static interface v6<T1, T2, T3, T4, T5, T6, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6);
	}

	static interface v7<T1, T2, T3, T4, T5, T6, T7, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7);
	}

	static interface v8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
		R e(T1 o1, T2 o2, T3 o3, T4 o4, T5 o5, T6 o6, T7 o7, T8 o8);
	}
}
