package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;

public class Tile {
	
		public Tile (int x, int y,BufferedImage sprite) {
			this.x = x;
			this.y = y;
			this.sprite = sprite;
		}
	
		public static BufferedImage Tile_Chao = Game.spritesheet.getSprite(0, 32, 16, 16);
		public static BufferedImage Tile_Parede = Game.spritesheet.getSprite(16, 32, 16, 16);
		
		private BufferedImage sprite;
		private int x,y;
		
		public void render(Graphics g) {
			g.drawImage(sprite, x, y, null);
		}

}
