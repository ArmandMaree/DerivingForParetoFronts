package com.paretofinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Node {
	public static final int OPERATOR_NODE = 0;
	public static final int VARIABLE_NODE = 1;
	public static final int CONSTANT_NODE = 2;

	public static int numVariables;
	public static double minRange;
	public static double maxRange;
	public static ArrayList<HashMap<String, Double>> points;

	public static Random rand = new Random();

	public static Node getNode(int nodeType) {
		switch (nodeType) {
			case OPERATOR_NODE:
				return new OperatorNode();
			case VARIABLE_NODE:
				return new VariableNode(numVariables); 
			case CONSTANT_NODE:
				return new ConstantNode(minRange, maxRange);
			default:
				throw new RuntimeException("nodeType: " + nodeType + " not legal!");
		}
	}

	public static Node getRandomNode() {
		return getNode(rand.nextInt(3));
	}

	public static Node getRandomLeafNode() {
		int nodeType = rand.nextInt(2) + 1;

		switch (nodeType) {
			case VARIABLE_NODE:
				return new VariableNode(numVariables);
			case CONSTANT_NODE:
				return new ConstantNode(minRange, maxRange);
			default:
				throw new RuntimeException("nodeType: " + nodeType + " not legal leaf node!");
		}
	}

	public static double getVariableValue(int pointIndex, String variableName) {
		return points.get(pointIndex).get(variableName);
	}

	public double getValue(int pointIndex) {
		return Double.MAX_VALUE;
	}

	public void randomize() {
		
	}

	public Node clone() {
		throw new RuntimeException("clone not implemented for " + this.getClass().getName() + ".");
	}

	public double cleanUp() throws CannotReduceException {
		throw new RuntimeException("Node reduction not implemented for " + this.getClass().getName());
	}

	@Override
	public String toString() {
		throw new RuntimeException("toString not implemented for " + this.getClass().getName() + ".");
	}
}