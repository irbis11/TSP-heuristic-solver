package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Random;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class OX extends CrossoverHeuristicOperators implements XOHeuristicInterface {
	
	public OX(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		// invalid operation, return the same solution!
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2, TSPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		// setting initial variables
		int adjustedIOM = adjustParameter(1, 2, 3, 4, 5, 6, intensityOfMutation);
		ArrayList<Integer> parent1 = new ArrayList<>(p1.getSolutionRepresentation().getRepresentationOfSolution());
		ArrayList<Integer> parent2 = new ArrayList<>(p2.getSolutionRepresentation().getRepresentationOfSolution());
		int parentSize = parent1.size();

		for (int i = 0; i < adjustedIOM; i++) {
			// setting additional variables
			int[] parentCuts = createCutPoints(p1.getNumberOfCities());
			int cutLength = parentCuts[1] - parentCuts[0];
			ArrayList<Integer> child1 = new ArrayList<>(parentSize);
			child1 = initializeArrayListAsNulls(child1, parentSize);
			ArrayList<Integer> child2 = new ArrayList<>(parentSize);
			child2 = initializeArrayListAsNulls(child2, parentSize);

			// transfer cities between cut points of parent to its child unchanged
			int indexChild1 = parentCuts[0] - 1;
			int indexChild2 = indexChild1;
			for (int j = 0; j < cutLength; j++) {
				indexChild1 = nextIndex(indexChild1, parentSize);
				indexChild2 = nextIndex(indexChild2, parentSize);
				child1.set(indexChild1, parent1.get(indexChild1));
				child2.set(indexChild2, parent2.get(indexChild2));
			}

			// transfer rest cities from other parent in order they appear
			int indexParent = indexChild1;
			for (int j = 0; j < parentSize; j++) {
				indexParent = nextIndex(indexParent, parentSize);
				if (!child1.contains(parent2.get(indexParent))) {
					indexChild1 = nextIndex(indexChild1, parentSize);
					child1.set(indexChild1, parent2.get(indexParent));
				}
				if (!child2.contains(parent1.get(indexParent))) {
					indexChild2 = nextIndex(indexChild2, parentSize);
					child2.set(indexChild2, parent1.get(indexParent));
				}
			}
			// set new parents for next generation
			parent1 = new ArrayList<>(child1);
			parent2 = new ArrayList<>(child2);
		}
		// I did not implement delta for crossovers as usually reevaluating new solution will be faster than calculating all changes
		// choose child to return at random
		if (0.5 < random.nextDouble()) {
			c.setObjectiveFunctionValue(f.getObjectiveFunctionValue(new SolutionRepresentation(parent1)));
			c.getSolutionRepresentation().setRepresentationOfSolution(parent1);
		}
		else {
			c.setObjectiveFunctionValue(f.getObjectiveFunctionValue(new SolutionRepresentation(parent2)));
			c.getSolutionRepresentation().setRepresentationOfSolution(parent2);
		}
		return c.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return true;
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