package com.paretofinder;

import java.util.Random;

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

	public double getValue(int pointIndex) {
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

	@Override
	public String toString() {
		return "CONST=" + value;
	}
}