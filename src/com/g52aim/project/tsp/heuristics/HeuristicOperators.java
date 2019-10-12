package com.g52aim.project.tsp.heuristics;

import java.util.*;
import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 *		to save having to re-implement them in all 
 *		your other heuristics!
 *		( swapping two cities seems to be popular )
 *
 *
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm 
 */
public class HeuristicOperators {
	
	protected final Random random;
	protected TSPObjectiveFunction f;

	HeuristicOperators(Random random) {
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

	// get 2 random indexes
	int[] randomNumberPair(int limit) {
		int[] randoms = new int[2];
		int temp1 = random.nextInt(limit);
		int temp2 = random.nextInt(limit - 1);
		if (temp2 == temp1) {
			temp2 = limit - 1;
		}
		randoms[0] = temp1;
		randoms[1] = temp2;
		return randoms;
	}

	// get pair of random sorted indexes and return it in increasing order
	int[] sortedRandomNumberPair(int limit) {
		int[] randoms = new int[2];
		int temp1 = random.nextInt(limit);
		int temp2 = random.nextInt(limit - 1);
		if (temp2 == randoms[0]) {
			temp2 = limit - 1;
		}
		randoms[0] = Math.min(temp1, temp2);
		randoms[1] = Math.max(temp1, temp2);
		return randoms;
	}

	// get pair of consecutive indexes
	int[] nextNumberPair(int index, int limit) {
		int[] pair = new int[2];
		pair[0] = index;
		int indexNext = nextIndex(index, limit);
		pair[1] = indexNext;
		return pair;
	}

	// create random permutation of indexes
	ArrayList<Integer> createRandomOrder(int limit) {
		ArrayList<Integer> randomOrder = new ArrayList<>(limit);
		for (int i = 0; i < limit; i++) {
			randomOrder.add(i);
		}
		Collections.shuffle(randomOrder, random);
		return randomOrder;
	}

	// return previous index
	private int previousIndex(int index, int limit) {
		int indexPrevious = index - 1;
		if (indexPrevious < 0) {
			indexPrevious = limit - 1;
		}
		return indexPrevious;
	}

	// return next index
	private int nextIndex(int index, int limit) {
		int indexNext = index + 1;
		if (indexNext == limit) {
			indexNext = 0;
		}
		return indexNext;
	}

	// delta evaluation optimised for Adjacent Swap
	double deltaEvaluationAdjacentSwap(TSPSolutionInterface solution, double currentFitness, int index1, int index2) {
		ArrayList<Integer> cities = solution.getSolutionRepresentation().getRepresentationOfSolution();
		int cityPrevious = cities.get(previousIndex(index1, solution.getNumberOfCities()));
		int city1 = cities.get(index1);
		int city2 = cities.get(index2);
		int cityNext = cities.get(nextIndex(index2, solution.getNumberOfCities()));

		double distanceAdd = f.getCost(cityPrevious, city1) + f.getCost(city2, cityNext);
		double distanceRemove = f.getCost(cityPrevious, city2) + f.getCost(city1, cityNext);
		double delta = distanceAdd - distanceRemove;
		return currentFitness + delta;
	}

	// delta evaluation optimised for Two Opt
	double deltaEvaluationTwoOpt(TSPSolutionInterface solution, ArrayList<Integer> previousSolution, double currentFitness, int index1, int index2) {
		ArrayList<Integer> cities = solution.getSolutionRepresentation().getRepresentationOfSolution();
		int cityPrevious1 = cities.get(previousIndex(index1, solution.getNumberOfCities()));
		int cityPrevious2 = previousSolution.get(previousIndex(index1, solution.getNumberOfCities()));
		double distanceAdd = 0;
		double distanceRemove = 0;

		int counter = index2 - index1 + 2;
		for (int i = 0; i < counter; i++) {
			int cityNext1 = cities.get(index1);
			distanceAdd += f.getCost(cityPrevious1, cityNext1);
			cityPrevious1 = cityNext1;
			int cityNext2 = previousSolution.get(index1);
			distanceRemove += f.getCost(cityPrevious2, cityNext2);
			cityPrevious2 = cityNext2;
			index1 = nextIndex(index1, solution.getNumberOfCities());
		}
		double delta = distanceAdd - distanceRemove;
		return currentFitness + delta;
	}

	// delta evaluation optimised for reinsertion
	double deltaEvaluationReinsertion(TSPSolutionInterface solution, ArrayList<Integer> previousSolution, double currentFitness, int index1, int index2) {
		ArrayList<Integer> cities = solution.getSolutionRepresentation().getRepresentationOfSolution();
		int city1Previous = previousSolution.get(previousIndex(index1, solution.getNumberOfCities()));
		int city1 = previousSolution.get(index1);
		int city1Next = previousSolution.get(nextIndex(index1, solution.getNumberOfCities()));
		int city2Previous = cities.get(previousIndex(index2, solution.getNumberOfCities()));
		int city2 = cities.get(index2);
		int city2Next = cities.get(nextIndex(index2, solution.getNumberOfCities()));

		double distanceAdd = f.getCost(city1Previous, city1Next) + f.getCost(city2Previous, city2) + f.getCost(city2, city2Next);
		double distanceRemove = f.getCost(city1Previous, city1) + f.getCost(city1, city1Next) + f.getCost(city2Previous, city2Next);
		double delta = distanceAdd - distanceRemove;
		return currentFitness + delta;
	}

	// delta evaluation optimised for Daviss Hill Climbing
	double deltaEvaluationDavissHill(TSPSolutionInterface solution, double currentFitness, int index1, int index2) {
		ArrayList<Integer> cities = solution.getSolutionRepresentation().getRepresentationOfSolution();
		int city1Previous = cities.get(previousIndex(index1, solution.getNumberOfCities()));
		int city1 = cities.get(index1);
		int city1Next = cities.get(nextIndex(index1, solution.getNumberOfCities()));
		int city2Previous = cities.get(previousIndex(index2, solution.getNumberOfCities()));
		int city2 = cities.get(index2);
		int city2Next = cities.get(nextIndex(index2, solution.getNumberOfCities()));

		double distanceAdd = f.getCost(city1Previous, city2) + f.getCost(city1, city2Next);
		double distanceRemove = f.getCost(city1Previous, city1) + f.getCost(city2, city2Next);
		if (index2 - index1 > 1) {
			distanceAdd += f.getCost(city2, city1Next) + f.getCost(city2Previous, city1);
			distanceRemove += f.getCost(city1, city1Next) + f.getCost(city2Previous, city2);
		}
		double delta = distanceAdd - distanceRemove;
		return currentFitness - delta;
	}

}