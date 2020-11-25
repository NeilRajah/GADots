/**
 * Dot
 * Author: Neil Balaskandarajah
 * Created on: 16/11/2020
 * Dot that moves around
 */

package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Dot extends Vec {
	//Constants
	public static final double STEP_SIZE = 15;	//Length of step the Dot can take
	private static final int RAD = 3;			//Default Dot radius

	//Attributes
	private int rad;							//Radius in pixels
	private Color color;						//RGB color
	private Vec vel;							//Velocity in pixels/s
	private Vec[] directions;					//Directions for the dot to move in
	private int stepsTaken;						//Number of steps the Dot has taken
	private boolean dead;						//Whether the Dot is dead or not
	private boolean atGoal;						//Whether the Dot is at the goal or not
	
	/**
	 * Create a Dot
	 * @param x Starting x position in pixels
	 * @param y Starting y position in pixels
	 * @param color Color of the Dot
	 * @param steps Steps the Dot can take before dying
	 */
	public Dot(int x, int y, Color color, int steps) {
		//set attributes
		super(x, y);
		this.color = color;
		this.vel = new Vec();
		
		//start alive and away from goal
		dead = false;
		atGoal = false;
		
		//initialize values
		this.rad = RAD;
		stepsTaken = 0;
		directions = new Vec[steps];
		fillDirections();
	}
	
	/**
	 * Create a default dot
	 */
	public Dot() {
		this(0, 0, Color.WHITE, 100);
	}
	
	/**
	 * Get the directions from the dot
	 * @return Array of directions the dot will take
	 */
	public Vec[] directions() {
		return directions;
	}
	
	/**
	 * Set the directions for the dot to take
	 * @param directions Directions to take during simulation
	 */
	public void setDirections(Vec[] directions) {
		this.directions = directions.clone();
	}
	
	/**
	 * Get the color of the Dot
	 * @return Color of the Dot
	 */
	public Color color() {
		return color;
	}
	
	/**
	 * Get the velocity of the Dot
	 * @return Velocity of the Dot
	 */
	public Vec vel() {
		return vel;
	}
	
	/**
	 * Get the radius of the Dot
	 * @return radius of the Dot
	 */
	public int rad() {
		return rad;
	}
	
	/**
	 * Set the radius of the Dot
	 * @param rad New radius
	 */
	public void setRad(int rad) {
		this.rad = rad;
	}
	
	/**
	 * Set the status of the dot
	 * @param dead True if dead, false if alive
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	/**
	 * Check if the Dot is dead
	 * @return True if dead
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Get the number of steps taken by the Dot
	 * @return Number of steps taken by the Dot
	 */
	public int stepsTaken() {
		return stepsTaken;
	}
	
	/**
	 * Set the goal status of the Dot
	 * @param atGoal True if at the Dot
	 */
	public void setAtGoal(boolean atGoal) {
		this.atGoal = atGoal;
	}
	
	/**
	 * Return whether the Dot is at the goal
	 * @return Whether the Dot is at the goal
	 */
	public boolean isAtGoal() {
		return atGoal;
	}
	
	/**
	 * Get the number of steps the Dot can take
	 * @return Number of steps the Dot can take
	 */
	public int numSteps() {
		return directions.length;
	}
	
	/**
	 * Fill the array of random directions for the Dot to take
	 */
	private void fillDirections() {
		for (int i = 0; i < directions.length; i++)
			directions[i] = randomVec(STEP_SIZE);
	}
	
	/**
	 * Set a direction in the directions array
	 * @param i Index of the direction to change
	 * @param v Vector to change the direction of
	 */
	public void setDirection(int i, Vec v) {
		directions[i] = v;
	}
	
	/**
	 * Create a random vector
	 * @param mag Desired magnitude of the Vector
	 * @return Random vector with magnitude mag
	 */
	public static Vec randomVec(double mag) {
		return Vec.fromAngle(Math.random() * 2 * Math.PI, mag);
	}
	
	/**
	 * Move the dot
	 */
	public void move() {
		// if not dead
		if (!dead) {
			
			// if the dot has more steps to take and is not at the goal
			if (stepsTaken < directions.length && !atGoal) {
				// take a step
				this.addX(directions[stepsTaken].x());
				this.addY(directions[stepsTaken].y());
				stepsTaken++;
				
			// else, it is dead
			} else {
				dead = true;
			}
		}
	}	
	
	/**
	 * Draw the Dot
	 * @param g2 Drawing object
	 */
	public void draw(Graphics2D g2) {
		// Red if dead, Green if at goal
		g2.setColor(dead ? Color.RED : atGoal ? Color.GREEN : this.color);
		g2.fillOval((int) (x() - rad), (int) (y() - rad), 2*rad, 2*rad);
	}
	
	/**
	 * Set the color of this Dot
	 * @param color New color of this Dot
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}