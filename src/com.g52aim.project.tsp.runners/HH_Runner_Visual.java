package com.g52aim.project.tsp.runners;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.SolutionPrinter;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.solution.TSPSolution;
import com.g52aim.project.tsp.visualiser.TSPView;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Warren G. Jackson
 *
 * Runs a hyper-heuristic using a default configuration then displays the best solution found.
 */
public abstract class HH_Runner_Visual {

	public HH_Runner_Visual() { }
	
	public void run() {
		Random random = new Random();
		long seed = random.nextLong();
		long timeLimit = 5000;
		ProblemDomain problem = new G52AIMTSP(seed);
		problem.loadInstance(4);
		HyperHeuristic hh = getHyperHeuristic(seed);
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(problem);
		hh.run();
		
		System.out.println("f(s_best) = " + hh.getBestSolutionValue());
		Location[] route = transformSolution((TSPSolution)((G52AIMTSP)problem).getBestSolution(), (G52AIMTSP)problem);
		SolutionPrinter.printSolution(Arrays.asList(route));
		new TSPView(route, Color.RED, Color.GREEN);
	}
	
	/** 
	 * Transforms the best solution found, represented as a TSPSolution, into an ordering of Location's
	 * which the visualiser tool uses to draw the tour.
	 */
	protected Location[] transformSolution(TSPSolution solution, G52AIMTSP problem) {
		return problem.getLocations(solution);
	}
	
	/**
	 * Allows a general visualiser runner by making the HyperHeuristic abstract.
	 * You can sub-class this class to run any hyper-heuristic that you want.
	 */
	protected abstract HyperHeuristic getHyperHeuristic(long seed);

}