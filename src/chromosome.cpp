#include "chromosome.h"

using namespace std;

Chromosome::Chromosome(int numDimensions, vector<vector<float>>* points) {
	this->points = points;
	for (int i = 0; i < NUM_GENES; i++) {
		cells.push_back(Cell(numDimensions));

		if (2 * i + 1 >= NUM_GENES) {
			if (!cells[i].isLeaf()) {
				cells[i].mutateLeaf();
			}
		}
		// else if (cells[i].getValueType() == OPERATOR_TYPE) {
		// 	int operatorIndex = cells[i].getValueOperator();

		// 	for (int i = 0; i < cells[i].operatorNumChildren[operatorIndex]; i++) {
		// 		if (/* condition */)
		// 		{
		// 			/* code */
		// 		}
		// 	}
		// }
	}
}

Chromosome::~Chromosome() {

}

float Chromosome::getFitness() {
	// less is better
	/*
		for all datapoints {
			delta = targetResult - currentFunction(variable)
			sum += delta
		}
	 */
}

void Chromosome::depthFirstSearchLVR(vector<Cell> &tree, int index) const {
	if (index < NUM_GENES) {
		int leftChildIndex = 2 * index + 1;
		depthFirstSearchLVR(tree, leftChildIndex);
		tree.push_back(cells[index]); // visit
		int rightChildIndex = 2 * index + 2;
		depthFirstSearchLVR(tree, rightChildIndex);
	}
}

void Chromosome::depthFirstSearchLVRTrim(vector<Cell> &tree, int index) const {
	cout << "testing: " << cells[index] << " at " << index << endl;
	if (index < NUM_GENES) {

		if (!cells[index].isLeaf()) { // go left if possible
			int leftChildIndex = 2 * index + 1;
			depthFirstSearchLVRTrim(tree, leftChildIndex);
		}

		tree.push_back(cells[index]); // visit

		if (!cells[index].isLeaf()) { // go right if possible
			int rightChildIndex = 2 * index + 2;
			depthFirstSearchLVRTrim(tree, rightChildIndex);
		}
	}
}

ostream& operator<<(ostream& os, const Chromosome& chromosome) {
	vector<Cell> lvrTree;
	chromosome.depthFirstSearchLVRTrim(lvrTree, 0);

	for (int i = 0; i < lvrTree.size(); i++) {
		os << lvrTree[i];
	}

	return os;
}

