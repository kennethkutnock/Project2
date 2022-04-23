import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public abstract class Automaton {

	private Rule rule;
	private ArrayList<Generation> generations;
	public char falseSymbol = '0';
	public char trueSymbol = '1';

	protected Automaton(int ruleNum, Generation initial) throws RuleNumException {
		rule = createRule(ruleNum);
		generations = new ArrayList<Generation>();
		generations.add(initial);
		trueSymbol = '1';
		falseSymbol = '0';
	}

	protected Automaton(String filename) throws IOException, RuleNumException{

		Scanner readFile = new Scanner(new File(filename));

		rule = createRule(Integer.parseInt(readFile.nextLine()));
		String getTruthSymbols = readFile.nextLine();
		falseSymbol = getTruthSymbols.charAt(0);
		trueSymbol = getTruthSymbols.charAt(2);
		getTruthSymbols = readFile.nextLine();
		generations = new ArrayList<Generation>();
		generations.add(new Generation(getTruthSymbols, trueSymbol));

		readFile.close();
	}
	
	public static Automaton createAutomaton(CellularAutomaton ca, int ruleNum, Generation initial) throws RuleNumException {
		if(ca == null)
			return null;
		else if(ca == CellularAutomaton.ECA)
			return new ElementaryAutomaton(ruleNum, initial);
		else 
			return new TotalisticAutomaton(ruleNum, initial);	
	}
	
	protected abstract Rule createRule(int ruleNum) throws RuleNumException;

	public int evolve(int numSteps) {
		if (numSteps < 1) {
			return 0;
		}

		for (int i = 0; i < numSteps; ++i) {
			generations.add(rule.evolve(getCurrentGeneration()));
		}
		return numSteps;
	}

	// Written with help from Brendan Ford
	public Generation getGeneration(int stepNum) {

		if (generations.size() < stepNum) {
			for (int i = generations.size(); i < stepNum + 1; ++i) {
				generations.add(rule.evolve(getCurrentGeneration()));
			}
		}

		return generations.get(stepNum);
	}

	public Generation getCurrentGeneration() {
		return generations.get(generations.size() - 1);
	}

	public int getRuleNum() {
		return rule.getRuleNum();
	}

	public int getTotalSteps() {
		return generations.size() - 1;
	}

	public void saveEvolution(String filename) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		bw.write(toString());
		bw.close();
	}
	
	public String ruleTableString() {
		return rule.ruleTableString(falseSymbol, trueSymbol);
	}

	public String toString() {
		StringJoiner sj = new StringJoiner(System.lineSeparator());

		for (int i = 0; i < generations.size(); ++i) {
			sj.add(getGeneration(i).getStates(falseSymbol, trueSymbol));
		}

		return sj.toString();
	}
	
	
}
