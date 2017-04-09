#ifndef CELL_H
#define CELL_H

#include <iostream> 

#include "constants.h"

using namespace std;

class Cell {
	public:
		Cell(int numDimensions);
		~Cell();
		int mutate();
		void mutateLeaf();
		void mutateFloat();
		void mutateVariable();
		void mutateOperator();
		bool isLeaf() const;

		// mutators and accessors
		int getValueType();
		float getValueFloat();
		int getValueVariable();
		int getValueOperator();

		friend ostream& operator<<(ostream& os, const Cell& cell); 

		int numValidOperators = 6;
		char validOperators[6] = {'+', '-', '*', '/', '^', 'R'};
		int operatorNumChildren[6] = {2, 2, 2, 2, 1, 1};
	
	private:
		int numDimensions = 0;
		int valueType = 0;
		float valueFloat = 0.0;
		int valueVariable = 0;
		int valueOperator = 0;
};

#endif