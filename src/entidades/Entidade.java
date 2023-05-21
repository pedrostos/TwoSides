package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;

public class Entidade {

		/*
		 Criação de imagens dos personagens e objetos.
		 Buffered Image é usado para representar a sequência de pixel armazenda na memória.
		 Através do spritesheet, será pego as coordenadas de pixels X, Y, altura e largura,
		 assim originando as imagens.
		*/

		// Pacote de vida.
		public static BufferedImage PacoteDeVida_Entidade = Game.spritesheet.getSprite(5*16, 0, 16, 16);
		// Arco.
		public static BufferedImage Arco_Entidade = Game.spritesheet.getSprite(6*16, 0, 16, 16);
		// Flecha.
		public static BufferedImage Flecha_Entidade = Game.spritesheet.getSprite(5*16, 16, 16, 16);
		// Inimigo.
		public static BufferedImage Inimigo_Entidade = Game.spritesheet.getSprite(6*16, 16, 16, 16);
		// Arco esquerda.
		public static BufferedImage ArcoParaEsquerda = Game.spritesheet.getSprite(112, 0, 16, 16);
		// Arco direita.
		public static BufferedImage ArcoParaDireita = Game.spritesheet.getSprite(96, 0, 16, 16);
		// Flecha esquerda.
		public static BufferedImage FlechaParaEsquerda = Game.spritesheet.getSprite(80, 32, 16, 16);
		// Flecha direita.
		public static BufferedImage FlechaParaDireta = Game.spritesheet.getSprite(80, 16, 16, 16);
		// Inimigo ao tomar dano.
		public static BufferedImage InimigoTomandoDano = Game.spritesheet.getSprite(16, 48, 16, 16);
		// Radu.
		public static BufferedImage Boss_Entidade = Game.spritesheet.getSprite(0, 96, 16, 16);
		// Radu ao tomar dano direita.
		public static BufferedImage BossTomandoHitDireita = Game.spritesheet.getSprite(32, 48, 16, 16);
		// Radu ao tomar dano esquerda.
		public static BufferedImage BossTomandoHitEsquerda = Game.spritesheet.getSprite(48, 48, 16, 16);
		// Anahí ao tomar dano direita.
		public static BufferedImage PlayerTomandoHitDireita = Game.spritesheet.getSprite(0, 48, 16, 16);
		// Anahí ao tomar dano esquerda.
		public static BufferedImage PlayerTomandoHitEsquerda = Game.spritesheet.getSprite(0, 80, 16, 16);
		// Luke ao tomar dano direita.
		public static BufferedImage ChefaoTomandoHitDireita = Game.spritesheet.getSprite(16, 80, 16, 16);
		// Luke ao tomar dano esquerda.
		public static BufferedImage ChefaoTomandoHitEsquerda = Game.spritesheet.getSprite(32, 80, 16, 16);
		// Luke desacordado.
		public static BufferedImage ChefaoMorto = Game.spritesheet.getSprite(144, 96, 16, 16);
		// Radu após conversa com Anahí.
		public static BufferedImage BossDoBem = Game.spritesheet.getSprite(128,128,16,16);


		public double x;
		public double y;
		protected int widht;
		protected int height;

		private BufferedImage sprite;
		// Hitbox dos personagens e inimigos.
		private int maskx,masky,mwidth,mheight;

		// Construtor da entidade.
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

		// Método para alterar o hitbox dos personagens e inimigos.
		public void setMask(int maskx,int masky,int mwidth,int mheight) {
			this.maskx = maskx;
			this.masky = masky;
			this.mwidth = mwidth;
			this.mheight = mheight;
		}

		// Método para alterar a coordenada de X.
		public void setX(int newX) {
			this.x = newX;
		}

		//Método para alterar a coordenada de Y.
		public void setY(int newY) {
			this.y = newY;
		}

		// Método para receber o valor da coordenada de X.
		public int getX() {
			return (int)this.x;
		}

		// Método para receber o valor da coordenada de Y.
		public int getY() {
			return (int)this.y;
		}

		// Método para receber o valor da coordenada da largura.
		public int getWidht() {
			return this.widht;
		}

		// Método para receber o valor da coordenada da altura.
		public int getHeight() {
			return this.height;
		}

		public void tick(){

		}

		// Método para definir se os personagens e inimigos estão colidindo com as estruturas,
		public static boolean isColidding(Entidade e1,Entidade e2){
			Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
			Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
			return e1Mask.intersects(e2Mask);
		}

		// Método para renderizar a imagem.
		public void render(Graphics g) {
			g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
}