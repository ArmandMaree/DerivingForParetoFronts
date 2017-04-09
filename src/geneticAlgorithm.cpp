#include "geneticAlgorithm.h"

#include <cstdlib>
#include <ctime>
#include <iostream>

using namespace std;

GeneticAlgorithm::GeneticAlgorithm(int numDimensions, vector<vector<float>>* points) {
	this->points = points;
	/* initialize random seed: */
	srand(time(NULL));

	for (int i = 0; i < NUM_CHROMOSOMES; i++) {
		chromosomes.push_back(Chromosome(numDimensions, points));
		cout << chromosomes[i] << endl;
	}
}

GeneticAlgorithm::~GeneticAlgorithm() {

}
