package org.q4j.data;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.q4j.data.xml.AttributeIterable;
import org.q4j.data.xml.NodeIterable;
import org.q4j.utils.CastUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class QXml {

	private QXml() {
	}

	public static Document load(String input) {
		return load(new StringReader(input));
	}

	public static Document load(Reader reader) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(reader));
		} catch (Exception exception) {
			return null;
		}
	}

	public static <T extends Node> Iterable<Element> ancestors(
			Iterable<T> source) {
		List<Element> results = QUtils.createList();
		for (T item : source)
			for (Element n = CastUtils.as(item.getParentNode()); n != null; n = CastUtils
					.as(n.getParentNode()))
				results.add(n);
		return results;
	}

	public static <T extends Node> Iterable<Element> ancestors(
			Iterable<T> source, QName name) {
		List<Element> results = QUtils.createList();
		for (T item : source)
			for (Element n = CastUtils.as(item.getParentNode()); n != null; n = CastUtils
					.as(n.getParentNode()))
				if (n.getNamespaceURI().equals(name.getNamespaceURI())
						|| n.getLocalName().equals(name.getLocalPart())) {
					Element elem = CastUtils.as(n);
					if (elem != null)
						results.add(elem);
				}
		return results;
	}

	public static Iterable<Element> ancestorsAndSelf(Iterable<Element> source) {
		List<Element> results = QUtils.createList();
		for (Element item : source)
			for (Element n = CastUtils.as(item); n != null; n = CastUtils.as(n
					.getParentNode()))
				results.add(n);
		return results;
	}

	public static Iterable<Element> ancestorsAndSelf(Iterable<Element> source,
			QName name) {
		List<Element> results = QUtils.createList();
		for (Element item : source)
			for (Element n = CastUtils.as(item); n != null; n = CastUtils.as(n
					.getParentNode()))
				if (n.getNamespaceURI().equals(name.getNamespaceURI())
						|| n.getLocalName().equals(name.getLocalPart())) {
					Element elem = CastUtils.as(n);
					if (elem != null)
						results.add(elem);
				}
		return results;
	}

	public static Iterable<Attr> attributes(Iterable<Element> source) {
		List<Attr> results = QUtils.createList();
		for (Element item : source)
			for (Attr attr : AttributeIterable.create(item.getAttributes()))
				results.add(attr);
		return results;
	}

	public static Iterable<Attr> attributes(Iterable<Element> source, QName name) {
		List<Attr> results = QUtils.createList();
		for (Element item : source)
			for (Attr attr : AttributeIterable.create(item.getAttributes()))
				if (attr.getNamespaceURI().equals(name.getNamespaceURI())
						|| attr.getLocalName().equals(name.getLocalPart()))
					results.add(attr);
		return results;
	}

	public static <T extends Document> Iterable<Node> descendantNodes(
			Iterable<T> source) {
		List<Node> results = QUtils.createList();
		for (Document item : source)
			for (Node n : NodeIterable.create(item.getChildNodes()))
				results.add(n);
		return results;
	}

	public static Iterable<Node> descendantNodesAndSelf(Iterable<Element> source) {
		List<Node> results = QUtils.createList();
		for (Element item : source) {
			for (Node n : NodeIterable.create(item.getChildNodes()))
				results.add(n);
		}
		return results;
	}

	public static <T extends Document> Iterable<Element> descendants(
			Iterable<T> source) {
		List<Element> results = QUtils.createList();
		for (Document item : source)
			for (Node node : NodeIterable.create(item.getChildNodes())) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		return results;
	}

	public static <T extends Document> Iterable<Element> descendants(
			Iterable<T> source, QName name) {
		List<Element> results = QUtils.createList();
		for (Document item : source)
			for (Node node : NodeIterable.create(item.getElementsByTagNameNS(
					name.getNamespaceURI(), name.getLocalPart()))) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		return results;
	}

	public static Iterable<Element> descendantsAndSelf(Iterable<Element> source) {
		List<Element> results = QUtils.createList();
		for (Element item : source) {
			for (Node node : NodeIterable.create(item.getChildNodes())) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		}
		return results;
	}

	public static Iterable<Element> descendantsAndSelf(
			Iterable<Element> source, QName name) {
		List<Element> results = QUtils.createList();
		for (Element item : source) {
			for (Node node : NodeIterable.create(item.getElementsByTagNameNS(
					name.getNamespaceURI(), name.getLocalPart()))) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		}
		return results;
	}

	public static <T extends Document> Iterable<Element> elements(
			Iterable<T> source) {
		List<Element> results = QUtils.createList();
		for (Document doc : source) {
			for (Node node : NodeIterable.create(doc.getChildNodes())) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		}
		return results;
	}

	public static <T extends Document> Iterable<Element> elements(
			Iterable<T> source, QName name) {
		List<Element> results = QUtils.createList();
		for (Document doc : source) {
			for (Node node : NodeIterable.create(doc.getElementsByTagNameNS(
					name.getNamespaceURI(), name.getLocalPart()))) {
				Element elem = CastUtils.as(node);
				if (elem != null)
					results.add(elem);
			}
		}
		return results;
	}

	private static final Comparator<Node> nodeComparer = new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o1.compareDocumentPosition(o2);
		}
	};

	public static <T extends Node> Iterable<Node> inDocumentOrder(
			Iterable<T> source) {
		List<Node> list = QUtils.createList();
		for (Node n : source)
			list.add(n);
		Collections.sort(list, nodeComparer);
		return list;
	}

	public static <T extends Document> Iterable<Node> nodes(Iterable<T> source) {
		List<Node> list = QUtils.createList();
		for (Document item : source)
			for (Node node : NodeIterable.create(item.getChildNodes()))
				list.add(node);
		return list;
	}

	public static void remove(Iterable<Attr> source, Attr... a) {
		for (Attr item : source)
			item.getParentNode().removeChild(item);
	}

	public static <T extends Node> void remove(Iterable<T> source, T... t) {
		for (Node item : source)
			item.getParentNode().removeChild(item);
	}
}
