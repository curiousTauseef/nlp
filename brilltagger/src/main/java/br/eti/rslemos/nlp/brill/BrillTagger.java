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
import java.util.Map;

import br.eti.rslemos.nlp.Sentence;
import br.eti.rslemos.nlp.Tagger;
import br.eti.rslemos.nlp.Text;
import br.eti.rslemos.nlp.Token;

public class BrillTagger implements Tagger {

	private List<Rule> rules = new ArrayList<Rule>();

	public void tag(Text text) {
		for (Rule rule : rules) {
			apply(rule, text);
		}
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	private static void apply(Rule rule, Text text) {
		for (Sentence sentence : text) {
			apply(rule, sentence);
		}
	}

	private static void apply(Rule rule, Sentence sentence) {
		for (int i = 0; i < sentence.size(); i++) {
			apply(rule, sentence, i);
		}
	}

	private static void apply(Rule rule, Sentence sentence, int i) {
		for (int j = 0; j < rule.matches.length; j++) {
			int real = Rule.stored2Real(j) + i;
			
			if (real >= 0 && real < sentence.size()) {
				if (rule.matches[j] != null && !match(rule.matches[j], sentence.get(real))) 
					return;
			}
		}
		
		sentence.get(i).putAll(rule.sets);
	}

	private static boolean match(Map<String, Object> match, Token token) {
		for (Map.Entry<String, Object> entry : match.entrySet()) {
			if (!token.containsKey(entry.getKey()) || !eq(entry.getValue(), token.get(entry.getKey())))
				return false;
		}
		
		return true;
	}

	private static boolean eq(Object o1, Object o2) {
		return o1 != null ? o1.equals(o2) : o2 == null;
	}
}
