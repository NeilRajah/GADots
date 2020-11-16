/**
 * Window
 * Author: Neil Balaskandarajah
 * Created on: 15/11/2020
 * Graphical window where simulation occurs
 */

package main;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	//Attributes
	private int width, height;
	private JPanel panel;
	private Component env;
	
	public Window(int w, int h) {
		this.width = w;
		this.height = h;
		
		init();
	}
	
	private void init() {
		this.setSize(width, height);
		
		this.panel = new JPanel();
		this.env = new Environment(width, height);
		
		panel.add(env);
	}

	public void launch() {
		this.setTitle("GADots");
		this.setContentPane(panel);
		this.setUndecorated(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
//		Thread t = new Thread(() -> {
//			while (true) {
//				try {
//					Thread.sleep(Util.PAUSE); 
//				} catch (Exception e) {}
//			}
//		});
//		t.start();
	}
}
