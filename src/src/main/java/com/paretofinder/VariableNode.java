package com.paretofinder;

import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

public class VariableNode extends Node {
	protected String variableName;
	private Random rand = new Random();

	public VariableNode() {
	}

	public void randomize() {
		ArrayList<String> variables;

		if (parent == null) {
			variables = Node.variables;
		}
		else {
			variables = parent.getVariables();
		}

		variableName = variables.get(rand.nextInt(variables.size() - 1));
	}

	public double getValue(int pointIndex, HashMap<String, Double> variableValues) {
		if (parent == null) {
			return Node.points.get(pointIndex).get(variableName);
		}
		else {
			return parent.getVariableValue(pointIndex, variableName);
		}
	}

	public Node clone() {
		VariableNode vNode = new VariableNode();
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