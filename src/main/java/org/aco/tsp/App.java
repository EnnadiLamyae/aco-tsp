package org.aco.tsp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.aco.tsp.ant.Environment;
import org.aco.tsp.config.Parameters;
import org.aco.tsp.reader.FileReader;
import org.aco.tsp.statistics.Statistics;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	 String tspPath = (new File(".")).getCanonicalPath();
         tspPath = Paths.get(tspPath, "tsp").toAbsolutePath().toString();
         String tspFile = "lin60.tsp";
         
         App app = new App();
         System.out.println("\nProblem: " + tspFile);
         app.start(tspPath, tspFile);
         
    }
    public void start(String path, String file) {

        // Create a TSP instance from file with .tsp extension
        Environment environment = new Environment(FileReader.getDistances(path, file));
        Statistics statistics = new Statistics(file, environment, FileReader.getCoordinates(path, file));

        // Startup part
        environment.generateNearestNeighborList();
        environment.generateAntPopulation();
        environment.generateEnvironment();

        // Repeat the ants behavior by n times
        int n = 0;
        while(n < Parameters.iterationsMax) {
            environment.constructSolutions();
            environment.updatePheromone();
            statistics.calculateStatistics(n);
            n++;
        }
        System.out.println("Finished");
    }
}
