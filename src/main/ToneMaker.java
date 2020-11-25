/**
 * ToneMaker
 * Author: Neil Balaskandarajah
 * Created on: 24/11/2020
 * Creates a Tone
 */

package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class ToneMaker {
	//Constants
	private final int SAMPLE_RATE = 16384;
	private final int SAMPLE_SIZE = 8;
	private final double TWO_PI = Math.PI * 2;
	
	//Attributes
	private static SourceDataLine line;
	
	public ToneMaker() {
		try {
			AudioFormat af = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, 1, true, true);
			line = AudioSystem.getSourceDataLine(af);
			line.open(af, SAMPLE_RATE);
			line.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] createSinWaveBuffer(double freq) {
		return null;
	}
	
	public void playTone(double exp, double time) {
		byte[] sin = new byte[(int) (time * SAMPLE_RATE)];
		double f = 440d * Math.pow(2d, exp);
		
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < sin.length; i++) {
			double period = (double) SAMPLE_RATE / f;
			double angle = TWO_PI * i / period;
			sin[i] = (byte) (Math.sin(angle) * 127f);
		}
		int count = line.write(sin, 0, (int) (SAMPLE_RATE * time));
//		line.drain();
	}
	
	public static void main(String[] args) {
		ToneMaker tm = new ToneMaker();
		
		double lo = 0.1;
		double hi = 0.5;
		double range = hi - lo;
		double numSteps = 1000;
		for (int i = 0; i < numSteps; i++) {
			double exp = range * i / 1000;
//			exp = 0.27;
			tm.playTone(exp, 0.25);
			System.out.println(exp);
		}
	}	
}