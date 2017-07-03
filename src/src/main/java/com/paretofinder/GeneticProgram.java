package com.paretofinder;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneticProgram {
	public static final int NUM_INDIVIDUALS = 30;
	public static final int MAX_ITERATIONS_NO_PROGRESS = 50000;

	private int numPoints;
	private ArrayList<Individual> individuals = new ArrayList<>();
	private SelectionOperator selectionOperator = new TournamentSelection();
	public static Individual bestIndividual = null;

	public GeneticProgram(ArrayList<HashMap<String, Double>> points) {
		Node.points = points;
		Node.numVariables = points.get(0).size();
		Node.minRange = -100.0;
		Node.maxRange = 100.0;

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
		Individual bestIndividual = individuals.get(0);
		ArrayList<Individual> newGeneration = new ArrayList<>();

		while (newGeneration.size() < individuals.size()) {
			ArrayList<Individual> parents = selectionOperator.selectParents(individuals);
			ArrayList<Individual> children = CrossOver.performCrossOver(individuals);

			for (Individual child : children) {
				Mutation.mutate(child, this.bestIndividual);
			}

			int added = 0;
			int maxAdd = parents.size();

			while (added < maxAdd) {
				if (parents.isEmpty()) {
					newGeneration.add(0, children.remove(0));
				}
				else if (children.isEmpty()) {
					newGeneration.add(0, parents.remove(0));
				}
				else {
					if (parents.get(0).getFitness() < children.get(0).getFitness()) {
						newGeneration.add(0, parents.remove(0));
					}
					else {
						newGeneration.add(0, children.remove(0));
					}
				}

				if (newGeneration.get(0).getFitness() < bestIndividual.getFitness()) {
					bestIndividual = newGeneration.get(0);
				}

				added++;
			}
		}

		individuals = newGeneration;

		return bestIndividual;
	}

	public void run() {
		int lastProgressIteration = 0;
		int currentIteration = 0;
		bestIndividual = calculateBestIndividual();

		while (lastProgressIteration < MAX_ITERATIONS_NO_PROGRESS) {
			Mutation.modifyNodeProbability = 1.0 - lastProgressIteration / MAX_ITERATIONS_NO_PROGRESS;
			Mutation.adaptNodeProbability = 1.0 - Mutation.modifyNodeProbability;
			Individual bestInIteration = performOneIteration();
			// System.out.println("bestIndividual: " + bestIndividual.getFitness());
			// System.out.println("bestInIteration: " + bestInIteration.getFitness());

			if (bestInIteration.getFitness() < bestIndividual.getFitness()) {
				bestIndividual = bestInIteration;
				lastProgressIteration = 0;
			}
			else {
				lastProgressIteration++;
			}

			currentIteration++;

			if (currentIteration % 1 == 0) {
				System.out.print("\rcurrentIteration: " + currentIteration + "   lastUpdate: " + lastProgressIteration + "  bestFitness: " + bestIndividual.getFitness() + "                        ");

				// try {
				// 	Thread.sleep(200);
				// }
				// catch (InterruptedException e) {
					
				// }
			}
		}

		System.out.println();
	}
}