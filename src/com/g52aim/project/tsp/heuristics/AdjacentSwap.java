package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	public AdjacentSwap(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		// setting initial variables
		int adjustedIOM = adjustParameter(4, 8, 16, 32, intensityOfMutation);
		ArrayList<Integer> tour = solution.getSolutionRepresentation().getRepresentationOfSolution();
		double currentFitness = solution.getObjectiveFunctionValue();
		for (int i = 0; i < adjustedIOM; i++) {
			// find two random consecutive indexes
			int firstIndex = random.nextInt(solution.getNumberOfCities());
			int[] pair = nextNumberPair(firstIndex, solution.getNumberOfCities());
			// swap according to indexes
			Collections.swap(tour, pair[0], pair[1]);
			// delta evaluation
			currentFitness = deltaEvaluationAdjacentSwap(solution, currentFitness, pair[0], pair[1]);
		}
		solution.setObjectiveFunctionValue(currentFitness);
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

}