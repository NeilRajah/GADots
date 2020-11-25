/**
 * Population
 * Author: Neil Balaskandarajah
 * Created on: 16/11/2020
 * Collection of dots
 */

package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Population {
	//Constants
	private final double MUTATION_RATE = 0.01;		//How often the Dots mutate 
	
	//Attributes
	private Dot[] dots;								//Dots in the population
	private double width;							//Width of space Dots are in
	private double height;							//Height of space Dots are in
	private Dot goal;								//Goal the Dots are trying to reach
	private double[] fitnesses;						//Fitnesses of each Dot
	private double fitnessSum;						//Sum of fitnesses
	private double maxFitness;						//Highest fitness of all Dots
	private Vec start;								//Start point for Population
	private int gen;								//Current generation
	private int bestDotIndex;						//Index of the best Dot
	private int minSteps; 							//Steps taken by the best dot
	private ArrayList<Dot> obstacles;				//List of obstacles
	private boolean showChampOnly;					//Whether to draw just the Champion or not
	
	/**
	 * Create a Population
	 * @param size Number of Dots in the Population
	 * @param start Start point (x,y)
	 * @param steps Number of steps each Dot can take
	 * @param width Width of the space the Population is in
	 * @param height Height of the space the Population is in
	 * @param goal Goal the Dots are trying to reach
	 */
	public Population(int size, Vec start, int steps, int width, int height, Dot goal) {
		//Set attributes
		this.width = width;
		this.height = height;
		this.goal = goal;
		this.start = start;
		
		//Create the Dots
		dots = new Dot[size];
		for (int i = 0; i < dots.length; i++){
			dots[i] = new Dot((int) start.x(), (int) start.y(), Color.WHITE, steps);
		}
		
		//Set default values
		this.gen = 1;
		this.minSteps = 200;
		this.obstacles = new ArrayList<Dot>();
		this.showChampOnly = false;
		this.maxFitness = 0;
	}
	
	/**
	 * Get the number of steps the Dots can take
	 * @return Number of steps the Dots can take
	 */
	public int steps() {
		return dots[0].numSteps();
	}
	
	/**
	 * Add an obstacle
	 * @param d (x,y,rad) Obstacle
	 */
	public void addObstacle(Dot d) {
		obstacles.add(d);
	}
	
	/**
	 * Update and draw the Dots
	 * @param g2 Drawing object
	 */
	public void update(Graphics2D g2) {
		//Draw the goal and the obstacles
		goal.draw(g2);
		for (Dot o : obstacles)
			o.draw(g2);
		
		//loop through all the dots
		for (int i = 0; i < dots.length; i++) {
			Dot d = dots[i];
			
			//edge detection
			if (d.x() < d.rad()) {
				d.setX(d.rad()); 
				d.setDead(true);
			} else if (d.x() > width - d.rad()) {
				d.setX(width - d.rad());
				d.setDead(true);
			}
			
			if (d.y() < d.rad()) {
				d.setY(d.rad());
				d.setDead(true);
			} else if (d.y() > height - d.rad()){
				d.setY(height - d.rad());
				d.setDead(true);
			}
			
			//Kill dot if its worse than best dot
			if (d.stepsTaken() > minSteps)
				d.setDead(true);
			
			//Kill the dot if it hits an obstacle
			for (Dot obstacle : obstacles) {
				if (Vec.diff(d, obstacle).mag() < obstacle.rad()) {
					d.setDead(true);
					break;
				}
			}
			
			//Check if the dot is at the goal
			d.move();
			d.setAtGoal(withinGoal(d));
			
			//Augment the dot graphically if it is the champion
			if (i == 0) {
				d.setColor(Color.GREEN);
				d.setRad(6);
				d.draw(g2);
			}
			
			//Draw the dot
			if (!showChampOnly)
				d.draw(g2);
		}
	}
	
	/**
	 * Set whether only the Champion should be drawn or not
	 * @param val Whether only the Champion should be drawn or not
	 */
	public void setShowChampOnly(boolean val) {
		showChampOnly = val;
	}
	
	/**
	 * Get the generation number
	 * @return Generation umber
	 */
	public int gen() {
		return gen;
	}
	
	/**
	 * Check if a Dot is within the goal
	 * @param d Dot to check
	 * @return If the dot is in the goal or not
	 */
	private boolean withinGoal(Dot d) {
		return Vec.diff(d, goal).mag() < goal.rad();
	}
	
	/**
	 * Evaluate the current generation and create the next one
	 */
	public void nextGeneration() {
		calculateFitnessSum();
		getChampion();
		createNewPopulation();
		mutate();
		gen++;
	}
	
	/**
	 * Sum up all the fitness values and assign the fitnesses
	 */
	private void calculateFitnessSum() {
		fitnesses = new double[dots.length];
		fitnessSum = 0;
		for (int i = 0; i < dots.length; i++) {
			fitnesses[i] = calcFitness(dots[i]);
			fitnessSum += fitnesses[i];			
		}
	}
	
	/**
	 * Calculate the fitness of a Dot
	 * @param d Dot to evaluate
	 * @return Fitness of the Dot
	 */
	private double calcFitness(Dot d) {
		//Much higher fitness if the dot reached the goal
		if (d.isAtGoal())
			return 1.0/16.0 + 10000.0/(d.stepsTaken() * d.stepsTaken());
		
		//Higher fitness the closer the Dot is to the goal
		double dist = Vec.diff(d, goal).mag();
		return 1.0 / (dist * dist);
	}
	
	/**
	 * Determine the best dot and update the min steps taken
	 */
	private void getChampion() {
		maxFitness = 0;
		bestDotIndex = 0;
		for (int i = 0; i < fitnesses.length; i++) {
			if (fitnesses[i] > maxFitness) {
				maxFitness = fitnesses[i];
				bestDotIndex = i;
			}
		}
		
		//only update minSteps if the dot reached the goal
		if (dots[bestDotIndex].isAtGoal()) 
			minSteps = dots[bestDotIndex].stepsTaken();
	}
	
	/**
	 * Return the minimum number of steps taken by a Dot that has reached the goal
	 * @return minimum number of steps taken by a Dot that has reached the goal, 0 if none
	 */
	public int minSteps() {
		return minSteps;
	}
	
	/**
	 * Get the maximum fitness value of all the Dots
	 * @return Maximum fitness value of all the Dots
	 */
	public double maxFitness() {
		return maxFitness;
	}
	
	/**
	 * Create a new Population 
	 */
	private void createNewPopulation() {
		Dot[] newDots = new Dot[dots.length];
		
		//keep the champion
		newDots[0] = getChild(dots[bestDotIndex]);
		
		//get the rest
		for (int i = 1; i < newDots.length; i++) 
			newDots[i] = getChild(selectParent());
		
		dots = newDots.clone();
	}
	
	/**
	 * Select a parent for a new generation
	 * @return New parent
	 */
	private Dot selectParent() {
		//Choose a random fitness
		double randFitness = Math.random() * fitnessSum;
		double runningSum = 0;
		
		//This increases the probability of dots with higher fitnesses to be chosen
		for (int i = 0; i < fitnesses.length; i++) {
			runningSum += fitnesses[i];
			if (runningSum > randFitness)
				return dots[i];
		}
		
		//Never gets here
		return null;
	}
	
	/**
	 * Get a Dot's child
	 * @param d Dot to get child from
	 * @return The dot's child
	 */
	private Dot getChild(Dot d) {
		Dot baby = new Dot((int) start.x(), (int) start.y(), Color.WHITE, d.rad());
		baby.setDirections(d.directions());
		return baby;
	}
	
	/**
	 * Mutate the population
	 */
	private void mutate() {
		//Don't mutate the champion
		for (int i = 1; i < dots.length; i++) {
			Dot d = dots[i];
			
			//Mutate a percentage of all the steps
			for (int j = 0; j < d.numSteps(); j++) {
				if (Math.random() < MUTATION_RATE) 
					d.setDirection(j, Dot.randomVec(Dot.STEP_SIZE));
			}
		}
	}
	
	/**
	 * Check if current generation is finished
	 * @return If current generation is finished
	 */
	public boolean isGenFinished() {
		for (int i = 0; i < dots.length; i++) {
			//Generation is finished all Dots are dead or a Dot is at the goal
			if (!dots[i].isDead() && !dots[i].isAtGoal())
				return false;
		}
		return true;
	}
}