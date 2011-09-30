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
package org.q4j.tests;

import static org.q4j.data.QIterable.count;
import static org.q4j.data.QIterable.lastOrDefault;
import static org.q4j.data.QIterable.longCount;
import static org.q4j.data.QIterable.ofType;
import static org.q4j.data.QIterable.select;
import static org.q4j.data.QIterable.toList;
import static org.q4j.data.QIterable.where;

import java.util.LinkedList;

import org.q4j.api.Func;

public class MainTest {

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("Hello");
		list.add("Nello");
		list.add("Nothing");

		Iterable<Boolean> intList = ofType(select(
				where(list, new Func.v1<String, Boolean>() {
					public Boolean e(String o) {
						return o.startsWith("X");
					}
				}), new Func.v1<String, Double>() {
					public Double e(String o) {
						return o.hashCode() * 1.5;
					}
				}));

		System.out.println(longCount(intList));
		System.out.println(count(intList));
		System.out.println(intList.getClass());
		System.out.println(toList(intList).getClass());
		System.out.println(lastOrDefault(intList));

		for (Object integer : intList) {
			System.out.println(integer + "/" + integer.getClass());
		}

		// System.out.println(QIterable.all(list,
		// APIUtils.Always(String.class)));
		/*
		 * for (String item : ) { System.out.println(item); }
		 */
	}
}
