package org.q4j.data.xml;

import java.util.Iterator;

import org.q4j.utils.CastUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

public class AttributeIterable implements Iterable<Attr> {
	protected NamedNodeMap namedNodeMap;

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
