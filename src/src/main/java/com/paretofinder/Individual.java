package com.paretofinder;

public class Individual {
	private Tree lhs;
	private Tree rhs;
	private double fitness = Double.MAX_VALUE;
	
	public Individual() {
		lhs = new Tree();
		rhs = new Tree();
		lhs.randomize();
		// rhs.randomize();
		VariableNode root = new VariableNode(Node.numVariables);
		root.variableName = "x" + (Node.numVariables - 1);
		rhs.root = root;
		recalculateFitness();
	}

	public double getValueOfLHS(int pointId) {
		return lhs.evaluate(pointId);
	}

	public double recalculateFitness() {
		double value = 0;

		for (int i = 0; i < Node.points.size(); i++) {
			double lhsValue = lhs.evaluate(i);
			double rhsValue = rhs.evaluate(i);

			if (lhsValue != Double.MAX_VALUE && rhsValue != Double.MAX_VALUE) {
				value += Math.abs(lhs.evaluate(i) - rhs.evaluate(i));
			}
			else {
				fitness = Double.MAX_VALUE;
				return fitness;
			}
		}

		if (fitness != fitness) { // fitness == NaN
			value = Double.MAX_VALUE;
		}

		fitness = value;
		return fitness;
	}

	public double getFitness() {
		if (fitness == Double.MAX_VALUE) {
			return Double.MAX_VALUE;
		}

		return fitness * size();
	}

	public int size() {
		// return lhs.size() + rhs.size();
		return lhs.size();
	}

	public Individual clone() {
		Individual returnIndividual = new Individual();
		returnIndividual.lhs = lhs.clone();
		returnIndividual.rhs = rhs.clone();
		returnIndividual.fitness = this.fitness;

		return returnIndividual;
	}

	public Node getNode(int index) {
		if (index < 0 || index > size()) {
			return null;
		}

		if (index < lhs.size()) {
			return lhs.getNode(index);
		}
		else {
			return rhs.getNode(index - lhs.size());
		}
	}

	public void setNode(int index, Node node) {
		if (index < 0 || index > size()) {
			return;
		}

		if (index < lhs.size()) {
			lhs.setNode(index, node);
		}
		else {
			rhs.setNode(index - lhs.size(), node);
		}

		recalculateFitness();
	}

	public void printTree() {
		System.out.println("\n\u001B[33mIndividual:\u001B[0m");
		System.out.println("  LHS:");
		System.out.println(lhs);
		System.out.println("  RHS:");
		System.out.println(rhs);
	}

	@Override
	public String toString() {
		String s = "";
		s += "\n\u001B[33mIndividual:\u001B[0m\n";
		s += "LHS:\n";
		s += lhs;
		s += "\nRHS:\n";
		s += rhs;

		return s;
	}
}