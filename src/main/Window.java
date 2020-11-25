/**
 * Window
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Graphical window where simulation occurs
 */

package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	//Attributes
	private int width;				//Width in pixels
	private int height;				//Height in pixels
	private JPanel panel;			//Panel holding components
	private Environment env;		//Environment population is in
	
	/**
	 * Create a Window
	 * @param w Window width in pixels
	 * @param h Window height in pixels
	 */
	public Window(int w, int h) {
		this.width = w;
		this.height = h;
		this.setSize(width, height);
		this.panel = new JPanel();	
	}
	
	/**
	 * Set the Environment for this Window
	 * @param env Environment for this Window
	 */
	public void setEnvironment(Environment env) {
		this.env = env;
		panel.add(env);
	}
	
	/**
	 * Set the Population to the Environment
	 * @param p
	 */
	public void setPopulation(Population p) {
		env.setPopulation(p);
	}

	/**
	 * Launch the Window
	 */
	public void launch() {
		//Set up the JFrame
		this.setBounds((int) (width * 0.9), 0, width, height);
		this.setTitle("GADots");
		this.setContentPane(panel);
		this.setUndecorated(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		//Main Thread to run the program
		new Thread(() -> {
			while(true) {
				env.update();
				try {
					Thread.sleep(Util.PAUSE);
				} catch (Exception e) {}
			}
		}).start();
	}
}