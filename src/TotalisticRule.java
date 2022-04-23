import java.util.StringJoiner;

public class TotalisticRule extends Rule{
	
	private String ruleString;
	
	public TotalisticRule(int ruleNum) throws RuleNumException{
		super(ruleNum);
		if(ruleNum > 63 || ruleNum < 0) {
			throw new RuleNumException(0,63);
		}
		
		String rulBinary1 = Integer.toBinaryString(ruleNum);
		int rulBin = Integer.parseInt(rulBinary1);
		ruleString = String.format("%06d", rulBin);
	}
	
	public boolean evolve(boolean[] neighborhood) {
		
		char[] binary = ruleString.toCharArray();
		char[] reverse = new char[6];
		boolean[] test = new boolean[6];
		int countTrue = 0;
		
		for(int j = 0; j < 6; ++j) {
			reverse[j] = binary[5-j];
		}
		
		for (int i = 0; i < 6; ++i) {
			if (reverse[i] == '1') {
				test[i] = true;
			}
			else {
				test[i] = false;
			}
		}

		for (int idx = 0; idx < 5; idx++) {
			if(neighborhood[idx] == true) {
				++countTrue;
			}
		}
		return test[countTrue];
	}
	
	public boolean[] getNeighborhood(int idx, Generation gen) {
		return getNeighborhoodByRadius(idx, 2, gen);
	}
	
	public String ruleTableString(char falseSymbol, char trueSymbol) {
		String top = "5 4 3 2 1 0";
		StringJoiner js = new StringJoiner(" ");
		String stringRule = ruleString;
		stringRule = stringRule.replace('1', 'x');
		stringRule = stringRule.replace('0', 'z');
		stringRule = stringRule.replace('x', trueSymbol);
		stringRule = stringRule.replace('z', falseSymbol);
		
		
		for(int i = 0; i < 6; ++i) {
			js.add(stringRule.charAt(i)+"");
		}
		
		return top + System.lineSeparator() + js.toString();
		
	}
	
}
