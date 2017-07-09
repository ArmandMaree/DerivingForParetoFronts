package com.paretofinder;

import java.util.Random;

public class VariableNode extends Node {
	private int numVariables;
	protected String variableName;
	private Random rand = new Random();

	public VariableNode(int numVariables) {
		this.numVariables = numVariables;
	}

	public void randomize() {
		// variableName = "x" + rand.nextInt(numVariables);
		variableName = "x" + rand.nextInt(numVariables - 1);
	}

	public double getValue(int pointIndex) {
		return Node.getVariableValue(pointIndex, variableName);
	}

	public Node clone() {
		VariableNode vNode = new VariableNode(this.numVariables);
		vNode.variableName = this.variableName;

		return vNode;
	}

	public double cleanUp() throws CannotReduceException {
		throw new CannotReduceException("Variable " + variableName + " found.");
	}

	@Override
	public String toString() {
		return "VARIA=" + variableName;
	}
}