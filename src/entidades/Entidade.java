package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;

public class Entidade {
	
		public static BufferedImage PacoteDeVida_Entidade = Game.spritesheet.getSprite(5*16, 0, 16, 16);
		public static BufferedImage Arco_Entidade = Game.spritesheet.getSprite(6*16, 0, 16, 16);
		public static BufferedImage Flecha_Entidade = Game.spritesheet.getSprite(5*16, 16, 16, 16);
		public static BufferedImage Inimigo_Entidade = Game.spritesheet.getSprite(6*16, 16, 16, 16);
		public static BufferedImage ArcoParaEsquerda = Game.spritesheet.getSprite(112, 0, 16, 16);
		public static BufferedImage ArcoParaDireita = Game.spritesheet.getSprite(96, 0, 16, 16);
		public static BufferedImage FlechaParaEsquerda = Game.spritesheet.getSprite(80, 16, 16, 16);
		public static BufferedImage FlechaParaDireta = Game.spritesheet.getSprite(80, 32, 16, 16);
		public static BufferedImage InimigoTomandoDano = Game.spritesheet.getSprite(16, 48, 16, 16);
		
	
		protected double x;
		protected double y;
		protected int widht;
		protected int height;
		
		private BufferedImage sprite;
		private int maskx,masky,mwidth,mheight;
		
		public Entidade (int x, int y ,int width, int height,BufferedImage sprite) {
			this.x = x;
			this.y = y;
			this.widht = width;
			this.height = height;
			this.sprite = sprite;
			
			this.maskx = 0;
			this.masky = 0;
			this.mwidth = width;
			this.mheight = height;
		}
		
		public void setMask(int maskx,int masky,int mwidth,int mheight) {
			this.maskx = maskx;
			this.masky = masky;
			this.mwidth = mwidth;
			this.mheight = mheight;
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
		
		public static boolean isColidding(Entidade e1,Entidade e2){
			Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
			Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
			
			return e1Mask.intersects(e2Mask);
		}
		
		public void render(Graphics g) {
			
			g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		
}
