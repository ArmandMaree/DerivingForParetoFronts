package com.paretofinder;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneticProgram {
	public static final int NUM_INDIVIDUALS = 30;
	public static final int MAX_ITERATIONS_NO_PROGRESS = 100000;

	private int numPoints;
	private ArrayList<Individual> individuals = new ArrayList<>();
	private SelectionOperator selectionOperator = new TournamentSelection();
	public static Individual bestIndividual = null;

	public GeneticProgram(ArrayList<HashMap<String, Double>> points) {
		Node.points = points;
		Node.numVariables = points.get(0).size();
		Node.minRange = -10.0;
		Node.maxRange = 10.0;

		this.numPoints = points.size();

		for (int i = 0; i < NUM_INDIVIDUALS; i++) {
			individuals.add(new Individual());
			individuals.get(individuals.size() - 1).recalculateFitness();
		}
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public Individual calculateBestIndividual() {
		bestIndividual = individuals.get(0);

		for (Individual individual : individuals) {
			if (individual.getFitness() < bestIndividual.getFitness()) {
				bestIndividual = individual;
			}
		}

		return bestIndividual;
	}

	public Individual performOneIteration() {
		if (bestIndividual == null) {
			bestIndividual = calculateBestIndividual();
		}
		
		Individual bestIndividual = individuals.get(0);
		ArrayList<Individual> newGeneration = new ArrayList<>();
		newGeneration.add(bestIndividual);
		Individual modifiedBestIndividual = Mutation.modifyConstants(bestIndividual);
		// System.out.println(modifiedBestIndividual);

		if (modifiedBestIndividual.getFitness() < bestIndividual.getFitness()) {
			newGeneration.add(0, modifiedBestIndividual);
			bestIndividual = modifiedBestIndividual;
		}

		breedingLoop:
		while (newGeneration.size() < individuals.size()) {
			ArrayList<Individual> parents = selectionOperator.selectParents(individuals);
			ArrayList<Individual> children = CrossOver.performCrossOver(parents);

			for (Individual child : children) {
				Mutation.mutate(child, this.bestIndividual);
				int i = 0;

				for (; i < newGeneration.size() && child.getFitness() > newGeneration.get(i).getFitness(); i++) { // find position in list for child
					
				}

				newGeneration.add(i, child);

				if (newGeneration.size() == individuals.size()) {
					break breedingLoop;
				}
			}

			// int added = 0;
			// int maxAdd = parents.size();

			// while (added < maxAdd) {
			// 	if (parents.isEmpty()) {
			// 		newGeneration.add(0, children.remove(0));
			// 	}
			// 	else if (children.isEmpty()) {
			// 		newGeneration.add(0, parents.remove(0));
			// 	}
			// 	else {
			// 		if (parents.get(0).getFitness() < children.get(0).getFitness()) {
			// 			newGeneration.add(0, parents.remove(0));
			// 		}
			// 		else {
			// 			newGeneration.add(0, children.remove(0));
			// 		}
			// 	}

			// 	if (newGeneration.get(0).getFitness() < bestIndividual.getFitness()) {
			// 		bestIndividual = newGeneration.get(0);
			// 	}

			// 	added++;
			// }
		}

		individuals = newGeneration;

		return bestIndividual;
	}

	public void introduceNewIndividuals() {
		for (int i = (int)(individuals.size() * 0.75); i < individuals.size(); i++) {
			individuals.set(i, new Individual());
			individuals.get(i).recalculateFitness();
		}
	}

	public void run() {
		int lastProgressIteration = 0;
		int currentIteration = 0;
		bestIndividual = calculateBestIndividual();

		while (lastProgressIteration < MAX_ITERATIONS_NO_PROGRESS && bestIndividual.getFitness() != 0.0) {
			// Mutation.modifyNodeProbability = 1.0 - lastProgressIteration / MAX_ITERATIONS_NO_PROGRESS;
			// Mutation.adaptNodeProbability = 1.0 - Mutation.modifyNodeProbability;
			Individual bestInIteration = performOneIteration();
			// System.out.println("bestIndividual: " + bestIndividual.getFitness());
			// System.out.println("bestInIteration: " + bestInIteration.getFitness());

			if (bestInIteration.getFitness() < bestIndividual.getFitness()) {
				bestIndividual = bestInIteration;
				lastProgressIteration = 0;
				System.out.println("\nBest " + bestIndividual);
			}
			else {
				lastProgressIteration++;
			}

			currentIteration++;

			if (currentIteration % 1 == 0) {
				System.out.print("\rcurrentIteration: " + currentIteration + "   lastUpdate: " + lastProgressIteration + "  bestFitness: " + bestIndividual.getFitness() + "    bestIndividual #nodes: " + bestIndividual.size() + "                        ");

				// try {
				// 	Thread.sleep(200);
				// }
				// catch (InterruptedException e) {
					
				// }
			}

			if (currentIteration % 1000 == 0) {
				for (Individual individual : individuals) {
					individual.cleanUp();
				}

				// System.out.println(individuals.get(individuals.size() - 1));
			}

			// if (lastProgressIteration % 10000 == 0 && lastProgressIteration != 0) {
			if (currentIteration % 10000 == 0) {
				introduceNewIndividuals();
			}
		}

		System.out.println();
	}
}