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

import java.util.ArrayList;
import java.util.List;

public class RulePattern {

	@SuppressWarnings("unchecked")
	List<String>[] matches = new List[0];
	List<String> sets = new ArrayList<String>();

	public void addMatch(int real, String feature) {
		int stored = Rule.real2Stored(real);
		if (!(matches.length > stored)) {
			@SuppressWarnings("unchecked")
			ArrayList<String>[] newmatches = new ArrayList[stored + 1];
			System.arraycopy(matches, 0, newmatches, 0, matches.length);
			matches = newmatches;
		}

		if (matches[stored] == null)
			matches[stored] = new ArrayList<String>(2);
		else if (matches[stored].contains(feature))
			throw new IllegalArgumentException(String.format("Feature match %s[%d] already set", feature, real));

		matches[stored].add(feature);
	}

	public void addSet(int real, String feature) {
		if (real != 0)
			throw new IllegalArgumentException();

		if (sets.contains(feature))
			throw new IllegalArgumentException(String.format("Feature set %s[0] already set", feature));

		sets.add(feature);
	}
}
