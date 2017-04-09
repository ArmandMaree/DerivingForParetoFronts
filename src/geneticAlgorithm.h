#ifndef GENETICALGORITHM_H
#define GENETICALGORITHM_H

#include <vector>

#include "constants.h"
#include "chromosome.h"

using namespace std;

class GeneticAlgorithm {
	public:
		GeneticAlgorithm(int numDimensions, vector<vector<float>>* points);
		~GeneticAlgorithm();
	
	private:
		vector<Chromosome> chromosomes;
		vector<vector<float>>* points;
};

#endif