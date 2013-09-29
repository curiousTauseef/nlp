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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.Text;

public class BrillTrainerUnitTest {
	
	private static final List<Rule> EMPTY_RULES = Collections.emptyList();
	
	private BrillTrainer trainer;

	@Before
	public void setUp() {
		trainer = new BrillTrainer();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testEmptyCorpus() {
		trainer.train();
	}
	
	@Test
	public void testEmptyRulePatterns() {
		Text text = BrillTaggerUnitTest.text("the/DET quick/ADJ brown/ADJ fox/N jumped/VB over/ADV the/DET lazy/ADJ dog/N");
		trainer.setProofCorpus(text);
		trainer.setBaseCorpus(text);
		
		List<Rule> rules = trainer.train();
		
		assertThat(rules, is(equalTo(EMPTY_RULES)));
	}
	
	@Test
	public void testUninstantiableRulePattern() {
		Text text = BrillTaggerUnitTest.text("the/DET quick/ADJ brown/ADJ fox/NOM jumped/VRB over/PREP the/DET lazy/ADJ dog/NOM");
		trainer.setProofCorpus(text);
		trainer.setBaseCorpus(text);
		
		trainer.addRulePattern(RulePattern.parse("FEATURE[-1] => FEATURE[0]"));
		
		List<Rule> rules = trainer.train();
		
		assertThat(rules, is(equalTo(EMPTY_RULES)));
	}
}
