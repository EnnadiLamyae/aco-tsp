package org.aco.tsp.config;

public class Parameters {
	
	// Pheromone evaporation rate 
    public static double rho = 0.5;
    //Pheromone importance
    public static double alpha = 1.0;
   //Heuristic importance
    public static double beta = 2.0;
    //Size of ant population
    public static int antPopSize = 300;
    //Size of nearest neighbor list for each vertex
    public static int NNSize = 30;
    //Number of iterations to find a good solution
    public static int iterationsMax = 1000;
}
