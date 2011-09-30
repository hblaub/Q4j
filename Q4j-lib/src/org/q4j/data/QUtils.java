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
package org.q4j.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.q4j.api.Func;
import org.q4j.exceptions.ArgumentException;
import org.q4j.exceptions.MoreThanOneElementException;
import org.q4j.exceptions.NoElementFoundException;

class QUtils {

	static <S> boolean moveNext(Iterator<S> iterator) {
		if (iterator.hasNext()) {
			iterator.next();
			return true;
		}
		return false;
	}

	static <S> S last(Iterable<S> source, Func.v1<S, Boolean> predicate,
			boolean throwError) {
		boolean empty = true;
		S item = null;
		for (S element : source) {
			if (!predicate.e(element))
				continue;
			item = element;
			empty = false;
		}
		if (!empty)
			return item;
		if (throwError)
			throw new NoElementFoundException();
		return item;
	}

	static <S, R> Iterable<R> createSelectManyIterator(Iterable<S> source,
			Func.v2<S, Integer, Iterable<R>> selector) {
		List<R> list = createList();
		int counter = 0;
		for (S element : source) {
			for (R item : selector.e(element, counter))
				list.add(item);
			counter++;
		}
		return list;
	}

	static <R> List<R> createList() {
		return new LinkedList<R>();
	}

	static <S, R> Iterable<R> createOfTypeIterator(Iterable<S> source) {
		List<R> results = createList();
		for (S element : source) {
			R obj = CastUtils.as(element);
			if (obj == null)
				continue;
			results.add(obj);
		}
		return results;
	}

	static <S, R> Iterable<R> createSelectIterator(Iterable<S> source,
			Func.v1<S, R> selector) {
		List<R> list = createList();
		for (S element : source)
			list.add(selector.e(element));
		return list;
	}

	static <S, R> Iterable<R> createSelectIterator(Iterable<S> source,
			Func.v2<S, Integer, R> selector) {
		List<R> list = createList();
		int counter = 0;
		for (S element : source) {
			list.add(selector.e(element, counter));
			counter++;
		}
		return list;
	}

	static <S, C, R> Iterable<R> createSelectManyIterator(Iterable<S> source,
			Func.v1<S, Iterable<C>> collectionSelector,
			Func.v2<S, C, R> selector) {
		List<R> list = createList();
		for (S element : source)
			for (C collection : collectionSelector.e(element))
				list.add(selector.e(element, collection));
		return list;
	}

	static <S, R> Iterable<R> createSelectManyIterator(Iterable<S> source,
			Func.v1<S, Iterable<R>> selector) {
		List<R> list = createList();
		for (S element : source)
			for (R item : selector.e(element))
				list.add(item);
		return list;
	}

	static <S, C, R> Iterable<R> createSelectManyIterator(Iterable<S> source,
			Func.v2<S, Integer, Iterable<C>> collectionSelector,
			Func.v2<S, C, R> selector) {
		List<R> list = createList();
		int counter = 0;
		for (S element : source)
			for (C collection : collectionSelector.e(element, counter++))
				list.add(selector.e(element, collection));
		return list;
	}

	static <T1, T2, R> void check(Iterable<T1> source,
			Func.v2<T1, T2, R> predicate) {
		if (source == null || predicate == null)
			throw new ArgumentException();
	}

	static <S> void check(Iterable<S> source) {
		if (source == null)
			throw new ArgumentException();
	}

	static <S, C, R> void check(Iterable<S> source,
			Func.v1<S, Iterable<C>> func1, Func.v2<S, C, R> func2) {
		if (source == null || func1 == null || func2 == null)
			throw new ArgumentException();
	}

	static <S, R> void check(Iterable<S> source, Func.v1<S, R> predicate) {
		if (source == null || predicate == null)
			throw new ArgumentException();
	}

	static <S, C, R> void check(Iterable<S> source,
			Func.v2<S, Integer, Iterable<C>> func1, Func.v2<S, C, R> func2) {
		if (source == null || func1 == null || func2 == null)
			throw new ArgumentException();
	}

	static <S> S single(Iterable<S> source, Func.v1<S, Boolean> predicate,
			boolean throwError) {
		boolean found = false;
		S item = null;
		for (S element : source) {
			if (!predicate.e(element))
				continue;
			if (found)
				throw new MoreThanOneElementException();
			found = true;
			item = element;
		}
		if (!found && throwError)
			throw new NoSuchElementException();
		return item;
	}

	static <S> Iterable<S> createSkipIterator(Iterable<S> source, int count) {
		List<S> list = createList();
		Iterator<S> iterator = source.iterator();
		try {
			while (count-- > 0)
				if (!moveNext(iterator))
					break;
			while (iterator.hasNext())
				list.add(iterator.next());
		} finally {
		}
		return list;
	}

	static <S> Iterable<S> createSkipWhileIterator(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		List<S> list = createList();
		boolean yield = false;
		for (S element : source) {
			if (yield)
				list.add(element);
			else if (!predicate.e(element)) {
				list.add(element);
				yield = true;
			}
		}
		return list;
	}

	static <S> Iterable<S> createSkipWhileIterator(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		List<S> list = createList();
		int counter = 0;
		boolean yield = false;
		for (S element : source) {
			if (yield)
				list.add(element);
			else if (!predicate.e(element, counter)) {
				list.add(element);
				yield = true;
			}
			counter++;
		}
		return list;
	}

	static <S> Iterable<S> createTakeIterator(Iterable<S> source, int count) {
		List<S> list = createList();
		if (count <= 0)
			return list;
		int counter = 0;
		for (S element : source) {
			list.add(element);
			if (++counter == count)
				return list;
		}
		return list;
	}

	static <S> Iterable<S> createTakeWhileIterator(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		List<S> list = createList();
		for (S element : source) {
			if (!predicate.e(element))
				return list;
			list.add(element);
		}
		return list;
	}

	static <S> Iterable<S> createTakeWhileIterator(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		List<S> list = createList();
		int counter = 0;
		for (S element : source) {
			if (!predicate.e(element, counter))
				return list;
			list.add(element);
			counter++;
		}
		return list;
	}

	static <S> Iterable<S> createWhereIterator(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		List<S> list = createList();
		for (S element : source)
			if (predicate.e(element))
				list.add(element);
		return list;
	}

	static <S> Iterable<S> createWhereIterator(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		List<S> list = createList();
		int counter = 0;
		for (S element : source) {
			if (predicate.e(element, counter))
				list.add(element);
			counter++;
		}
		return list;
	}
}
