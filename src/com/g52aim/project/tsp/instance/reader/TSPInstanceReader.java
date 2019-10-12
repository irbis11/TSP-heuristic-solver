package com.g52aim.project.tsp.instance.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.TSPInstance;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceReaderInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class TSPInstanceReader implements TSPInstanceReaderInterface {

	// file reader
	@Override
	public TSPInstanceInterface readTSPInstance(Path path, Random random) {
		int cities = -1;
		Location[] locations = null;
		try(BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {
			String line = br.readLine();
			while (line != null && !line.contains("NODE_COORD_SECTION")) {
				if(line.contains("DIMENSION : ")) {
					String dim = "DIMENSION : ";
					cities = Integer.parseInt(line.substring(dim.length()));
					locations = new Location[cities];
				}
				line = br.readLine();
			}

			for (int i = 0; i < cities; i++) {
				line = br.readLine();
				if (line != null) {
					String s[] = line.split(" ");
					if(s.length == 1) {
						s = line.split("\t");
					}
					locations[i] = new Location(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new TSPInstance(cities, locations, random);
	}

}