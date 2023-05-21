package game;

import java.io.*;
import javax.sound.sampled.*;

public class Sons {
	
	public static class Clips {
		public Clip[] clips;
		private int p;
		private int contador;
		private FloatControl controlarVolume;
		private int volume;

		// Construtor que recebe como parâmetro um aúdio externo, onde será transformado em bytes.
		public Clips (byte[] buffer, int contador) throws LineUnavailableException,IOException,UnsupportedAudioFileException{
			if(buffer == null) 
				return;
			clips = new Clip[contador];
			this.contador = contador;
			
			for(int i=0; i < contador; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
				controlarVolume = (FloatControl)clips[0].getControl(FloatControl.Type.MASTER_GAIN);
			}
		}

		// Método que reproduz o aúdio.
		public void tocar() {
			if ( clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if( p >= contador)
				p = 0;
		}

		// Método que torna o aúdio em um loop.
		public void loop() {
			if (clips == null)
				return;
			clips[p].loop(300);
		}

		// Método que altera o volume do aúdio.
		public void setVolume (int vol) {
			if(clips == null) 
				return;
			if (volume != vol) {
				volume = vol;
				controlarVolume.setValue(volume);
			}
		}
	}

	public static Clips musica = carregarClips("/music.wav",1);
	public static Clips hit = carregarClips("/tomandoDano.wav",1);

	private static Clips carregarClips (String name,int count) {
		try {
			ByteArrayOutputStream saidaDeByte = new  ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sons.class.getResourceAsStream(name));
			byte[] buffer = new byte[1024]; 
			int leitura = 0;
			
			while ((leitura = dis.read(buffer)) >= 0 ) {
				saidaDeByte.write(buffer,0,leitura);
			}
			dis.close(); 
			byte[] data = saidaDeByte.toByteArray();
			return new Clips(data,count);
		} catch (Exception e) {
			try {
				return new Clips (null,0);
			} catch (Exception ee ) {
				return null;
			}
		}
	}
}
