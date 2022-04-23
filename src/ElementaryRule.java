import java.util.Arrays;
import java.util.StringJoiner;

public class ElementaryRule extends Rule {

	private String ruleString;
	private char trueSymbol = '1';

	// @author Bobby O'Brien
	private boolean[][] ruleStates = new boolean[][] { { true, true, true }, { true, true, false },
			{ true, false, true }, { true, false, false }, { false, true, true }, { false, true, false },
			{ false, false, true }, { false, false, false }, };

	public ElementaryRule(int ruleNum) throws RuleNumException{
		super(ruleNum);
		if(ruleNum > 255 || ruleNum < 0) {
			throw new RuleNumException(0,255);
		}
		
		String rulBinary1 = Integer.toBinaryString(ruleNum);
		int rulBin = Integer.parseInt(rulBinary1);
		ruleString = String.format("%08d", rulBin);
		}


	public boolean[] getNeighborhood(int idx, Generation gen) {
		return getNeighborhoodByRadius(idx, 1, gen);
	}

	public boolean evolve(boolean[] neighborhood) {

		char[] binary = ruleString.toCharArray();
		boolean[] test = new boolean[8];

		for (int i = 0; i < 8; ++i) {
			if (binary[i] == trueSymbol)
				test[i] = true;
			else
				test[i] = false;
		}

		for (int idx = 0; idx < 8; idx++) {
			if (Arrays.equals(neighborhood, ruleStates[idx])) {
				return test[idx];
			}
		}
		return false;
	}
	
	public String ruleTableString(char falseSymbol, char trueSymbol) {
		StringJoiner sj = new StringJoiner(" ");
		for(int i = 0; i < 8; ++i) {
			boolean[] abc = ruleStates[i];
			String zyx = "";
				for(int j = 0; j < abc.length; ++j) {
					if(abc[j] == true) {
						zyx = zyx + trueSymbol;
					}
					else
						zyx = zyx + falseSymbol;
				}
			sj.add(zyx);
		}
		
		StringJoiner js = new StringJoiner(" ");
		String stringRule = ruleString;
		stringRule = stringRule.replace('1', 'x');
		stringRule = stringRule.replace('0', 'z');
		stringRule = stringRule.replace('x', trueSymbol);
		stringRule = stringRule.replace('z', falseSymbol);
		
		for(int i = 0; i < 8; ++i) {
			js.add(" " + stringRule.charAt(i) + " ");
		}
		
		return sj.toString() + System.lineSeparator() + js.toString();
	}
	
}
