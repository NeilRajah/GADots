/**
 * Environment
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Environment where simulation occurs
 */

package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Environment extends JComponent {
	//Attributes
	private int width, height;
	
	public Environment(int w, int h) {
		width = w;
		height = h;
		
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.fillRect(0, 0, width/2, height/2);
	}

}
