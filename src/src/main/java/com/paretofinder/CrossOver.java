package com.paretofinder;

import java.util.ArrayList;
import java.util.Random;

public class CrossOver {
	private static Random randomGenerator = new Random();

	public static ArrayList<Individual> performCrossOver(ArrayList<Individual> parents) {
		int p1Size = parents.get(0).size();
		int p2Size = parents.get(1).size();

		int p1COPoint = randomGenerator.nextInt(p1Size);
		int p2COPoint = randomGenerator.nextInt(p2Size);

		// System.out.println("P1 INDEX: " + p1COPoint);
		// System.out.println("P2 INDEX: " + p2COPoint);

		Individual child1 = parents.get(0).clone();
		Individual child2 = parents.get(1).clone();

		Node node1 = child1.getNode(p1COPoint);
		Node node2 = child2.getNode(p2COPoint);

		// System.out.println("Node1: \n" + node1);
		// System.out.println("Node2: \n" + node2);

		try {
			child1.setNode(p1COPoint, node2);
		}
		catch (NullPointerException e) {
			System.out.println("p2COPoint: " + p2COPoint);
			throw e;
		}

		try {
			child2.setNode(p2COPoint, node1);
		}
		catch (NullPointerException e) {
			System.out.println("p1COPoint: " + p1COPoint);
			throw e;
		}

		child1.recalculateFitness();
		child2.recalculateFitness();

		ArrayList<Individual> returnList = new ArrayList<>();

		returnList.add(child1);
		returnList.add(child2);
		
		return returnList;
	}
}