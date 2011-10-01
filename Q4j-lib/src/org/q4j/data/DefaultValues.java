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

import java.math.BigInteger;

import org.q4j.api.IDefaultValue;

public class DefaultValues {

	public static final IDefaultValue<?>[] all = new IDefaultValue<?>[] {
			new DefaultByte(), new DefaultShort(), new DefaultInteger(),
			new DefaultLong(), new DefaultFloat(), new DefaultDouble(),
			new DefaultBigInteger() };

	static class DefaultByte implements IDefaultValue<Byte> {
		public boolean isCompatible(Class<?> type) {
			return type == byte.class || type == Byte.class;
		}

		public Byte getDefault() {
			return 0;
		}
	}

	static class DefaultShort implements IDefaultValue<Short> {
		public boolean isCompatible(Class<?> type) {
			return type == short.class || type == Short.class;
		}

		public Short getDefault() {
			return 0;
		}
	}

	static class DefaultInteger implements IDefaultValue<Integer> {
		public boolean isCompatible(Class<?> type) {
			return type == int.class || type == Integer.class;
		}

		public Integer getDefault() {
			return 0;
		}
	}

	static class DefaultLong implements IDefaultValue<Long> {
		public boolean isCompatible(Class<?> type) {
			return type == long.class || type == Long.class;
		}

		public Long getDefault() {
			return 0L;
		}
	}

	static class DefaultFloat implements IDefaultValue<Float> {
		public boolean isCompatible(Class<?> type) {
			return type == float.class || type == Float.class;
		}

		public Float getDefault() {
			return 0F;
		}
	}

	static class DefaultDouble implements IDefaultValue<Double> {
		public boolean isCompatible(Class<?> type) {
			return type == double.class || type == Double.class;
		}

		public Double getDefault() {
			return 0D;
		}
	}

	static class DefaultBigInteger implements IDefaultValue<BigInteger> {
		public boolean isCompatible(Class<?> type) {
			return type == BigInteger.class;
		}

		public BigInteger getDefault() {
			return BigInteger.ZERO;
		}
	}
}
