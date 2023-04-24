package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;

public class Entidade {
	
		public static BufferedImage PacoteDeVida_Entidade = Game.spritesheet.getSprite(5*16, 0, 16, 16);
		public static BufferedImage Arco_Entidade = Game.spritesheet.getSprite(6*16, 0, 16, 16);
		public static BufferedImage Flecha_Entidade = Game.spritesheet.getSprite(5*16, 16, 16, 16);
		public static BufferedImage Inimigo_Entidade = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	
		protected double x;
		protected double y;
		protected int widht;
		protected int height;
		
		private BufferedImage sprite;
		
		
		public Entidade (int x, int y ,int width, int height,BufferedImage sprite) {
			this.x = x;
			this.y = y;
			this.widht = width;
			this.height = height;
			this.sprite = sprite;
		}
		
		public void setX(int newX) {
			this.x = newX;
		}
		
		public void setY(int newY) {
			this.y = newY;
		}

		public int getX() {
			return (int)this.x;
		}
		public int getY() {
			return (int)this.y;
		}
		public int getWidht() {
			return this.widht;
		}
		public int getHeight() {
			return this.height;
		}
		
		public void tick() {
			
		}
		
		public void render(Graphics g) {
			
			g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		
}
