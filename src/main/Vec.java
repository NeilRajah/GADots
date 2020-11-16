/**
 * Vec
 * Author: Neil Balaskandarajah
 * Created on: 31/10/2020
 * 2D Vector with magnitude, direction
 */
package main;

public class Vec {
	//Attributes
	private double x;		//X coordinate of vector
	private double y;		//Y coordinate of vector
	private double mag;		//Magnitude of vector
	private double dir;		//Direction of vector
	
	/**
	 * Create a rectangular vector
	 * @param x X value of vector
	 * @param y Y value of vector
	 */
	public Vec(double x, double y) {
		this.x = x;
		this.y = y;
		calcMag();
		calcDir();
	}
	
	/**
	 * Create a blank vector
	 */
	public Vec() {
		this.x = 0;
		this.y = 0;
		calcMag();
		calcDir();
	}
	
//	/**
//	 * Create a polar vector
//	 * @param mag Magnitude of vector
//	 * @param dir Direction of vector (radians)
//	 */
//	public Vec(float mag, float dir) {
//		this.x = mag * Math.cos(dir);
//		this.y = mag * Math.sin(dir);
//		this.mag = mag;
//		this.dir = dir;
//	}
	
	//Getters
	
	/**
	 * Get the vector's x value
	 * @return Rectangular x value
	 */
	public double x() {
		return x;
	}
	
	/**
	 * Get the vector's y value
	 * @return Rectangular y value
	 */
	public double y() {
		return y;
	}
	
	/**
	 * Get the vector's magnitude
	 * @return Magnitude
	 */
	public double mag() {
		return mag;
	}
	
	/**
	 * Get the vector's direction
	 * @return Vector's direction in radians [-PI/2, PI/2]
	 */
	public double dir() {
		return dir;
	}
	
	/**
	 * Get the vector's direction
	 * @return Vector's direction in degrees [-180, 180]
	 */
	public double dirDeg() { 
		return Math.toDegrees(dir);
	}
	
	//Setters
	
	/**
	 * Set the vector's x value and update its magnitude, direction
	 * @param x New x value
	 */
	public void setX(double x) {
		this.x = x;
		calcMag();
		calcDir();
	}
	
	/**
	 * Set the vector's y value and update its magnitude, direction
	 * @param y New y value
	 */
	public void setY(double y) {
		this.y = y;
		calcMag();
		calcDir();
	}
	
	/**
	 * Set the vector's x, y values and update its magnitude, direction
	 * @param x New x value
	 * @param y New y value
	 */
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
		calcMag();
		calcDir();
	}
	
	/**
	 * Update the magnitude of the vector
	 */
	private void calcMag() {
		this.mag = Math.hypot(x, y);
	}
	
	/**
	 * Update the direction of the vector
	 */
	private void calcDir() {
		this.dir = Math.atan2(y, x);
	}
	
	/**
	 * Add onto the x value of the vector
	 * @param dx X increment
	 */
	public void addX(double dx) {
		this.x += dx;
	}
	
	/**
	 * Add onto the y value of the vector
	 * @param dy Y increment
	 */
	public void addY(double dy) {
		this.y += dy;
	}
	
	/**
	 * Scale the x and y values of the vector
	 * @param sx Scale factor for x
	 * @param sy Scale factor for y
	 */
	public void scale(double sx, double sy) {
		this.x *= sx;
		this.y *= sy;
	}
	
	//Operations
	//Addition, subtraction, dot, cross?, norm
	
	
	/**
	 * Return a scaled vector
	 * @param v Vector to scale
	 * @param s Linear scale factor
	 */
	public static Vec scale(Vec v, double s) {
		return new Vec(v.x() * s, v.y() * s);
	}
	
	/**
	 * Normalize v (retain its direction, change its magnitude to 1
	 * @param v Vector to normalize
	 * @return Normalized v
	 */
	public static Vec norm(Vec v) {
		return scale(v, 1.0 / v.mag());
	}
	
	/**
	 * Set the magnitude of v
	 * @param v Vector to change
	 * @param mag New vector magnitude
	 * @return Scaled v
	 */
	public static Vec setMag(Vec v, double mag) {
		return scale(v, mag / v.mag());
	}
	
	/**
	 * Limit the magnitude of v
	 * @param v Vector to limit
	 * @param maxMag Maximum magnitude for vector
	 * @return Capped vector
	 */
	public static Vec limitMag(Vec v, double maxMag) {
		return v.mag() > maxMag ? Vec.setMag(v, maxMag) : v;
	}
	
	/**
	 * Return the difference of two vectors
	 * @param v1 First vector
	 * @param v2 Second vector
	 * @return v2 - v1
	 */
	public static Vec diff(Vec v1, Vec v2) {
		return new Vec(v2.x() - v1.x(), v2.y() - v1.y());
	}
	
	/**
	 * Return the sum of two vectors
	 * @param v1 First vector
	 * @param v2 Second vector
	 * @return v2 + v1
	 */
	public static Vec sum(Vec v1, Vec v2) {
		return new Vec(v2.x() + v1.x(), v2.y() + v1.y());
	}
	
	/**
	 * Get the x-component as a vector
	 * @return (x,0)
	 */
	public Vec xVec() {
		return new Vec(x, 0);
	}
	
	/**
	 * Get the y-component as a vector
	 * @return (y,0)
	 */
	public Vec yVec() {
		return new Vec(0, y);
	}
	
	/**
	 * Return the String representation of the vector
	 * @return (x,y) of vector as string
	 */
	public String toString() {
		return (int) x +" "+ (int) y;
	}
}