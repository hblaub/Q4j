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

import java.util.Iterator;

import org.q4j.utils.CastUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

public class AttributeIterable implements Iterable<Attr> {
	protected final NamedNodeMap namedNodeMap;

	public AttributeIterable(NamedNodeMap namedNodeMap) {
		this.namedNodeMap = namedNodeMap;
	}

	public static AttributeIterable create(NamedNodeMap namedNodeMap) {
		return new AttributeIterable(namedNodeMap);
	}

	@Override
	public Iterator<Attr> iterator() {
		return new AttributeIterator(namedNodeMap);
	}

	static class AttributeIterator implements Iterator<Attr> {
		protected NamedNodeMap map;
		protected int index;

		public AttributeIterator(NamedNodeMap map) {
			this.map = map;
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < map.getLength();
		}

		@Override
		public Attr next() {
			return CastUtils.as(map.item(index++));
		}

		@Override
		public void remove() {
		}
	}
}
