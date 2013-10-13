package br.eti.rslemos.nlp.brill;

public class RuleBuilder {
	public static RuleBuilder buildRule() {
		return new RuleBuilder();
	}

	public final Rule rule = new Rule();
	
	private RuleBuilder set(String feature, Object value) {
		rule.addSet(0, feature, value);
		
		return this;
	}
	
	private RuleBuilder match(int real, String feature, Object value) {
		rule.addMatch(real, feature, value);
		
		return this;
	}

	public RuleBuilder to(String pos) {
		return set("POS", pos);
	}
	
	public RuleBuilder from(String pos) {
		return match( 0, "POS", pos);
	}
	
	public RuleBuilder PREVTAG(String pos) {
		return match(-1, "POS", pos);
	}
	
	public RuleBuilder NEXTTAG(String pos) {
		return match( 1, "POS", pos);
	}
	
	public RuleBuilder WD(String word) {
		return match( 0, "WORD", word);
	}
}