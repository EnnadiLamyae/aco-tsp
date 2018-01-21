package org.aco.tsp.statistics;

import org.aco.tsp.ant.Ant;
import org.aco.tsp.ant.Environment;
import org.aco.tsp.visualizer.Visualizer;

public class Statistics {

	 	private Environment environment;
	    public double getBestSoFar() {
			return bestSoFar;
		}

		public void setBestSoFar(double bestSoFar) {
			this.bestSoFar = bestSoFar;
		}

		public int[] getBestTourSoFar() {
			return bestTourSoFar;
		}

		public void setBestTourSoFar(int[] bestTourSoFar) {
			this.bestTourSoFar = bestTourSoFar;
		}
		private double bestSoFar = Double.MAX_VALUE;
	    private int[] bestTourSoFar;
	    private Visualizer visualizer;
	    private String tspFile;
	    
	    public Statistics(String tspFile, Environment environment, double[][] coordinates) {
	        this.environment = environment;
	        this.visualizer = new Visualizer(coordinates);
	        this.tspFile = tspFile;
	    }

	    public void calculateStatistics(int phase) {
	        double min = Double.MAX_VALUE;
	        double max = Double.MIN_VALUE;
	        double total = 0.0;
	        Ant bestAnt = null;
	        for(Ant ant : environment.getAnts()) {
	            if(ant.getTourCost() < min) {
	                min = ant.getTourCost();
	                bestAnt = ant;
	            }
	            if(ant.getTourCost() > max) {
	                max = ant.getTourCost();
	            }
	            total += ant.getTourCost();
	        }
	        if(min < bestSoFar) {
	            bestSoFar = min;
	            bestTourSoFar = bestAnt.getTour().clone();
	            String stats = String.format("%s -> Min(%.1f) Phase(%d) Max(%.1f) Mean(%.1f)\n", tspFile, min, phase, max, (total / environment.getAntPopSize()));
	            String message = "[" + bestTourSoFar[0];
	            for(int i = 1; i < bestTourSoFar.length - 1; i++) {
	                message += "->" + bestTourSoFar[i];
	            }
	            message += "]";
	            System.out.println(message);
	            visualizer.setStat(stats);
	            visualizer.draw(bestTourSoFar);
	            try { Thread.sleep(1000); } catch (Exception ex) {}
	        }
	    }
	    public void close() {
	        this.visualizer.dispose();
	    }
}
