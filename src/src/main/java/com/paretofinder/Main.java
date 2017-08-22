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

		OperatorNode op1 = new OperatorNode();

		while (op1.size() != 2) {
			op1.randomize();
		}

		ConstantNode const1 = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
		const1.setValue(1.0);
		op1.setChild(0, const1);

		ConstantNode const2 = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
		const1.setValue(2.0);
		op1.setChild(1, const2);

		OperatorNode op3 = new OperatorNode();

		while (op3.size() != 2) {
			op3.randomize();
		}

		ConstantNode const3 = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
		const3.setValue(3.0);
		op3.setChild(0, const3);

		ConstantNode const4 = (ConstantNode)Node.getNode(Node.CONSTANT_NODE);
		const4.setValue(4.0);
		op3.setChild(1, const4);

		OperatorNode op2 = new OperatorNode();

		while (op2.size() != 2) {
			op2.randomize();
		}

		op2.setChild(0, op1);
		op2.setChild(1, op3);

		System.out.println("OPERATOR NODE BEFORE REDUCTION: " + op2.getValue(-1, null));
		System.out.println(op2 + "\n" + op2.depthFirstSearch(1));
		try {
			op2.cleanUp();
		}
		catch(CannotReduceException e) {
			e.printStackTrace();
		}
		System.out.println("OPERATOR NODE AFTER REDUCTION: " + op2.getValue(-1, null));
		System.out.println(op2 + "\n" + op2.depthFirstSearch(1));

		Individual best = gp.performOneIteration();
		System.out.println("BEST AFTER ITERATION: \n" + best);
	}

	public static void main(String[] args) {
		try {
			ArrayList<HashMap<String, Double>> points = getPoints();
			Node.variables = new ArrayList<>();
			Iterator<String> it = points.get(0).keySet().iterator();
			// it.next();

			while (it.hasNext()) {
				Node.variables.add(it.next());
			}

			GeneticProgram gp = new GeneticProgram(points);
			System.out.println("\n\u001B[34mNumber of Individuals:\u001B[0m " + GeneticProgram.NUM_INDIVIDUALS);

			/////////////////////			TEST
			// test(gp);
			// return;
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