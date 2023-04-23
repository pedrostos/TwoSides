package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entidade {
	
		protected int x;
		protected int y;
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
			return this.x;
		}
		public int getY() {
			return this.y;
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
			
			g.drawImage(sprite, this.getX(), this.getY(), null);
			
		}
		
}
