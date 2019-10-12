package com.g52aim.project.tsp;

import java.util.List;
import java.util.stream.Collectors;
import com.g52aim.project.tsp.instance.Location;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class SolutionPrinter {

	public SolutionPrinter() { }
	
	/**
	 * 
	 * @param routeLocations The array of Locations ordered in route order.
	 */
	public static void printSolution(List<Location> routeLocations) {
		String route = routeLocations.stream().map( l -> "( " + l.getX() + ", " + l.getY() + " )" ).collect(Collectors.joining(" -> "));
		System.out.println(route);
	}

}