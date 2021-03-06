import java.io.IOException;

public class ElementaryAutomaton extends Automaton{
	
	protected ElementaryAutomaton(int ruleNum, Generation initial) throws RuleNumException {
		super(ruleNum, initial); 
	}
	
	protected ElementaryAutomaton(String filename) throws IOException, RuleNumException {
		super(filename);
	}
	
	public Rule createRule(int ruleNum) throws RuleNumException {
		return new ElementaryRule(ruleNum);
	}
}
