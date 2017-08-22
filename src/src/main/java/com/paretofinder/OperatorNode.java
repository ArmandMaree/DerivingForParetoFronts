package com.paretofinder;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;

public class OperatorNode extends Node {
	private int operatorIndex;
	private char[] possibleOperators = {'+', '-', '*', '/', '^', '#', '&'};
	private char[] operatorNumOperands = {2, 2, 2, 2, 2, 1, 1};
	private ArrayList<Node> children = new ArrayList<>();
	private Random rand = new Random();
	private String counterVariable = null;
	private int endValueCounter = 10;
	private int startValueCounter = 1;

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
				child.parent = this;
				child.randomize();
				children.add(child);
			}
		}
	}

	public ArrayList<String> getVariables() {
		ArrayList<String> variables = new ArrayList<>();

		if (parent != null) {
			for (String value : parent.getVariables()) {
				variables.add(new String(value));
			}
		}
		else {
			for (String value : super.getVariables()) {
				variables.add(new String(value));
			}
		}

		if (operatorIndex == 6) {
			variables.add(variables.size() - 1, counterVariable);
		}
		
		return variables;
	}

	public double getValue(int pointIndex, HashMap<String, Double> variableValues) {
		double value = 0.0;

		if (variableValues == null) {
			if (pointIndex != -1) {
				variableValues = Node.points.get(pointIndex);
			}
			else {
				variableValues = new HashMap<>();
			}
		}

		switch (operatorIndex) {
			case 0: // +
				value = children.get(0).getValue(pointIndex, variableValues) + children.get(1).getValue(pointIndex, variableValues);
				break;
			case 1: // -
				value = children.get(0).getValue(pointIndex, variableValues) - children.get(1).getValue(pointIndex, variableValues);
				break;
			case 2: // *
				value = children.get(0).getValue(pointIndex, variableValues) * children.get(1).getValue(pointIndex, variableValues);
				break;
			case 3: // /
				value = children.get(0).getValue(pointIndex, variableValues) / children.get(1).getValue(pointIndex, variableValues);
				break;
			case 4: // ^ power
				value = Math.pow(children.get(0).getValue(pointIndex, variableValues), children.get(1).getValue(pointIndex, variableValues));
				break;
			case 5: // # square root
				value = Math.sqrt(children.get(0).getValue(pointIndex, variableValues));
				break;
			case 6: // & sigma summation
				HashMap<String, Double> variableValuesLocal = new HashMap<>();
				Iterator<String> it = variableValues.keySet().iterator();

				while (it.hasNext()) {
					String key = it.next();
					variableValuesLocal.put(key, variableValuesLocal.get(key));
				}

				for (int i = startValueCounter; i <= endValueCounter; i++) {
					variableValues.put(counterVariable, new Double(i));
					value += children.get(0).getValue(pointIndex, variableValues);				
				}
				
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
		if (child == null) {
			throw new NullPointerException();
		}
		else {
			children.set(index, child);
		}
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
		return children.size();
	}

	public double cleanUp() throws CannotReduceException {
		boolean containsVariableInSubtree = false;
		ArrayList<Node> newChildren = new ArrayList<>();

		for (int i = 0; i < children.size(); i++) {
			try {
				if (!(children.get(i) instanceof ConstantNode)) { // is not ConstantNode
					double value = children.get(i).cleanUp();
					ConstantNode cn = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
					cn.setValue(value);
					newChildren.add(cn);
				}
				else { // is ConstantNode
					newChildren.add(children.get(i));
				}
			}
			catch (CannotReduceException e) {
				containsVariableInSubtree = true;
				newChildren.add(children.get(i));
			}

			if (newChildren.get(newChildren.size() - 1) == null) {
				throw new NullPointerException();
			}
		}

		children = newChildren;

		if (!containsVariableInSubtree) {
			double returnValue = getValue(-1, null);

			if (returnValue != returnValue) { // returnValue == NaN
				return Double.MAX_VALUE;
			}
			else {
				return returnValue;			
			}
		}
		else {
			throw new CannotReduceException();
		}
	}

	@Override
	public String toString() {
		String s = "OPERA=" + possibleOperators[operatorIndex];

		if (operatorIndex == 6) {
		 	s += "   [" + startValueCounter + "," + endValueCounter + "]";
		}

		return  s;
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