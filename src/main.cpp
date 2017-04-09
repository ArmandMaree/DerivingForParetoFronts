#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <iostream>
#include <vector>

#include "geneticAlgorithm.h"

using namespace std;

vector<vector<float>> getDataPoints(int* numDimensions);
void printPoints(vector<vector<float>>& points);

int main(int argc, char const *argv[]) {
	int numDimensions;
	vector<vector<float>> points = getDataPoints(&numDimensions);
	printPoints(points);
	cout << endl;
	GeneticAlgorithm ga(numDimensions, &points);
	return 0;
}

vector<vector<float>> getDataPoints(int* numDimensions) {
	vector<vector<float>> points;
	ifstream infile("points.data");
	string line, lhs, rhs; // line, left hand side, right hand side
	size_t pos = -1;
	*numDimensions = 0;

	while (getline(infile, line)) {
		vector<float> v;
		pos = line.find("=");

		if (pos == string::npos || pos == 0) {
			cout << "ERROR 101: " << line << endl;
		}

		lhs = line.substr(0, pos);
		rhs = line.substr(pos + 1);

		v.push_back(stof(rhs));

		int variableCounter = 0;
		istringstream iss(lhs);
		string variable;

		while (getline(iss, variable, ',')){
			v.push_back(stof(variable));
		}

		if (v.size() - 1 > *numDimensions) {
			*numDimensions = (int)v.size();
		}

		points.push_back(v);
	}

	return points;
}

void printPoints(vector<vector<float>>& points) {
	cout << "Points:" << endl;

	for (vector<vector<float>>::iterator i = points.begin(); i != points.end(); i++) {
		float val;
		for (vector<float>::iterator j = i->begin() ; j != i->end(); j++) {
			if (j == i->begin()) {
				val = *j;
				cout << "\tf(";
			}
			else {
				if (j == prev(i->end())) {
					cout << *j;
				}
				else {
					cout << *j << ",";
				}
			}
		}

		cout << ") = " << val << endl;
	}
}
