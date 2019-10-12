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
public class TwoOpt extends HeuristicOperators implements HeuristicInterface {
	
	public TwoOpt(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {
		// setting initial variables
		int adjustedIOM = adjustParameter(3, 4, 5, 6, iom);
		ArrayList<Integer> tour = solution.getSolutionRepresentation().getRepresentationOfSolution();
		ArrayList<Integer> previousTour = new ArrayList<>(tour);
		double currentFitness = solution.getObjectiveFunctionValue();
		for (int i = 0; i < adjustedIOM; i++) {
			// find two random indexes
			int[] randomPair = sortedRandomNumberPair(solution.getNumberOfCities());
			int indexLower = randomPair[0];
			int indexHigher = randomPair[1];
			// run until indexes meet
			while (indexLower < indexHigher) {
				// swap according to indexes
				Collections.swap(tour, indexLower, indexHigher);
				indexLower++;
				indexHigher--;
			}
			// delta evaluation
			currentFitness = deltaEvaluationTwoOpt(solution, previousTour, currentFitness, randomPair[0], randomPair[1]);
			previousTour = new ArrayList<>(tour);
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