package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Tone {
    //
   protected static final int SAMPLE_RATE = 16 * 1024;


   public static byte[] createSinWaveBuffer(double freq, double n) {
	   double waveLen = 1.0/freq;
	   int samples = (int) Math.round(waveLen * n * SAMPLE_RATE);
//	   System.out.println(samples);
	   byte[] output = new byte[samples];
	   double period = SAMPLE_RATE / freq;
	   for (int i = 0; i < output.length; i++) {
	       double angle = 2.0 * Math.PI * i / period;
	       output[i] = (byte)(Math.sin(angle) * 127f);  
	   }

	   return output;
	}



   public static void main(String[] args) throws LineUnavailableException {
       final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
       SourceDataLine line = AudioSystem.getSourceDataLine(af);
       line.open(af, SAMPLE_RATE);
       line.start();

       boolean forwardNotBack = true;

       for(double freq = 300; freq <= 1000;)  {
           byte [] toneBuffer = createSinWaveBuffer(freq, 5);
           long init = System.nanoTime();
           line.write(toneBuffer, 0, toneBuffer.length);
           System.out.println((System.nanoTime()-init) * 1E-9);
           
           if(forwardNotBack)  {
               freq += 20;  
               forwardNotBack = false;  }
           else  {
               freq -= 10;
               forwardNotBack = true;  
       }   }

       line.drain();
       line.close();
    }

}