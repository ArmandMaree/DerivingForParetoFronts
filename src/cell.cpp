#include "cell.h"

#include <cstdlib>
#include <iostream>

using namespace std;

Cell::Cell(int numDimensions) {		
	this->numDimensions = numDimensions;
	mutate();
	cout << "Cell of type " << valueType << " created with value: " << *this << endl;
}

Cell::~Cell() {

}

int Cell::mutate() {
	valueType = rand() % NUM_TYPES;

	switch (valueType) {
		case NUMBER_TYPE:
			mutateFloat();
			break;
		case VARIABLE_TYPE:
			mutateVariable();
			break;
		case OPERATOR_TYPE:
			mutateOperator();
			break;
		default:
			cout << "INCORRECT TYPE " << valueType << endl;
			break;
	}

	return valueType;
}

void Cell::mutateLeaf() {
	valueType = rand() % 2;

	switch (valueType) {
		case NUMBER_TYPE:
			mutateFloat();
			break;
		case VARIABLE_TYPE:
			mutateVariable();
			break;
		default:
			cout << "INCORRECT TYPE " << valueType << endl;
			break;
	}
}

void Cell::mutateFloat() {
	valueType = NUMBER_TYPE;
	valueFloat = static_cast <float> (rand()) / (static_cast <float> (RAND_MAX/MAX_CELL_FLOAT)); // generate random float
}

void Cell::mutateVariable() {
	valueType = VARIABLE_TYPE;
	valueVariable = rand() % numDimensions;
}

void Cell::mutateOperator() {
	valueType = OPERATOR_TYPE;
	valueOperator = rand() % numValidOperators;
}

bool Cell::isLeaf() const {
	return valueType == NUMBER_TYPE || valueType == VARIABLE_TYPE;
}

/**************************************************************
 * ACCESSORS, MUTATORS, STRINGIFIERS
 **************************************************************/

int Cell::getValueType() {
	return valueType;
}

float Cell::getValueFloat() {
	return valueFloat;
}

int Cell::getValueVariable() {
	return valueVariable;
}

int Cell::getValueOperator() {
	return valueOperator;
}

ostream& operator<<(ostream& os, const Cell& cell)  {
	switch (cell.valueType) {
		case NUMBER_TYPE:
			os << cell.valueFloat;
			break;
		case VARIABLE_TYPE:
			os << "x" << cell.valueVariable;
			break;
		case OPERATOR_TYPE:
			os << cell.validOperators[cell.valueOperator];
			break;
		default:
			cout << "INCORRECT TYPE " << cell.valueType << endl;
			break;
	}

	return os;
}