package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Random;
import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 *		to save having to re-implement them in all 
 *		your other heuristics!
 *
 *
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm 
 */
public class CrossoverHeuristicOperators {
	
	protected final Random random;
	protected TSPObjectiveFunction f;

	CrossoverHeuristicOperators(Random random) {
		this.random = random;
	}
	
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.f = (TSPObjectiveFunction) f;
	}

	// map parameter to correct iterations number
	int adjustParameter(int first, int second, int third, int forth, int fifth, int sixth, double parameter) {
		if (parameter < 0.2) {
			return first;
		}
		else if (parameter < 0.4) {
			return second;
		}
		else if (parameter < 0.6) {
			return third;
		}
		else if (parameter < 0.8) {
			return forth;
		}
		else if (parameter < 1.0) {
			return fifth;
		}
		else if (parameter == 1) {
			return sixth;
		}
		return 0;
	}

	// create 2 random cut point and return it in increasing order
	int[] createCutPoints(int limit) {
		int[] randoms = new int[2];
		int temp1 = random.nextInt(limit);
		int temp2 = random.nextInt(limit);
		if (temp2 == temp1) {
			temp2 = limit;
		}
		randoms[0] = Math.min(temp1, temp2);
		randoms[1] = Math.max(temp1, temp2);
		return randoms;
	}

	// return next index
	int nextIndex(int index, int limit) {
		int indexNext = index + 1;
		if (indexNext == limit) {
			indexNext = 0;
		}
		return indexNext;
	}

	// initialize array with values -1
	ArrayList<Integer> initializeArrayListAsNulls(ArrayList<Integer> arrayList, int limit) {
		for (int i = 0; i < limit; i++) {
			arrayList.add(-1);
		}
		return arrayList;
	}

}