package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;

public class Tile {

	// Construtor para criação das estruturas.
		public Tile (int x, int y,BufferedImage sprite) {
			this.x = x;
			this.y = y;
			this.sprite = sprite;
		}

		// Chão.
		public static BufferedImage Tile_Chao = Game.spritesheet.getSprite(0, 32, 16, 16);
		// Parede.
		public static BufferedImage Tile_Parede = Game.spritesheet.getSprite(16, 32, 16, 16);
		// Chão pegando fogo.
		public static BufferedImage Tile_ChaoQueimado = Game.spritesheet.getSprite(0, 64, 16, 16);
		// Parede pegando fogo.
		public static BufferedImage Tile_ParedeQueimada = Game.spritesheet.getSprite(16, 64, 16, 16);
		// Arbusto.
		public static BufferedImage Tile_Padrao = Game.spritesheet.getSprite(48, 64, 16, 16);
		
		private BufferedImage sprite;
		private int x,y;

		// Método que em que renderizará a imagem da respectiva estrutura.
		public void render(Graphics g) {
			g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		}
}
