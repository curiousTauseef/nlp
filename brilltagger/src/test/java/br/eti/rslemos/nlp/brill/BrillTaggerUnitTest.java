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

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.DefaultSentence;
import br.eti.rslemos.nlp.DefaultText;
import br.eti.rslemos.nlp.DefaultToken;
import br.eti.rslemos.nlp.Sentence;
import br.eti.rslemos.nlp.Text;
import br.eti.rslemos.nlp.Token;

public class BrillTaggerUnitTest {
	
	private BrillTagger tagger;

	@Before
	public void setUp() {
		tagger = new BrillTagger();
	}
	
	@Test
	public void testEmptyTaggerOverEmptyText() {
		Text text = text();
		tagger.tag(text);
	}
	
	@Test
	public void testExample3RocheAndSchabes1995() {
		Text text = text("He/PPS witnessed/VBD Lennon/NP killed/VBN by/BY Chapman/NP");
		tagger.tag(text);

		verify(toStrings(text), "He/PPS witnessed/VBD Lennon/NP killed/VBN by/BY Chapman/NP");
	}
	
	private static Text text(String... sentences) {
		Text text = new DefaultText();
		
		for (String s : sentences) {
			text.add(sentence(s));
		}
		
		return text;
	}
	
	private static Sentence sentence(String s) {
		DefaultSentence sentence = new DefaultSentence();
		
		String[] tokens = s.split(" ");
		for (String t : tokens) {
			sentence.add(token(t));
		}
		
		return sentence;
	}

	private static Token token(String t) {
		DefaultToken token = new DefaultToken();
		
		String[] features = t.split("/", 2);
		token.put("WORD", features[0]);
		token.put("POS", features[1]);
		
		return token;
	}
	
	private static String[] toStrings(Text text) {
		String[] strings = new String[text.size()];
		
		for (int i = 0; i < strings.length; i++) {
			strings[i] = toString(text.get(i));
		}
		
		return strings;
	}

	private static String toString(Sentence sentence) {
		StringBuilder s = new StringBuilder();
		
		for (Token token : sentence) {
			s.append(toString(token)).append(' ');
		}
		
		if (s.length() > 1)
			s.setLength(s.length() - 1);
		
		return s.toString();
	}

	private static String toString(Token token) {
		return token.get("WORD") + "/" + token.get("POS");
	}

	private static void verify(String[] actual, String... expected) {
		assertThat(actual, is(equalTo(expected)));
	}
}
