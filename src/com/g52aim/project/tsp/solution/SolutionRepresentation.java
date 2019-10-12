package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import java.util.ArrayList;

/**
 * 
 * @author Warren G. Jackson
 *
 * For example, boolean[] can be used to represent the binary string for MAX-SAT problems.
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface<ArrayList<Integer>> {

	private ArrayList<Integer> tour;

	@Override
	public ArrayList<Integer> getRepresentationOfSolution() {
		return tour;
	}

	@Override
	public void setRepresentationOfSolution(ArrayList<Integer> solutionRepresentation) {
		tour = solutionRepresentation;
	}

	@Override
	public int getNumberOfCities() {
		return tour.size();
	}

	public SolutionRepresentation(ArrayList<Integer> solutionRepresentation){
		tour = solutionRepresentation;
	}

	public SolutionRepresentationInterface<ArrayList<Integer>> clone() {
		ArrayList<Integer> temp = new ArrayList<>(tour);
		return new SolutionRepresentation(temp);
	}

}