package com.paretofinder;

import java.util.Random;
import java.util.HashMap;

public class ConstantNode extends Node {
	private double min;
	private double max;
	private double value;
	private Random rand = new Random();

	public ConstantNode(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public void randomize() {
		value = rand.nextDouble() * (max - min) + min;
	}

	public double getValue(int pointIndex, HashMap<String, Double> variableValues) {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Node clone() {
		ConstantNode cNode = new ConstantNode(this.min, this.max);
		cNode.value = this.value;

		return cNode;
	}

	public double cleanUp() throws CannotReduceException {
		return getValue(-1, null);
	}

	@Override
	public String toString() {
		return "CONST=" + value;
	}
}