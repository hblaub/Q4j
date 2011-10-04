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
import static org.q4j.data.QUtils.createUnionIterator;
import static org.q4j.data.QUtils.createWhereIterator;
import static org.q4j.data.QUtils.createZipIterator;
import static org.q4j.data.QUtils.moveNext;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.q4j.api.Func;
import org.q4j.api.Func.F1;
import org.q4j.api.IGrouping;
import org.q4j.api.ILookup;
import org.q4j.exceptions.ArgumentException;
import org.q4j.exceptions.EmptySourceException;
import org.q4j.exceptions.OutOfRangeException;
import org.q4j.structs.Lookup;
import org.q4j.utils.APIUtils;
import org.q4j.utils.CastUtils;

public class QIterable {

	private QIterable() {
	}

	public static <S> S aggregate(Iterable<S> source, Func.F2<S, S, S> func) {
		check(source, func);
		Iterator<S> iterator = source.iterator();
		if (!iterator.hasNext())
			throw new EmptySourceException();
		S folded = iterator.next();
		while (iterator.hasNext())
			folded = func.e(folded, iterator.next());
		return folded;
	}

	public static <S, A> A aggregate(Iterable<S> source, A seed,
			Func.F2<A, S, A> func) {
		check2(source, func);
		A folded = seed;
		for (S element : source)
			folded = func.e(folded, element);
		return folded;
	}

	public static <S, A, R> R aggregate(Iterable<S> source, A seed,
			Func.F2<A, S, A> func, Func.F1<A, R> resultSelector) {
		check2(source, func);
		if (resultSelector == null)
			throw new ArgumentException();
		A result = seed;
		for (S e : source)
			result = func.e(result, e);
		return resultSelector.e(result);
	}

	public static <S> boolean all(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
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
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		for (S element : source)
			if (predicate.e(element))
				return true;
		return false;
	}

	public static <S> Iterable<S> asIterable(Iterable<S> source) {
		return source;
	}

	public static int average(Iterable<Integer> source, int... i) {
		check(source);
		long total = 0;
		int count = 0;
		for (int element : source) {
			total = total + element;
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return (int) (total / (double) count);
	}

	public static long average(Iterable<Long> source, long... l) {
		check(source);
		long total = 0;
		long count = 0;
		for (long element : source) {
			total += element;
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return (long) (total / (double) count);
	}

	public static double average(Iterable<Double> source, double... d) {
		check(source);
		double total = 0;
		long count = 0;
		for (double element : source) {
			total += element;
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total / count;
	}

	public static float average(Iterable<Float> source, float... f) {
		check(source);
		float total = 0;
		long count = 0;
		for (float element : source) {
			total += element;
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total / count;
	}

	public static BigInteger average(Iterable<BigInteger> source) {
		check(source);
		BigInteger total = BigInteger.ZERO;
		long count = 0;
		for (BigInteger element : source) {
			total.add(element);
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total.divide(BigInteger.valueOf(count));
	}

	public static Integer average(Iterable<Integer> source, Integer... i) {
		check(source);
		long total = 0;
		long counter = 0;
		for (Integer element : source) {
			if (element == null)
				continue;
			total = total + element.intValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return (int) (total / (double) counter);
	}

	public static Long average(Iterable<Long> source, Long... l) {
		check(source);
		long total = 0;
		long counter = 0;
		for (Long element : source) {
			if (element == null)
				continue;
			total = total + element.longValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return (long) (total / (double) counter);
	}

	public static Double average(Iterable<Double> source, Double... d) {
		check(source);
		double total = 0;
		long counter = 0;
		for (Double element : source) {
			if (element == null)
				continue;
			total = total + element.doubleValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return new Double(total / counter);
	}

	public static Float average(Iterable<Float> source, Float... f) {
		check(source);
		float total = 0;
		long counter = 0;
		for (Float element : source) {
			if (element == null)
				continue;
			total = total + element.floatValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return new Float(total / counter);
	}

	public static <S> int average(Iterable<S> source,
			Func.F1<S, Integer> selector, int... i) {
		check(source, selector);
		long total = 0;
		long count = 0;
		for (S element : source) {
			total += selector.e(element);
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return (int) (total / (double) count);
	}

	public static <S> Integer average(Iterable<S> source,
			Func.F1<S, Integer> selector, Integer... i) {
		check(source, selector);
		long total = 0;
		long counter = 0;
		for (S element : source) {
			Integer value = selector.e(element);
			if (value == null)
				continue;
			total = total + value.intValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return (int) (total / (double) counter);
	}

	public static <S> long average(Iterable<S> source,
			Func.F1<S, Long> selector, long... l) {
		check(source, selector);
		long total = 0;
		long count = 0;
		for (S element : source) {
			total = total + selector.e(element);
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return (long) (total / (double) count);
	}

	public static <S> Long average(Iterable<S> source,
			Func.F1<S, Long> selector, Long... l) {
		check(source, selector);
		long total = 0;
		long counter = 0;
		for (S element : source) {
			Long value = selector.e(element);
			if (value == null)
				continue;
			total = total + value.longValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return (long) (total / (double) counter);
	}

	public static <S> double average(Iterable<S> source,
			Func.F1<S, Double> selector, double... d) {
		check(source, selector);
		double total = 0;
		long count = 0;
		for (S element : source) {
			total += selector.e(element);
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total / count;
	}

	public static <S> Double average(Iterable<S> source,
			Func.F1<S, Double> selector, Double... d) {
		check(source, selector);
		double total = 0;
		long counter = 0;
		for (S element : source) {
			Double value = selector.e(element);
			if (value == null)
				continue;
			total = total + value.doubleValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return new Double(total / counter);
	}

	public static <S> float average(Iterable<S> source,
			Func.F1<S, Float> selector, float... f) {
		check(source, selector);
		float total = 0;
		long count = 0;
		for (S element : source) {
			total += selector.e(element);
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total / count;
	}

	public static <S> Float average(Iterable<S> source,
			Func.F1<S, Float> selector, Float... f) {
		check(source, selector);
		float total = 0;
		long counter = 0;
		for (S element : source) {
			Float value = selector.e(element);
			if (value == null)
				continue;
			total = total + value.floatValue();
			counter++;
		}
		if (counter == 0)
			return null;
		return new Float(total / counter);
	}

	public static <S> BigInteger average(Iterable<S> source,
			Func.F1<S, BigInteger> selector, BigInteger... b) {
		check(source, selector);
		BigInteger total = BigInteger.ZERO;
		long count = 0;
		for (S element : source) {
			total.add(selector.e(element));
			count++;
		}
		if (count == 0)
			throw new EmptySourceException();
		return total.divide(BigInteger.valueOf(count));
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
			comparer = APIUtils.getDefaultComparator();
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

	public static <S> int count(Iterable<S> source, Func.F1<S, Boolean> selector) {
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
			comparer = APIUtils.getDefaultComparator();
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
			comparer = APIUtils.getDefaultComparator();
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
		throw new EmptySourceException();
	}

	public static <S> S first(Iterable<S> source, Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.first(source, predicate, true);
	}

	public static <S> S firstOrDefault(Iterable<S> source) {
		check(source);
		F1<S, Boolean> func = APIUtils.getAlwaysFunc();
		return QUtils.first(source, func, false);
	}

	public static <S> S firstOrDefault(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.first(source, predicate, false);
	}

	public static <S, K> Iterable<IGrouping<K, S>> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector) {
		Comparator<K> comparer = null;
		return groupBy(source, keySelector, comparer);
	}

	public static <S, K> Iterable<IGrouping<K, S>> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector, Comparator<K> comparer) {
		check(source, keySelector);
		return createGroupByIterator(source, keySelector, comparer);
	}

	public static <S, K, E> Iterable<IGrouping<K, E>> groupBy(
			Iterable<S> source, Func.F1<S, K> keySelector,
			Func.F1<S, E> elementSelector) {
		Comparator<K> comparer = null;
		return groupBy(source, keySelector, elementSelector, comparer);
	}

	public static <S, K, E> Iterable<IGrouping<K, E>> groupBy(
			Iterable<S> source, Func.F1<S, K> keySelector,
			Func.F1<S, E> elementSelector, Comparator<K> comparer) {
		check(source, keySelector, elementSelector);
		return createGroupByIterator(source, keySelector, elementSelector,
				comparer);
	}

	public static <S, K, E, R> Iterable<R> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector,
			Func.F2<K, Iterable<E>, R> resultSelector) {
		return groupBy(source, keySelector, elementSelector, resultSelector,
				null);
	}

	public static <S, K, E, R> Iterable<R> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector,
			Func.F2<K, Iterable<E>, R> resultSelector, Comparator<K> comparer) {
		check(source, keySelector, elementSelector, resultSelector);
		return createGroupByIterator(source, keySelector, elementSelector,
				resultSelector, comparer);
	}

	public static <S, K, R> Iterable<R> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F2<K, Iterable<S>, R> resultSelector) {
		return groupBy(source, keySelector, resultSelector, null);
	}

	public static <S, K, R> Iterable<R> groupBy(Iterable<S> source,
			Func.F1<S, K> keySelector,
			Func.F2<K, Iterable<S>, R> resultSelector, Comparator<K> comparer) {
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
			comparer = APIUtils.getDefaultComparator();
		return createIntersectIterator(first, second, comparer);
	}

	public static <S> S last(Iterable<S> source) {
		check(source);
		Collection<S> collection = CastUtils.as(source);
		if (collection != null && collection.isEmpty())
			throw new EmptySourceException();
		List<S> list = CastUtils.as(source);
		if (list != null)
			return list.get(list.size() - 1);
		F1<S, Boolean> func = APIUtils.getAlwaysFunc();
		return QUtils.last(source, func, true);
	}

	public static <S> S last(Iterable<S> source, Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.last(source, predicate, true);
	}

	public static <S> S lastOrDefault(Iterable<S> source) {
		check(source);
		List<S> list = CastUtils.as(source);
		if (list != null)
			return list.isEmpty() ? null : list.get(list.size() - 1);
		F1<S, Boolean> func = APIUtils.getAlwaysFunc();
		return QUtils.last(source, func, false);
	}

	public static <S> S lastOrDefault(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
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
			Func.F1<S, Boolean> selector) {
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
			throw new EmptySourceException();
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
			throw new EmptySourceException();
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
			throw new EmptySourceException();
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
			throw new EmptySourceException();
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

	public static <S> S max(Iterable<S> source) {
		check(source);
		Comparator<S> comparer = APIUtils.getDefaultComparator();
		S max = null;
		for (S element : source) {
			if (element == null)
				continue;
			if (max == null || comparer.compare(element, max) > 0)
				max = element;
		}
		return max;
	}

	public static <S> int max(Iterable<S> source, Func.F1<S, Integer> selector,
			int... i) {
		check(source, selector);
		boolean empty = true;
		int max = Integer.MIN_VALUE;
		for (S element : source) {
			max = Math.max(selector.e(element), max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return max;
	}

	public static <S> long max(Iterable<S> source, Func.F1<S, Long> selector,
			long... l) {
		check(source, selector);
		boolean empty = true;
		long max = Long.MIN_VALUE;
		for (S element : source) {
			max = Math.max(selector.e(element), max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return max;
	}

	public static <S> double max(Iterable<S> source,
			Func.F1<S, Double> selector, double... d) {
		check(source, selector);
		boolean empty = true;
		double max = Double.MIN_VALUE;
		for (S element : source) {
			max = Math.max(selector.e(element), max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return max;
	}

	public static <S> float max(Iterable<S> source, Func.F1<S, Float> selector,
			float... f) {
		check(source, selector);
		boolean empty = true;
		float max = Float.MIN_VALUE;
		for (S element : source) {
			max = Math.max(selector.e(element), max);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return max;
	}

	public static <S> BigInteger max(Iterable<S> source,
			Func.F1<S, BigInteger> selector) {
		check(source, selector);
		boolean empty = true;
		BigInteger max = BigInteger.ZERO;
		for (S element : source) {
			max = max.max(selector.e(element));
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return max;
	}

	public static <S> Integer max(Iterable<S> source,
			Func.F1<S, Integer> selector, Integer... i) {
		check(source, selector);
		boolean empty = true;
		Integer max = null;
		for (S element : source) {
			Integer item = selector.e(element);
			if (max == null)
				max = item;
			else if (item > max)
				max = item;
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static <S> Long max(Iterable<S> source, Func.F1<S, Long> selector,
			Long... l) {
		check(source, selector);
		boolean empty = true;
		Long max = null;
		for (S element : source) {
			Long item = selector.e(element);
			if (max == null)
				max = item;
			else if (item > max)
				max = item;
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static <S> Double max(Iterable<S> source,
			Func.F1<S, Double> selector, Double... d) {
		check(source, selector);
		boolean empty = true;
		Double max = null;
		for (S element : source) {
			Double item = selector.e(element);
			if (max == null)
				max = item;
			else if (item > max)
				max = item;
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static <S> Float max(Iterable<S> source, Func.F1<S, Float> selector,
			Float... f) {
		check(source, selector);
		boolean empty = true;
		Float max = null;
		for (S element : source) {
			Float item = selector.e(element);
			if (max == null)
				max = item;
			else if (item > max)
				max = item;
			empty = false;
		}
		if (empty)
			return null;
		return max;
	}

	public static <S, R> R max(Iterable<S> source, Func.F1<S, R> selector,
			Object... o) {
		check(source, selector);
		return max(select(source, selector));
	}

	public static int min(Iterable<Integer> source, int... i) {
		check(source);
		boolean empty = true;
		int min = Integer.MAX_VALUE;
		for (int element : source) {
			min = Math.min(element, min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static long min(Iterable<Long> source, long... l) {
		check(source);
		boolean empty = true;
		long min = Long.MAX_VALUE;
		for (long element : source) {
			min = Math.min(element, min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static double min(Iterable<Double> source, double... d) {
		check(source);
		boolean empty = true;
		double min = Double.MAX_VALUE;
		for (double element : source) {
			min = Math.min(element, min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static float min(Iterable<Float> source, float... f) {
		check(source);
		boolean empty = true;
		float min = Float.MAX_VALUE;
		for (float element : source) {
			min = Math.min(element, min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static BigInteger min(Iterable<BigInteger> source, BigInteger... b) {
		check(source);
		boolean empty = true;
		BigInteger min = BigInteger.valueOf(Long.MAX_VALUE);
		for (BigInteger element : source) {
			min = min.min(element);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static Integer min(Iterable<Integer> source, Integer... i) {
		check(source);
		boolean empty = true;
		int min = Integer.MAX_VALUE;
		for (Integer element : source) {
			if (element == null)
				continue;
			min = Math.min(element.intValue(), min);
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static Long min(Iterable<Long> source, Long... l) {
		check(source);
		boolean empty = true;
		long min = Long.MAX_VALUE;
		for (Long element : source) {
			if (element == null)
				continue;
			min = Math.min(element.longValue(), min);
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static Double min(Iterable<Double> source, Double... d) {
		check(source);
		boolean empty = true;
		double min = Double.MAX_VALUE;
		for (Double element : source) {
			if (element == null)
				continue;
			min = Math.min(element.doubleValue(), min);
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static Float min(Iterable<Float> source, Float... f) {
		check(source);
		boolean empty = true;
		float min = Float.MAX_VALUE;
		for (Float element : source) {
			if (element == null)
				continue;
			min = Math.min(element.floatValue(), min);
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static <S> S min(Iterable<S> source) {
		check(source);
		Comparator<S> comparer = APIUtils.getDefaultComparator();
		S min = null;
		for (S element : source) {
			if (element == null)
				continue;
			if (min == null || comparer.compare(element, min) < 0)
				min = element;
		}
		return min;
	}

	public static <S> int min(Iterable<S> source, Func.F1<S, Integer> selector,
			int... i) {
		check(source, selector);
		boolean empty = true;
		int min = Integer.MAX_VALUE;
		for (S element : source) {
			min = Math.min(selector.e(element), min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static <S> long min(Iterable<S> source, Func.F1<S, Long> selector,
			long... l) {
		check(source, selector);
		boolean empty = true;
		long min = Long.MAX_VALUE;
		for (S element : source) {
			min = Math.min(selector.e(element), min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static <S> double min(Iterable<S> source,
			Func.F1<S, Double> selector, double... d) {
		check(source, selector);
		boolean empty = true;
		double min = Double.MAX_VALUE;
		for (S element : source) {
			min = Math.min(selector.e(element), min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static <S> float min(Iterable<S> source, Func.F1<S, Float> selector,
			float... f) {
		check(source, selector);
		boolean empty = true;
		float min = Float.MAX_VALUE;
		for (S element : source) {
			min = Math.min(selector.e(element), min);
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static <S> BigInteger min(Iterable<S> source,
			Func.F1<S, BigInteger> selector, BigInteger... b) {
		check(source, selector);
		boolean empty = true;
		BigInteger min = BigInteger.valueOf(Long.MAX_VALUE);
		for (S element : source) {
			min = min.min(selector.e(element));
			empty = false;
		}
		if (empty)
			throw new EmptySourceException();
		return min;
	}

	public static <S> Integer min(Iterable<S> source,
			Func.F1<S, Integer> selector, Integer... i) {
		check(source, selector);
		boolean empty = true;
		Integer min = null;
		for (S element : source) {
			Integer item = selector.e(element);
			if (min == null)
				min = item;
			else if (item < min)
				min = item;
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static <S> Long min(Iterable<S> source, Func.F1<S, Long> selector,
			Long... l) {
		check(source, selector);
		boolean empty = true;
		Long min = null;
		for (S element : source) {
			Long item = selector.e(element);
			if (min == null)
				min = item;
			else if (item < min)
				min = item;
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static <S> Float min(Iterable<S> source, Func.F1<S, Float> selector,
			Float... f) {
		check(source, selector);
		boolean empty = true;
		Float min = null;
		for (S element : source) {
			Float item = selector.e(element);
			if (min == null)
				min = item;
			else if (item < min)
				min = item;
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static <S> Double min(Iterable<S> source,
			Func.F1<S, Double> selector, Double... d) {
		check(source, selector);
		boolean empty = true;
		Double min = null;
		for (S element : source) {
			Double item = selector.e(element);
			if (min == null)
				min = item;
			else if (item < min)
				min = item;
			empty = false;
		}
		if (empty)
			return null;
		return min;
	}

	public static <S, R> R min(Iterable<S> source, Func.F1<S, R> selector) {
		check(source, selector);
		return min(select(source, selector));
	}

	public static <S, R> Iterable<R> ofType(Iterable<S> source) {
		check(source);
		return createOfTypeIterator(source);
	}

	public static <S, R> Iterable<R> select(Iterable<S> source,
			Func.F1<S, R> selector) {
		check(source, selector);
		return createSelectIterator(source, selector);
	}

	public static <S, R> Iterable<R> select(Iterable<S> source,
			Func.F2<S, Integer, R> selector) {
		check(source, selector);
		return createSelectIterator(source, selector);
	}

	public static <S, R> Iterable<R> selectMany(Iterable<S> source,
			Func.F1<S, Iterable<R>> selector) {
		check(source, selector);
		return createSelectManyIterator(source, selector);
	}

	public static <S, R> Iterable<R> selectMany(Iterable<S> source,
			Func.F2<S, Integer, Iterable<R>> selector) {
		check(source, selector);
		return createSelectManyIterator(source, selector);
	}

	public static <S, C, R> Iterable<R> selectMany(Iterable<S> source,
			Func.F1<S, Iterable<C>> collectionSelector,
			Func.F2<S, C, R> selector) {
		check(source, collectionSelector, selector);
		return createSelectManyIterator(source, collectionSelector, selector);
	}

	public static <S, C, R> Iterable<R> selectMany(Iterable<S> source,
			Func.F2<S, Integer, Iterable<C>> collectionSelector,
			Func.F2<S, C, R> selector) {
		check(source, collectionSelector, selector);
		return createSelectManyIterator(source, collectionSelector, selector);
	}

	public static <S> boolean sequenceEqual(Iterable<S> first,
			Iterable<S> second) {
		return sequenceEqual(first, second, null);
	}

	public static <S> boolean sequenceEqual(Iterable<S> first,
			Iterable<S> second, Comparator<S> comparer) {
		check(first, second);
		if (comparer == null)
			comparer = APIUtils.getDefaultComparator();
		Iterator<S> firstIterator = first.iterator();
		Iterator<S> secondIterator = second.iterator();
		while (firstIterator.hasNext()) {
			if (!secondIterator.hasNext())
				return false;
			if (comparer.compare(firstIterator.next(), secondIterator.next()) != 0)
				return false;
		}
		return !secondIterator.hasNext();
	}

	public static <S> S single(Iterable<S> source) {
		check(source);
		F1<S, Boolean> func = APIUtils.getAlwaysFunc();
		return QUtils.single(source, func, true);
	}

	public static <S> S single(Iterable<S> source, Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.single(source, predicate, true);
	}

	public static <S> S singleOrDefault(Iterable<S> source) {
		check(source);
		F1<S, Boolean> func = APIUtils.getAlwaysFunc();
		return QUtils.single(source, func, false);
	}

	public static <S> S singleOrDefault(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return QUtils.single(source, predicate, false);
	}

	public static <S> Iterable<S> skip(Iterable<S> source, int count) {
		check(source);
		return createSkipIterator(source, count);
	}

	public static <S> Iterable<S> skipWhile(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return createSkipWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> skipWhile(Iterable<S> source,
			Func.F2<S, Integer, Boolean> predicate) {
		check(source, predicate);
		return createSkipWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> take(Iterable<S> source, int count) {
		check(source);
		return createTakeIterator(source, count);
	}

	public static <S> Iterable<S> takeWhile(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return createTakeWhileIterator(source, predicate);
	}

	public static <S> Iterable<S> takeWhile(Iterable<S> source,
			Func.F2<S, Integer, Boolean> predicate) {
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

	public static <S, K> ILookup<K, S> toLookup(Iterable<S> source,
			Func.F1<S, K> keySelector) {
		F1<S, S> func = APIUtils.getIdentityFunc();
		return toLookup(source, keySelector, func, null);
	}

	public static <S, K> ILookup<K, S> toLookup(Iterable<S> source,
			Func.F1<S, K> keySelector, Comparator<K> comparer) {
		F1<S, S> func = APIUtils.getIdentityFunc();
		return toLookup(source, keySelector, func, comparer);
	}

	public static <S, K, E> ILookup<K, E> toLookup(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector) {
		return toLookup(source, keySelector, elementSelector, null);
	}

	public static <S, K, E> ILookup<K, E> toLookup(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector,
			Comparator<K> comparer) {
		check(source, keySelector, elementSelector);
		List<E> nullKeyElements = null;
		Map<K, List<E>> map = new HashMap<K, List<E>>();
		for (S element : source) {
			K key = keySelector.e(element);
			List<E> list = null;
			if (key == null) {
				if (nullKeyElements == null)
					nullKeyElements = createList();
				list = nullKeyElements;
			} else if (!map.containsKey(key)) {
				list = createList();
				map.put(key, list);
			}
			list.add(elementSelector.e(element));
		}
		return new Lookup<K, E>(map, nullKeyElements);
	}

	public static <S, K, E> Map<K, E> toMap(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector) {
		return toMap(source, keySelector, elementSelector, null);
	}

	public static <S, K, E> Map<K, E> toMap(Iterable<S> source,
			Func.F1<S, K> keySelector, Func.F1<S, E> elementSelector,
			Comparator<K> comparer) {
		check(source, keySelector, elementSelector);
		if (comparer == null)
			comparer = APIUtils.getDefaultComparator();
		Map<K, E> dict = new HashMap<K, E>();
		for (S e : source)
			dict.put(keySelector.e(e), elementSelector.e(e));
		return dict;
	}

	public static <S, K> Map<K, S> toMap(Iterable<S> source,
			Func.F1<S, K> keySelector) {
		Comparator<K> comparer = null;
		return toMap(source, keySelector, comparer);
	}

	public static <S, K> Map<K, S> toMap(Iterable<S> source,
			Func.F1<S, K> keySelector, Comparator<K> comparer) {
		F1<S, S> func = APIUtils.getIdentityFunc();
		return toMap(source, keySelector, func, comparer);
	}

	public static <S> Iterable<S> union(Iterable<S> first, Iterable<S> second) {
		check(first, second);
		return union(first, second, null);
	}

	public static <S> Iterable<S> union(Iterable<S> first, Iterable<S> second,
			Comparator<S> comparer) {
		check(first, second);
		if (comparer == null)
			comparer = APIUtils.getDefaultComparator();
		return createUnionIterator(first, second, comparer);
	}

	public static <S> Iterable<S> where(Iterable<S> source,
			Func.F1<S, Boolean> predicate) {
		check(source, predicate);
		return createWhereIterator(source, predicate);
	}

	public static <S> Iterable<S> where(Iterable<S> source,
			Func.F2<S, Integer, Boolean> predicate) {
		check(source, predicate);
		return createWhereIterator(source, predicate);
	}

	public static <F, S, R> Iterable<R> zip(Iterable<F> first,
			Iterable<S> second, Func.F2<F, S, R> resultSelector) {
		check(first, second);
		if (resultSelector == null)
			throw new ArgumentException();
		return createZipIterator(first, second, resultSelector);
	}
}
