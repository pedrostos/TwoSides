package game;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sons {
	
	private AudioClip clip;
	public static final Sons musicaDeFundo = new Sons("/music.wav");
	public static final Sons somDoHit = new Sons("/tomandoDano.wav");
	
	private Sons (String nome) {
		try {
			clip = Applet.newAudioClip(Sons.class.getResource(nome));
		} catch (Throwable e) {
			
		}
	}
	
	public void play() {
		try {
			new Thread () {
				public void run () {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			
		}
	}
	
	public void loop() {
		try {
			new Thread () {
				public void run () {
					clip.loop();
				}
			}.start();
		} catch (Throwable e) {
			
		}
	}


}
