public abstract class Rule {

	private int ruleInt;

	protected Rule(int ruleNum) {
		ruleInt = ruleNum;
	}

	public int getRuleNum() {
		return ruleInt;
	}

	public abstract boolean[] getNeighborhood(int idx, Generation gen);

	public abstract boolean evolve(boolean[] neighborhood);

	public abstract String ruleTableString(char falseSymbol, char trueSymbol);

	public Generation evolve(Generation gen) {
		boolean[] neighborhood;
		boolean[] end = new boolean[gen.getStates().length];
		for (int i = 0; i < gen.getStates().length; ++i) {
			neighborhood = getNeighborhood(i, gen);
			end[i] = evolve(neighborhood);
		}
		return new Generation(end);
	}

	// Written with help from Aaron Coker
	public static boolean[] getNeighborhoodByRadius(int idx, int radius, Generation gen) {

		boolean[] neighborhood = new boolean[(radius * 2) + 1];

		for (int i = idx - radius, j = 0; i < idx + radius + 1; ++i, ++j) {
			neighborhood[j] = gen.getState(Math.floorMod(i, gen.size()));
		}

		return neighborhood;
	}

}
