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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RulePattern {

	private static final Pattern RULEPATTERN_REGEXP = Pattern.compile("^(.*) => (.*)$");
	private static final Pattern MATCHORSETCLAUSE_REGEXP = Pattern.compile("^([A-Za-z0-9]*)\\[(-?[0-9]+)\\]$");
	
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

	public static RulePattern parse(String pattern) {
		Matcher matcher = RULEPATTERN_REGEXP.matcher(pattern);
		
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("RulePattern format: [matches] => [sets]: '%s'", pattern));
		
		return parse(matcher.group(1), matcher.group(2));
	}

	private static RulePattern parse(String matches, String sets) {
		return parse(matches.split(","), sets.split(","));
	}

	private static RulePattern parse(String[] matches, String[] sets) {
		RulePattern pattern = new RulePattern();
		
		for (String match : matches) {
			parseMatchClause(pattern, match.trim());
		}
		
		for (String set : sets) {
			parseSetClause(pattern, set.trim());
		}
		
		return pattern;
	}

	private static void parseMatchClause(RulePattern pattern, String match) {
		Matcher matcher = MATCHORSETCLAUSE_REGEXP.matcher(match);
		
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("Match clause format: FEATURE '[' index ']': '%s'", pattern));
		
		String featureName = matcher.group(1);
		int index = Integer.parseInt(matcher.group(2));
		
		pattern.addMatch(index, featureName);
	}

	private static void parseSetClause(RulePattern pattern, String set) {
		Matcher matcher = MATCHORSETCLAUSE_REGEXP.matcher(set);
		
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("Set clause format: FEATURE '[' index ']': '%s'", pattern));
		
		String featureName = matcher.group(1);
		int index = Integer.parseInt(matcher.group(2));
		
		pattern.addSet(index, featureName);
	}

}
