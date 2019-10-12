package com.g52aim.project.tsp.interfaces;

import java.nio.file.Path;
import java.util.Random;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface TSPInstanceReaderInterface {

	/**
	 * 
	 * @param path The path where the TSP instance file is stored.
	 * @param random A seeded random number generator.
	 * @return An instance file describing the TSP instance.
	 */
	public TSPInstanceInterface readTSPInstance(Path path, Random random);
}
