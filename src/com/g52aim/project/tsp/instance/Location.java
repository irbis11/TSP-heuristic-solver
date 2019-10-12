package com.g52aim.project.tsp.instance;

/**
 * 
 * @author Warren G. Jackson
 * 
 * Stores the X and Y co-ordinates of a city.
 * 
 * This class is already implemented; there is no need to change anything here.
 */
public class Location {

	private final double x;
	private final double y;

	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}