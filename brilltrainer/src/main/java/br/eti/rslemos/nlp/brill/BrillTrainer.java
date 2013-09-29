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

import br.eti.rslemos.nlp.Text;

public class BrillTrainer {

	private Text baseCorpus;
	private Text proofCorpus;

	public List<Rule> train() {
		if (baseCorpus == null || proofCorpus == null)
			throw new IllegalStateException();
		
		return new ArrayList<Rule>();
	}

	public void setBaseCorpus(Text baseCorpus) {
		this.baseCorpus = baseCorpus;
	}

	public void setProofCorpus(Text proofCorpus) {
		this.proofCorpus = proofCorpus;
	}

	public void addRulePattern(RulePattern rulePattern) {
	}

}
