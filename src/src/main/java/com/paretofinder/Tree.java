package com.paretofinder;

import java.util.LinkedList;

public class Tree {
	protected Node root = null;
	private int numNodes = 0;

	public Tree() {

	}

	public void randomize() {
		root = new OperatorNode();
		root.randomize();

		OperatorNode operatorRoot = (OperatorNode)root;

		while (!(operatorRoot.getChild(0) instanceof OperatorNode)) {
			operatorRoot.setChild(0, Node.getRandomNode());
		}
		
		operatorRoot.getChild(0).randomize();

		recalculateSize();
	}

	public double evaluate(int pointIndex) {
		return root.getValue(pointIndex);
	}

	public int size() {
		return numNodes;
	}

	public int recalculateSize() {
		LinkedList<Node> nodes = new LinkedList<>();
		nodes.add(root);

		numNodes = 0;

		while (nodes.size() != 0) {
			Node nodeInList = nodes.remove(0);
			numNodes++;

			if (nodeInList instanceof OperatorNode) {
				OperatorNode opNode = (OperatorNode)nodeInList;
				nodes.addAll(0, opNode.getChildren());
			}
		}

		return size();
	}

	public Node getNode(int index) {
		if (index >= numNodes || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + "    Size: " + numNodes);
		}

		int counter = 0;

		LinkedList<Node> nodes = new LinkedList<>();
		nodes.add(root);

		while (nodes.size() != 0) {
			Node nodeInList = nodes.remove(0);

			if (counter == index) {
				return nodeInList;
			}

			if (nodeInList instanceof OperatorNode) {
				OperatorNode opNode = (OperatorNode)nodeInList;
				nodes.addAll(0, opNode.getChildren());
			}

			counter++;
		}

		throw new RuntimeException("Node not found. Index: " + index + "   Size: " + size() + "   newSize: " + recalculateSize());
	}

	public void setNode(int index, Node node) {
		if (node == null) {
			throw new NullPointerException();
		}

		if (index >= numNodes || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + "    Size: " + numNodes);
		}

		if (index == 0) { //  replace root node
			root = node;
		}
		else { // replace node in subtree
			int counter = 0;

			LinkedList<Node> nodes = new LinkedList<>();
			LinkedList<Tuple<OperatorNode, Integer>> parents = new LinkedList<>();
			nodes.add(root);

			while (nodes.size() != 0) {
				Node nodeInList = nodes.remove(0);

				if (index == counter) {
					parents.get(0)._1.setChild(parents.get(0)._2, node);
				}

				if (nodeInList instanceof OperatorNode) {
					OperatorNode opNode = (OperatorNode)nodeInList;
					parents.add(0, new Tuple<OperatorNode, Integer>(opNode, new Integer(0)));

					nodes.addAll(0, opNode.getChildren());
				}
				else {
					while (!parents.isEmpty() && parents.get(0)._2 == parents.get(0)._1.size() - 1) {
						parents.remove(0);
					}

					if (!parents.isEmpty()) {
						parents.get(0)._2 = new Integer(parents.get(0)._2 + 1);
					}
				}

				counter++;
			}
		}

		recalculateSize();
	}

	public Tree clone() {
		Tree returnTree = new Tree();
		returnTree.root = this.root.clone();
		returnTree.numNodes = this.numNodes;

		return returnTree;
	}

	public void cleanUp() throws CannotReduceException {
		try {
			if (root instanceof OperatorNode) {
				double value = root.cleanUp();
				ConstantNode cn = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
				cn.setValue(value);
				root = cn;
			}
		}
		finally {
			recalculateSize();
		}
	}

	@Override
	public String toString() {
		return depthFirstSearch(root, 0);
	}

	public String depthFirstSearch(Node node, int depth) {
		String stringRep = "  ";

		for (int i = 0; i < depth; i++) {
			stringRep += "| ";
		}

		stringRep += "|-" + node + "\n  ";

		if (node instanceof OperatorNode) {
			stringRep += ((OperatorNode)node).depthFirstSearch(depth + 1);
		}

		return stringRep;
	}
}