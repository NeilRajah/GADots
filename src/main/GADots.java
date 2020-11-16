/**
 * GADots
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Genetically optimizing dot trajectories
 */

package main;

import java.awt.Toolkit;

public class GADots {
	
	public static void main(String[] args) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width/2;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height/2;
		Window w = new Window(width, height);
		w.launch();
	}

}
