/**
 * Environment
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Environment where simulation occurs
 */

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Environment extends JComponent {
	//Attributes
	private int width;							//Width in pixels
	private int height;							//Height in pixels
	private Population pop;						//Population to control
	private int minPossibleSteps;				//Minimum possible steps to reach goal
	private double goalAccuracy;				//Goal accuracy to reach
	private int goalGen;						//Goal generation number to run to
	private double accuracy;					//How accurate the evolved solution is
	private ArrayList<Object[]> textItems;		//Text items to print out
	
	/**
	 * Create an Environment
	 * @param w Width in pixels
	 * @param h Height in pixels
	 */
	public Environment(int w, int h) {
		//Set attributes
		width = w;
		height = h;
		this.setPreferredSize(new Dimension(width, height));
		
		//Reset values
		goalAccuracy = 0;
		accuracy = 0;
	}
	
	/**
	 * Initialized the list of formatted text items to print
	 */
	private void initFormattedText() {
		textItems = new ArrayList<Object[]>();
		textItems.add(new Object[] {"accuracy: %.2f%%", accuracy*100.0});
		textItems.add(new Object[] {"maxFitness: %.6f", pop.maxFitness()});
		textItems.add(new Object[] {"minSteps: %.2f", (double) pop.minSteps()});
	}
	
	/**
	 * Set the Population to simulate
	 * @param p Population to simulate
	 */
	public void setPopulation(Population p) {
		pop = p;
		initFormattedText();
	}
	
	/**
	 * Get the number of steps in the Population
	 * @return Number of steps in the Population
	 */
	public int populationNumSteps() {
		return pop.steps();
	}
	
	/**
	 * Run the simulation until a goal accuracy
	 * @param acc Accuracy in percentage (0-100)
	 */
	public void runUntilAccuracy(double acc) {
		this.goalAccuracy = acc;
	}
	
	/**
	 * Run the simulation until a generation number
	 * @param gen Number of generations to simulate
	 */
	public void runUntilGen(int gen) {
		this.goalGen = gen;
	}
	
	/**
	 * Set the minimum possible number of steps to determine how accurate the solution is
	 * @param minPossibleSteps Minimum number of possible steps
	 */
	public void setMinPossibleSteps(int minPossibleSteps) {
		this.minPossibleSteps = minPossibleSteps;
	}
	
	/**
	 * Update the simulation
	 */
	public void update() {		
		//if there is a goal accuracy to run until
		if (goalAccuracy != 0) {
			while (accuracy < goalAccuracy)
				simulate();
		
		//generation to run simulation until
		} else if (goalGen != 0) {
			while (pop.gen() <= goalGen)
				simulate();
			
		//running indefinitely
		} else {
			simulate();
		}
	}
	
	/**
	 * Simulate the population
	 */
	private void simulate() {
		//calculate the accuracy
		accuracy = pop.minSteps() == pop.steps() ? 0 : 
					((double) minPossibleSteps / pop.minSteps());
		
		//if the population is not finished
		if (!pop.isGenFinished()) {
			repaint();
			
		//if it is, start the next one
		} else {
			pop.nextGeneration();
		}
	}
	
	/**
	 * Paint the Environment
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		//Fill the background
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, width, height);
		
		//Draw the text to the screen
		int x = (int) (width * 0.01);
		textToScreen(g2, x);
		
		//Scale to population coordinates and update the population
		g2.scale(1.0, -1.0);
		g2.translate(0, -height);
		pop.update(g2);
	}
	
	/**
	 * Draw the text items to the screen
	 * @param g2 Drawing object
	 * @param x X position of text
	 */
	private void textToScreen(Graphics2D g2, int x) {
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(40f));
		g2.drawString(Integer.toString(pop.gen()), x, (int) (height * 0.05));
		
		g2.setFont(g2.getFont().deriveFont(20f));
		textItems.clear();
		textItems.add(new Object[] {"accuracy: %.2f%%", accuracy*100.0});
		textItems.add(new Object[] {"maxFitness: %.6f", pop.maxFitness()});
		textItems.add(new Object[] {"minSteps: %.2f", (double) pop.minSteps()});
		
		//Draw items in the list
		double step = 0.025;
		double s = 0.1;  //start
		for (int i = 0; i < textItems.size(); i++) {
			s += step;
			g2.drawString(String.format((String) textItems.get(i)[0], (Double) textItems.get(i)[1]), 
							x, (int) (height * s));
		}
	}	
}