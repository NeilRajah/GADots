/**
 * Util
 * Author: Neil Balaskandarajah
 * Created on: 13/11/2020
 * Utility functions
 */

package main;

public class Util {	
	private static final int FPS = 60;
	public static final long PAUSE = (long) (1000.0 / FPS);
	
	/**
	 * Returns a random integer between lo and hi
	 * @param lo Min value
	 * @param hi Max value
	 * @return Random integer in [lo, hi]
	 */
	public static int randomInt(int lo, int hi) {
		return (int) Math.floor(Math.random() * (hi - lo)) + lo;
	}
	
	/**
	 * Returns a random integer from 0 to n
	 * @param n Max bound
	 * @return Random integer in [0, n]
	 */
	public static int randomInt(int n) {
		return randomInt(0, n);
	}
	
	
	/**
	 * Check whether two values are within tolerance of one another
	 * @param a First value
	 * @param b Second value
	 * @param eps Tolerance value to be within
	 * @return Whether a and b are within eps
	 */
	public static boolean fuzzyEquals(double a, double b, double eps) {
		return Math.abs(a - b) <= eps;			
	}
}