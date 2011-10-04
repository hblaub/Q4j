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
package org.q4j.data.xml;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeIterable implements Iterable<Node> {
	protected NodeList nodeList;

	public NodeIterable(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public static NodeIterable create(NodeList nodeList) {
		return new NodeIterable(nodeList);
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeIterator(nodeList);
	}

	static class NodeIterator implements Iterator<Node> {
		protected NodeList nodeList;
		protected int index;

		public NodeIterator(NodeList nodeList) {
			this.nodeList = nodeList;
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < nodeList.getLength();
		}

		@Override
		public Node next() {
			return nodeList.item(index++);
		}

		@Override
		public void remove() {
		}
	}
}
