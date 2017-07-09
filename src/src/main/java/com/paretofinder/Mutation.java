package com.paretofinder;

import java.util.Random;

public class Mutation {
	public static final double mutationProbability = 0.3;
	public static final double modifyNodeProbability = 0.5; // higher will result in more radical changes
	public static final double adaptNodeProbability = 1.0 - modifyNodeProbability;
	private static Random rand = new Random();

	public static void mutate(Individual individual, Individual bestIndividual) {
		boolean changed = false;

		for (int i = 0; i < individual.size(); i++) {
			if (rand.nextDouble() < mutationProbability) { // mutate gene
				double mutationTypeProbability = rand.nextDouble();
				// System.out.println("Changing " + i + "      " + mutationTypeProbability  + "    " + adaptNodeProbability);

				if (mutationTypeProbability <  adaptNodeProbability) {
					Node node = individual.getNode(i);
					
					if (node instanceof ConstantNode) {
						ConstantNode cNode = (ConstantNode)node;

						if (individual.getFitness() > bestIndividual.getFitness()) {
							double newValue = cNode.getValue(i) + (Node.maxRange - Node.minRange) * (bestIndividual.getFitness() / individual.getFitness()) * (rand.nextDouble() * 2 - 1);
							cNode.setValue(newValue);
							changed = true;
							// System.out.println("Changed Const by: " + newValue + "    " + (bestIndividual.getFitness() / individual.getFitness()));
						}
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