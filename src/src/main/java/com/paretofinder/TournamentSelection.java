package com.paretofinder;

import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection implements SelectionOperator {
	public static double tournamentSize = 0.1;
	private static Random rand = new Random();

	public ArrayList<Individual> selectParents(ArrayList<Individual> individuals) {
		int numIndividualsInTournament = (int)Math.ceil(individuals.size() * tournamentSize);

		ArrayList<Integer> tournamentIndividuals = new ArrayList<>();

		while (tournamentIndividuals.size() < numIndividualsInTournament) {
			int index = rand.nextInt(individuals.size());

			if (!tournamentIndividuals.contains(index)) {
				tournamentIndividuals.add(index);
			}
		}

		Integer best1 = null;

		for (Integer i : tournamentIndividuals) {
			if (best1 == null || individuals.get(i).getFitness() < individuals.get(best1).getFitness()) {
				best1 = i;
			}
		}

		tournamentIndividuals = new ArrayList<>();

		while (tournamentIndividuals.size() < numIndividualsInTournament) {
			int index = rand.nextInt(individuals.size());

			if (!tournamentIndividuals.contains(index) && index != best1) {
				tournamentIndividuals.add(index);
			}
		}

		Integer best2 = null;

		for (Integer i : tournamentIndividuals) {
			if (best2 == null || individuals.get(i).getFitness() < individuals.get(best2).getFitness()) {
				best2 = i;
			}
		}

		ArrayList<Individual> parents = new ArrayList<>();
		parents.add(individuals.get(best1));
		parents.add(individuals.get(best2));

		return parents;
	}
}