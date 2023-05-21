package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;

		/*
		Construtor para criação de spritesheet, em que o programa tenta ler a imagem buscada, e se não conseguir, ocorrerá um erro.
		*/
		public Spritesheet (String path) {
			try {
				spritesheet = ImageIO.read(getClass().getResource(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Método que retorna uma parte da spritesheet, armazenada na memória pelo BufferedImage.
		public BufferedImage getSprite(int x , int y, int widht , int height) {
			return spritesheet.getSubimage(x , y , widht , height);
		}
}
