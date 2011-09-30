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

import static org.q4j.data.QUtils.check;
import static org.q4j.data.QUtils.check2;
import static org.q4j.data.QUtils.createCastIterator;
import static org.q4j.data.QUtils.createConcatIterator;
import static org.q4j.data.QUtils.createDistinctIterator;
import static org.q4j.data.QUtils.createExceptIterator;
import static org.q4j.data.QUtils.createGroupByIterator;
import static org.q4j.data.QUtils.createIntersectIterator;
import static org.q4j.data.QUtils.createList;
import static org.q4j.data.QUtils.createOfTypeIterator;
import static org.q4j.data.QUtils.createSelectIterator;
import static org.q4j.data.QUtils.createSelectManyIterator;
import static org.q4j.data.QUtils.createSkipIterator;
import static org.q4j.data.QUtils.createSkipWhileIterator;
import static org.q4j.data.QUtils.createTakeIterator;
import static org.q4j.data.QUtils.createTakeWhileIterator;
import static org.q4j.data.QUtils.createWhereIterator;
import static org.q4j.data.QUtils.moveNext;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.q4j.api.APIUtils;
import org.q4j.api.Func;
import org.q4j.api.Func.v1;
import org.q4j.api.IGrouping;
import org.q4j.exceptions.ArgumentException;
import org.q4j.exceptions.EmptySourceSequence;
import org.q4j.exceptions.OutOfRangeException;

public class QIterable {

	private QIterable() {
	}

	public static <S> S aggregate(Iterable<S> source, Func.v2<S, S, S> func) {
		check(source, func);
		Iterator<S> iterator = source.iterator();
		if (!iterator.hasNext())
			throw new EmptySourceSequence();
		S folded = iterator.next();
		while (iterator.hasNext())
			folded = func.e(folded, iterator.next());
		return folded;
	}

	public static <S, A> A aggregate(Iterable<S> source, A seed,
			Func.v2<A, S, A> func) {
		check2(source, func);
		A folded = seed;
		for (S element : source)
			folded = func.e(folded, element);
		return folded;
	}

	public static <S, A, R> R aggregate(Iterable<S> source, A seed,
			Func.v2<A, S, A> func, Func.v1<A, R> resultSelector) {
		check2(source, func);
		if (resultSelector == null)
			throw new ArgumentException();
		A result = seed;
		for (S e : source)
			result = func.e(result, e);
		return resultSelector.e(result);
	}

	public static <S> boolean all(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		for (S element : source)
			if (!predicate.e(element))
				return false;
		return true;
	}

	public static <S> boolean any(Iterable<S> source) {
		check(source);
		Collection<S> collection = CastUtils.as(source);
		if (collection != null)
			return !collection.isEmpty();
		return source.iterator().hasNext();
	}

	public static <S> boolean any(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		for (S element : source)
			if (predicate.e(element))
				return true;
		return false;
	}

	public static <S> Iterable<S> asIterable(Iterable<S> source) {
		return source;
	}

	public static <S, R> Iterable<R> cast(Iterable<S> source) {
		check(source);
		Iterable<R> actual = CastUtils.as(source);
		if (actual != null)
			return actual;
		return createCastIterator(source);
	}

	public static <S> Iterable<S> concat(Iterable<S> first, Iterable<S> second) {
		check(first, second);
		return createConcatIterator(first, second);
	}

	public static <S> boolean contains(Iterable<S> source, S value) {
		Collection<S> collection = CastUtils.as(source);
		if (collection != null)
			return collection.contains(value);
		return contains(source, value, null);
	}

	public static <S> boolean contains(Iterable<S> source, S value,
			Comparator<S> comparer) {
		check(source);
		if (comparer == null)
			comparer = APIUtils.DefaultComparator();
		for (S element : source)
			if (comparer.compare(element, value) == 0)
				return true;
		return false;
	}

	public static <S> int count(Iterable<S> source) {
		check(source);
		Collection<S> collection = CastUtils.as(source);
		if (collection != null)
			return collection.size();
		int counter = 0;
		Iterator<S> iterator = source.iterator();
		while (moveNext(iterator))
			counter++;
		return counter;
	}

	public static <S> int count(Iterable<S> source, Func.v1<S, Boolean> selector) {
		check(source, selector);
		int counter = 0;
		for (S element : source)
			if (selector.e(element))
				counter++;
		return counter;
	}

	public static <S> Iterable<S> distinct(Iterable<S> source) {
		return distinct(source, null);
	}

	public static <S> Iterable<S> distinct(Iterable<S> source,
			Comparator<S> comparer) {
		check(source);
		if (comparer == null)
			comparer = APIUtils.DefaultComparator();
		return createDistinctIterator(source, comparer);
	}

	public static <S> S elementAt(Iterable<S> source, int index) {
		check(source);
		if (index < 0)
			throw new OutOfRangeException();
		List<S> list = CastUtils.as(source);
		if (list != null)
			return list.get(index);
		return QUtils.elementAt(source, index, true);
	}

	public static <S> S elementAtOrDefault(Iterable<S> source, int index) {
		check(source);
		if (index < 0)
			return null;
		List<S> list = CastUtils.as(source);
		if (list != null)
			return index < list.size() ? list.get(index) : null;
		return QUtils.elementAt(source, index, false);
	}

	public static <R> Iterable<R> empty() {
		return createList();
	}

	public static <S> Iterable<S> except(Iterable<S> first, Iterable<S> second) {
		return except(first, second, null);
	}

	public static <S> Iterable<S> except(Iterable<S> first, Iterable<S> second,
			Comparator<S> comparer) {
		check(first, second);
		if (comparer == null)
			comparer = APIUtils.DefaultComparator();
		return createExceptIterator(first, second, comparer);
	}

	public static <S> S first(Iterable<S> source) {
		check(source);
		List<S> list = CastUtils.as(source);
		if (list != null) {
			if (!list.isEmpty())
				return list.get(0);
		} else {
			Iterator<S> iterator = source.iterator();
			if (iterator.hasNext())
				return iterator.next();
		}
		throw new EmptySourceSequence();
	}

	public static <S> S first(Iterable<S> source, Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.first(source, predicate, true);
	}

	public static <S> S firstOrDefault(Iterable<S> source) {
		check(source);
		v1<S, Boolean> func = APIUtils.Always();
		return QUtils.first(source, func, false);
	}

	public static <S> S firstOrDefault(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.first(source, predicate, false);
	}

	public static <S, K> Iterable<IGrouping<K, S>> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector) {
		Comparator<K> comparer = null;
		return groupBy(source, keySelector, comparer);
	}

	public static <S, K> Iterable<IGrouping<K, S>> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector, Comparator<K> comparer) {
		check(source, keySelector);
		return createGroupByIterator(source, keySelector, comparer);
	}

	public static <S, K, E> Iterable<IGrouping<K, E>> groupBy(
			Iterable<S> source, Func.v1<S, K> keySelector,
			Func.v1<S, E> elementSelector) {
		Comparator<K> comparer = null;
		return groupBy(source, keySelector, elementSelector, comparer);
	}

	public static <S, K, E> Iterable<IGrouping<K, E>> groupBy(
			Iterable<S> source, Func.v1<S, K> keySelector,
			Func.v1<S, E> elementSelector, Comparator<K> comparer) {
		check(source, keySelector, elementSelector);
		return createGroupByIterator(source, keySelector, elementSelector,
				comparer);
	}

	public static <S, K, E, R> Iterable<R> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector, Func.v1<S, E> elementSelector,
			Func.v2<K, Iterable<E>, R> resultSelector) {
		return groupBy(source, keySelector, elementSelector, resultSelector,
				null);
	}

	public static <S, K, E, R> Iterable<R> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector, Func.v1<S, E> elementSelector,
			Func.v2<K, Iterable<E>, R> resultSelector, Comparator<K> comparer) {
		check(source, keySelector, elementSelector, resultSelector);
		return createGroupByIterator(source, keySelector, elementSelector,
				resultSelector, comparer);
	}

	public static <S, K, R> Iterable<R> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector, Func.v2<K, Iterable<S>, R> resultSelector) {
		return groupBy(source, keySelector, resultSelector, null);
	}

	public static <S, K, R> Iterable<R> groupBy(Iterable<S> source,
			Func.v1<S, K> keySelector,
			Func.v2<K, Iterable<S>, R> resultSelector, Comparator<K> comparer) {
		check2(source, keySelector, resultSelector);
		return createGroupByIterator(source, keySelector, resultSelector,
				comparer);
	}

	public static <S> Iterable<S> intersect(Iterable<S> first,
			Iterable<S> second) {
		return intersect(first, second, null);
	}

	public static <S> Iterable<S> intersect(Iterable<S> first,
			Iterable<S> second, Comparator<S> comparer) {
		check(first, second);
		if (comparer == null)
			comparer = APIUtils.DefaultComparator();
		return createIntersectIterator(first, second, comparer);
	}

	public static <S> S last(Iterable<S> source) {
		check(source);
		Collection<S> collection = CastUtils.as(source);
		if (collection != null && collection.isEmpty())
			throw new EmptySourceSequence();
		List<S> list = CastUtils.as(source);
		if (list != null)
			return list.get(list.size() - 1);
		v1<S, Boolean> func = APIUtils.Always();
		return QUtils.last(source, func, true);
	}

	public static <S> S last(Iterable<S> source, Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.last(source, predicate, true);
	}

	public static <S> S lastOrDefault(Iterable<S> source) {
		check(source);
		List<S> list = CastUtils.as(source);
		if (list != null)
			return list.isEmpty() ? null : list.get(list.size() - 1);
		v1<S, Boolean> func = APIUtils.Always();
		return QUtils.last(source, func, false);
	}

	public static <S> S lastOrDefault(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.last(source, predicate, false);
	}

	public static <S> long longCount(Iterable<S> source) {
		check(source);
		long counter = 0;
		Iterator<S> iterator = source.iterator();
		while (moveNext(iterator))
			counter++;
		return counter;
	}

	public static <S> long longCount(Iterable<S> source,
			Func.v1<S, Boolean> selector) {
		check(source, selector);
		long counter = 0;
		for (S element : source)
			if (selector.e(element))
				counter++;
		return counter;
	}

	public static int max(Iterable<Integer> source, int... i) {
		check(source);
		boolean empty = true;
		int max = Integer.MIN_VALUE;
		for (int element : source) {
			max = Math.max(element, max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceSequence();
		return max;
	}

	public static long max(Iterable<Long> source, long... l) {
		check(source);
		boolean empty = true;
		long max = Long.MIN_VALUE;
		for (long element : source) {
			max = Math.max(element, max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceSequence();
		return max;
	}

	public static double max(Iterable<Double> source, double... d) {
		check(source);
		boolean empty = true;
		double max = Double.MIN_VALUE;
		for (double element : source) {
			max = Math.max(element, max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceSequence();
		return max;
	}

	public static float max(Iterable<Float> source, float... f) {
		check(source);
		boolean empty = true;
		float max = Float.MIN_VALUE;
		for (float element : source) {
			max = Math.max(element, max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceSequence();
		return max;
	}

	public static Integer max(Iterable<Integer> source, Integer... i) {
		check(source);
		boolean empty = true;
		int max = Integer.MIN_VALUE;
		for (Integer element : source) {
			if (element == null)
				continue;
			max = Math.max(element.intValue(), max);
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static Long max(Iterable<Long> source, Long... l) {
		check(source);
		boolean empty = true;
		long max = Long.MIN_VALUE;
		for (Long element : source) {
			if (element == null)
				continue;
			max = Math.max(element.longValue(), max);
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static Float max(Iterable<Float> source, Float... f) {
		check(source);
		boolean empty = true;
		float max = Float.MIN_VALUE;
		for (Float element : source) {
			if (element == null)
				continue;
			max = Math.max(element.floatValue(), max);
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static Double max(Iterable<Double> source, Double... d) {
		check(source);
		boolean empty = true;
		double max = Double.MIN_VALUE;
		for (Double element : source) {
			if (element == null)
				continue;
			max = Math.max(element.doubleValue(), max);
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static <S, R> Iterable<R> ofType(Iterable<S> source) {
		check(source);
		return createOfTypeIterator(source);
	}

	public static <S, R> Iterable<R> select(Iterable<S> source,
			Func.v1<S, R> selector) {
		check(source, selector);
		return createSelectIterator(source, selector);
	}

	public static <S, R> Iterable<R> select(Iterable<S> source,
			Func.v2<S, Integer, R> selector) {
		check(source, selector);
		return createSelectIterator(source, selector);
	}

	public static <S, R> Iterable<R> selectMany(Iterable<S> source,
			Func.v1<S, Iterable<R>> selector) {
		check(source, selector);
		return createSelectManyIterator(source, selector);
	}

	public static <S, R> Iterable<R> selectMany(Iterable<S> source,
			Func.v2<S, Integer, Iterable<R>> selector) {
		check(source, selector);
		return createSelectManyIterator(source, selector);
	}

	public static <S, C, R> Iterable<R> selectMany(Iterable<S> source,
			Func.v1<S, Iterable<C>> collectionSelector,
			Func.v2<S, C, R> selector) {
		check(source, collectionSelector, selector);
		return createSelectManyIterator(source, collectionSelector, selector);
	}

	public static <S, C, R> Iterable<R> selectMany(Iterable<S> source,
			Func.v2<S, Integer, Iterable<C>> collectionSelector,
			Func.v2<S, C, R> selector) {
		check(source, collectionSelector, selector);
		return createSelectManyIterator(source, collectionSelector, selector);
	}

	public static <S> S single(Iterable<S> source) {
		check(source);
		v1<S, Boolean> func = APIUtils.Always();
		return QUtils.single(source, func, true);
	}

	public static <S> S single(Iterable<S> source, Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.single(source, predicate, true);
	}

	public static <S> S singleOrDefault(Iterable<S> source) {
		check(source);
		v1<S, Boolean> func = APIUtils.Always();
		return QUtils.single(source, func, false);
	}

	public static <S> S singleOrDefault(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.single(source, predicate, false);
	}

	public static <S> Iterable<S> skip(Iterable<S> source, int count) {
		check(source);
		return createSkipIterator(source, count);
	}

	public static <S> Iterable<S> skipWhile(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return createSkipWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> skipWhile(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		check(source, predicate);
		return createSkipWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> take(Iterable<S> source, int count) {
		check(source);
		return createTakeIterator(source, count);
	}

	public static <S> Iterable<S> takeWhile(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return createTakeWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> takeWhile(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		check(source, predicate);
		return createTakeWhileIterator(source, predicate);
	}

	public static <S> List<S> toList(Iterable<S> source) {
		check(source);
		List<S> list = createList();
		for (S element : source) {
			list.add(element);
		}
		return list;
	}

	public static <S> Iterable<S> where(Iterable<S> source,
			Func.v1<S, Boolean> predicate) {
		check(source, predicate);
		return createWhereIterator(source, predicate);
	}

	public static <S> Iterable<S> where(Iterable<S> source,
			Func.v2<S, Integer, Boolean> predicate) {
		check(source, predicate);
		return createWhereIterator(source, predicate);
	}
}
