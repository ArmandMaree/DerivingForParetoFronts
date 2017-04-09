#ifndef CHROMOSOME_H
#define CHROMOSOME_H

#include <vector>
#include <iostream>

#include "constants.h"
#include "cell.h"

using namespace std;

class Chromosome {
	public:
		Chromosome(int numDimensions, vector<vector<float>>* points);
		~Chromosome();
		float getFitness();
		void depthFirstSearchLVR(vector<Cell> &tree, int index) const;
		void depthFirstSearchLVRTrim(vector<Cell> &tree, int index) const;

		friend ostream& operator<<(ostream& os, const Chromosome& chromosome); 

	private:
		vector<Cell> cells;
		vector<vector<float>>* points;
};

#endif

