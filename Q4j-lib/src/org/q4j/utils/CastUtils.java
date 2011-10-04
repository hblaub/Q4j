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
package org.q4j.utils;

import org.q4j.api.IDefaultValue;
import org.q4j.data.DefaultValues;

public class CastUtils {

	private CastUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T as(Object obj) {
		T result;
		try {
			result = obj == null ? null : (T) obj;
		} catch (ClassCastException cce) {
			result = null;
		}
		return result;
	}

	public static <T> T defaultValue(Class<T> type) {
		for (IDefaultValue<?> defaultValueType : DefaultValues.all) {
			if (defaultValueType.isCompatible(type))
				return as(defaultValueType.getDefault());
		}
		return null;
	}
}
