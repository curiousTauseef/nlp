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

import org.junit.Test;

public class RuleUnitTest {
	@Test
	public void testAddressingFunctionBijectiveness() {
		for (int real = -150; real < 150; real++) {
			assertThat(Rule.stored2Real(Rule.real2Stored(real)), is(equalTo(real)));
		}
		
		for (int stored = 0; stored < 300; stored++) {
			assertThat(Rule.real2Stored(Rule.stored2Real(stored)), is(equalTo(stored)));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSetIndex() {
		Rule rule = new Rule();
		rule.addSet(1, "POS", "ADJ");
	}
	
	@Test
	public void testBuildRule() {
		Rule rule = new Rule();
		
		rule.addMatch(-1, "POS",  "DET");
		rule.addMatch( 0, "WORD", "quick");
		rule.addMatch( 1, "POS",  "ADJ");
		rule.addMatch( 2, "POS",  "NOM");
		
		rule.addSet(0, "POS", "ADJ");
		
		assertThat(rule.matches.length, is(equalTo(5)));
		assertThat(rule.matches[3], is(nullValue(Map.class)));
		assertThat(rule.matches[1].toString(), is(equalTo("{POS=DET}")));
		assertThat(rule.matches[0].toString(), is(equalTo("{WORD=quick}")));
		assertThat(rule.matches[2].toString(), is(equalTo("{POS=ADJ}")));
		assertThat(rule.matches[4].toString(), is(equalTo("{POS=NOM}")));
		
		assertThat(rule.sets.toString(), is(equalTo("{POS=ADJ}")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testForbidRepeatedMatch() {
		Rule rule = new Rule();
		rule.addMatch(-1, "POS", "DET");
		rule.addMatch(-1, "POS", "DET");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testForbidRepeatedSet() {
		Rule rule = new Rule();
		rule.addSet(0, "POS", "DET");
		rule.addSet(0, "POS", "DET");
	}
}
