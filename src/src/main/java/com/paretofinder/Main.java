package com.paretofinder;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;

public class Main {
	public static void test(GeneticProgram gp) {
		Individual p1 = new Individual();
		Individual p2 = new Individual();
		System.out.println("PARENT1:\n" + p1);
		System.out.println("PARENT2:\n" + p2);
		System.out.println("PARENT1 SIZE: " + p1.size());
		System.out.println("PARENT2 SIZE: " + p2.size());

		ArrayList<Individual> parents = new ArrayList<>();
		parents.add(p1);
		parents.add(p2);
		ArrayList<Individual> children = new ArrayList<>();

		children = CrossOver.performCrossOver(parents);

		System.out.println("CHILD1: \n" + children.get(0));
		System.out.println("CHILD2: \n" + children.get(1));

		Mutation.mutate(children.get(1), p1);
		System.out.println("CHILD2 AFTER MUTATION: \n" + children.get(1));

		Individual best = gp.performOneIteration();
		System.out.println("BEST AFTER ITERATION: \n" + best);
	}

	public static void main(String[] args) {
		try {
			ArrayList<HashMap<String, Double>> points = getPoints();
			GeneticProgram gp = new GeneticProgram(points);
			System.out.println("\n\u001B[34mNumber of Individuals:\u001B[0m " + GeneticProgram.NUM_INDIVIDUALS);

			/////////////////////			TEST
			// System.out.println("\u001B[32mFitness:\u001B[0m " + gp.getBestIndividual().getFitness());
			// gp.getBestIndividual().printTree();
			// test(gp);
			/////////////////////			END TEST

			gp.run();
			System.out.println("\u001B[32mBest Individual:\u001B[0m                                                                       \n");
			System.out.println("\u001B[32mFitness:\u001B[0m\n " + gp.getBestIndividual().getFitness());
			System.out.println(gp.getBestIndividual());

			for (int i = 0; i < points.size(); i++) {
				System.out.println("Point #" + i + ": " + gp.getBestIndividual().getValueOfLHS(i));
			}
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static ArrayList<HashMap<String, Double>> getPoints() throws IOException {
		ArrayList<HashMap<String, Double>> points = new ArrayList<>();

		InputStream is =  Main.class.getResourceAsStream("/input.json");
		String jsonTxt = IOUtils.toString(is);
		JSONObject inputFile = (JSONObject) JSONSerializer.toJSON(jsonTxt); 

		JSONArray jsonPoints = inputFile.getJSONArray("points");
		System.out.println("\u001B[34mPoints:\u001B[0m");
		System.out.print("                                 \r");

		for (int i = 0; i < jsonPoints.size(); i++) {
			Iterator it = jsonPoints.getJSONObject(i).keys();
			boolean first = true;
			System.out.print("\t[");
			HashMap<String, Double> map = new HashMap<>();

			while(it.hasNext()) {
				String key = (String)it.next();
				map.put(key, jsonPoints.getJSONObject(i).getDouble(key));

				if (first) {
					System.out.print(jsonPoints.getJSONObject(i).getDouble(key));
					first = false;
				}
				else {
					System.out.print(", " + jsonPoints.getJSONObject(i).getDouble(key));
				}
			}

			points.add(map);
			System.out.println("]");
		}

		return points;
	}
}