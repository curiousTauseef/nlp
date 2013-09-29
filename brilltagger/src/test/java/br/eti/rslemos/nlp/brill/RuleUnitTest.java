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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RuleUnitTest {
	private Rule rule;

	@Before
	public void setUp() {
		rule = new Rule();
		
		rule.addMatch(-1, "POS",  "DET");
		rule.addMatch( 0, "WORD", "quick");
		rule.addMatch( 1, "POS",  "ADJ");
		rule.addMatch( 2, "POS",  "NOM");
		
		rule.addSet(0, "POS", "ADJ");
	}
	
	@Test
	public void testAddressingFunctionBijectiveness() {
		for (int real = -150; real < 150; real++) {
			assertThat(Rule.stored2Real(Rule.real2Stored(real)), is(equalTo(real)));
		}
		
		for (int stored = 0; stored < 300; stored++) {
			assertThat(Rule.real2Stored(Rule.stored2Real(stored)), is(equalTo(stored)));
		}
	}
	
	@Test
	public void testAddressingFunctionIterators() {
		//                                                   0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 
		assertThat(Rule.reals(10),   is(equalTo(new int[] { -5, -4, -3, -2, -1,  0,  1,  2,  3,  4, })));
		assertThat(Rule.storeds(10), is(equalTo(new int[] {  9,  7,  5,  3,  1,  0,  2,  4,  6,  8, })));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSetIndex() {
		rule.addSet(1, "POS", "ADJ");
	}
	
	@Test
	public void testBuildRule() {
		assertThat(rule.matches.length, is(equalTo(5)));
		assertThat(rule.matches[3], is(nullValue(Map.class)));
		assertThat(rule.matches[1].toString(), is(equalTo("{POS=DET}")));
		assertThat(rule.matches[0].toString(), is(equalTo("{WORD=quick}")));
		assertThat(rule.matches[2].toString(), is(equalTo("{POS=ADJ}")));
		assertThat(rule.matches[4].toString(), is(equalTo("{POS=NOM}")));
	}
	
	@Test
	public void testToString() {
		assertThat(rule.toString(), is(equalTo("POS[-1]='DET', WORD[0]='quick', POS[1]='ADJ', POS[2]='NOM' => POS[0]='ADJ'")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testForbidRepeatedMatch() {
		rule.addMatch(-1, "POS", "ADJ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testForbidRepeatedSet() {
		rule.addSet(0, "POS", "DET");
	}
}
