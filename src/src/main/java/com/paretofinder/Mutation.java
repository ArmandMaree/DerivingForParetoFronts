package com.paretofinder;

import java.util.Random;

public class Mutation {
	public static double mutationProbability = 0.05;
	public static double modifyNodeProbability = 1.0;
	public static double adaptNodeProbability = 1.0 - modifyNodeProbability;
	private static Random rand = new Random();

	public static void mutate(Individual individual, Individual bestIndividual) {
		boolean changed = false;

		for (int i = 0; i < individual.size(); i++) {
			if (rand.nextDouble() < mutationProbability) { // mutate gene
				double mutationTypeProbability = rand.nextDouble();

				if (mutationTypeProbability <  adaptNodeProbability) {
					Node node = individual.getNode(i);
					
					if (node instanceof ConstantNode) {
						ConstantNode cNode = (ConstantNode)node;

						double newValue = cNode.getValue(i) + (rand.nextDouble() * 2.0 - 1.0); // value + U(-1, 1)
						cNode.setValue(newValue);
						changed = true;
					}
					else if (node instanceof VariableNode) {
						node.randomize();
						changed = true;
					}
				}
				else if (mutationTypeProbability < adaptNodeProbability + modifyNodeProbability) {  // elseif to ensure future scalability
					Node randomNode = Node.getRandomNode();
					randomNode.randomize();
					individual.setNode(i, randomNode);
					i += ((randomNode instanceof OperatorNode) ? ((OperatorNode)randomNode).size() + 1 : 1) - 1;

					changed = true;
				}
			}
		}

		if (changed) {
			individual.recalculateFitness();
		}
	}
}