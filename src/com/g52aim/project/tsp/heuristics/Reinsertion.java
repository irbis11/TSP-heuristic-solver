package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Random;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	public Reinsertion(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		// setting initial variables
		int adjustedIOM = adjustParameter(3, 4, 5, 6, intensityOfMutation);
		ArrayList<Integer> tour = solution.getSolutionRepresentation().getRepresentationOfSolution();
		ArrayList<Integer> previousTour = new ArrayList<>(tour);
		double currentFitness = solution.getObjectiveFunctionValue();
		for (int i = 0; i < adjustedIOM; i++) {
			// find two random indexes
			int[] randomPair = randomNumberPair(solution.getNumberOfCities());
			int indexFirst = randomPair[0];
			int indexSecond = randomPair[1];
			// remove from first index and insert in on second
			int temp = tour.remove(indexFirst);
			tour.add(indexSecond, temp);
			// delta evaluation
			currentFitness = deltaEvaluationReinsertion(solution, previousTour, currentFitness, randomPair[0], randomPair[1]);
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