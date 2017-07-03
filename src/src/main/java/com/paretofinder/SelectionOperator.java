package com.paretofinder;

import java.util.ArrayList;

public interface SelectionOperator {
	public ArrayList<Individual> selectParents(ArrayList<Individual> individuals);
}