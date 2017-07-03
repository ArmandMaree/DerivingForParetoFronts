package com.paretofinder;

import java.util.ArrayList;
import java.util.Random;

public class OperatorNode extends Node {
	private int operatorIndex;
	private char[] possibleOperators = {'+', '-', '*', '/', '^', '#'};
	private char[] operatorNumOperands = {2, 2, 2, 2, 2, 1};
	private ArrayList<Node> children = new ArrayList<>();
	private Random rand = new Random();

	public OperatorNode() {

	}

	public void randomize() {
		int depth = 1;
		operatorIndex = rand.nextInt(possibleOperators.length);

		if (depth <= 0) {
			children = null;
		}
		else {
			children = new ArrayList<>();

			while(children.size() < operatorNumOperands[operatorIndex]) {
				Node child = Node.getRandomLeafNode();
				child.randomize();
				children.add(child);
			}
		}
	}

	public double getValue(int pointIndex) {
		double value = children.get(0).getValue(pointIndex);

		switch (operatorIndex) {
			case 0: // +
				value = children.get(0).getValue(pointIndex) + children.get(1).getValue(pointIndex);
				break;
			case 1: // -
				value = children.get(0).getValue(pointIndex) - children.get(1).getValue(pointIndex);
				break;
			case 2: // *
				value = children.get(0).getValue(pointIndex) * children.get(1).getValue(pointIndex);
				break;
			case 3: // /
				value = children.get(0).getValue(pointIndex) / children.get(1).getValue(pointIndex);
				break;
			case 4: // ^ power
				value = Math.pow(children.get(0).getValue(pointIndex), children.get(1).getValue(pointIndex));
				break;
			case 5: // # square root
				value = Math.sqrt(children.get(0).getValue(pointIndex));
				break;
			default:
				System.out.println("Invalid operator: " + operatorIndex);
		}

		return value;
	}

	public Node getChild(int index) {
		return children.get(index);
	}

	public void setChild(int index, Node child) {
		children.set(index, child);
	}

	public Node clone() {
		OperatorNode opNode = new OperatorNode();
		opNode.operatorIndex = this.operatorIndex;

		for (Node node : children) {
			opNode.children.add(node.clone());
		}

		return opNode;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public int size() {
		return (children == null) ? 0 : children.size();
	}

	@Override
	public String toString() {
		return "OPERA=" + possibleOperators[operatorIndex];
	}

	public String depthFirstSearch(int depth) {
		String stringRep = "";

		for (Node child : children) {
			for (int i = 0; i < depth; i++) {
				stringRep += "| ";
			}

			stringRep += "|-" + child + "\n  ";

			if (child instanceof OperatorNode) {
				stringRep += ((OperatorNode)child).depthFirstSearch(depth + 1);
			}
		}

		return stringRep;
	}
}