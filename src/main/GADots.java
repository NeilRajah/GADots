/**
 * GADots
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Genetically optimizing dot trajectories
 */

package main;

import java.awt.Color;
import java.awt.Toolkit;

public class GADots {
	
	/**
	 * Create the environment and run the simulation
	 */
	public static void main(String[] args) {
		// size of the window is half the screen width
		int side = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.5);
		
		// create the window and the environment
		Window w = new Window(side, side);
		Environment env = new Environment(side, side);
		
		//goal for the dots to get to
		Dot goal = new Dot((int) (side * 0.5), (int) (side * 0.8), Color.BLUE, 1);
		goal.setRad(10);
		
		//start point
		Vec start = new Vec(side/2, 100);
		
		//Create the population
		int populationSize = 1000;
		int stepsPerGen = 200;
		Population p1 = new Population(populationSize, start, stepsPerGen, side, side, goal);
		env.setPopulation(p1);
		
		//The minimum possible steps can be determined geometrically 
		int minPossibleSteps = (int) Math.ceil(Vec.diff(goal, start).mag() / Dot.STEP_SIZE);
		env.setMinPossibleSteps(minPossibleSteps);
		
		//Change end parameters (run until certain generation number, a certain accuracy if minPossibleSteps
		//is known, etc.)
		env.runUntilGen(100);
//		env.runUntilAccuracy(0.9);
		
		//Show the champion only
		p1.setShowChampOnly(true);
		
		//Second population with obstacles
		/* 
		Population p2 = new Population(1000, start, 400, side, side, goal);		
		int obstRad = 30;
		for (int i = 0; i < 15; i++) {
			int x = (int) ((side - obstRad) * Math.random() + obstRad);
			int y = (int) ((goal.y() - 200) * Math.random() + 200);
			Dot obst = new Dot(x, y, Color.GRAY, 1);
			obst.setRad(obstRad);
			p2.addObstacle(obst);
		}
		p2.showChampOnly(false);
		env.setPopulation(p2);
		*/
		
		// Set the environment to the window and launch it
		w.setEnvironment(env);
		w.launch();
	}
}
