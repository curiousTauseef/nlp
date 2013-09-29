/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.brill;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Rule {
	@SuppressWarnings("unchecked") 
	Map<String, Object>[] matches = new Map[0];
	Map<String, Object> sets = new HashMap<String, Object>();
 
	public void addMatch(int real, String feature, String value) {
		int stored = real2Stored(real);
		if (stored >= matches.length) {
			@SuppressWarnings("unchecked")
			Map<String, Object>[] newmatches = new Map[stored + 1];
			System.arraycopy(matches, 0, newmatches, 0, matches.length);
			matches = newmatches;
		}
		
		if (matches[stored] == null)
			matches[stored] = new HashMap<String, Object>();
		else if (matches[stored].containsKey(feature))	
			throw new IllegalArgumentException("Feature match " + feature + "[" + real + "] already set");
		
		matches[stored].put(feature, value);
	}
	
	public void addSet(int real, String feature, Object value) {
		if (real != 0)
			throw new IllegalArgumentException("pos should be 0");
		
		if (sets.containsKey(feature))
			throw new IllegalArgumentException("Feature set " + feature + "[0] already set");
		
		sets.put(feature, value);
	}
	
	// real index can be either 0, positive or negative
	// let the positives be stored at the (2*i) th position (even)
	// the negatives will take the (-2*i - 1) th position (odd)
	
	public static int real2Stored(int i) {
		return (i << 1) ^ (i >> -1);
	}

	public static int stored2Real(int i) {
		return (i >>> 1) ^ (i << -1 >> -1);
	}

	static int[] reals(int length) {
		int[] reals = new int[length];

		for (int i = 0, j = -length / 2; i < reals.length; i++, j++) {
			reals[i] = j;
		}

		return reals;
	}

	static int[] storeds(int length) {
		int[] storeds = new int[length];

		int i = 0;
		for (int j = (length - 2) | 1; j > 0; i++, j -= 2)
			storeds[i] = j;

		for (int j = 0; j < length; i++, j += 2)
			storeds[i] = j;

		return storeds;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		int[] reals = reals(matches.length);
		int[] storeds = storeds(matches.length);

		int x = 0;
		for (int i = x; i < matches.length; i++) {
			if (matches[storeds[i]] != null)
				for (Entry<String, Object> entry : matches[storeds[i]].entrySet()) {
					result.append(toString(entry, reals[i])).append(", ");
				}
		}

		result.setLength(result.length() - 2);

		result.append(" => ");

		for (Entry<String, Object> entry : sets.entrySet()) {
			result.append(toString(entry, 0)).append(", ");
		}

		result.setLength(result.length() - 2);

		return result.toString();
	}

	private static String toString(Entry<String, Object> entry, int position) {
		return String.format("%s[%d]=%s", entry.getKey(), position, asString(entry.getValue()));
	}

	private static String asString(Object value) {
		if (value == null)
			return "";
		else if (value instanceof String)
			return "'" + value + "'";
		else
			return String.valueOf(value); // TODO: provide JSON output?
	}	
	
}
