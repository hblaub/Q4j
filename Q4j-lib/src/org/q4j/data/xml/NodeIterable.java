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
