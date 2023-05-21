package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import game.Sons;
import world.Camera;
import world.World;

// Classe para a criação do inimigo.
public class Inimigo extends Entidade{
	
	private double speed = 0.5;
	private int maskx = -4, masky = 4, maskw = 16, maskh = 16;
	private int frames = 0;
	private int maxFrames = 20;
	private int index = 0;
	private int maxIndex = 1;
	private int vida = 9;
	private boolean estaTomandoDano = false;
	private int danoFrames = 10,danoAtual = 0;
	private BufferedImage[] sprites;

	// Construtor para a criação do inimigo.
	public Inimigo(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		// Sprites recebe os pixels armazenado na memória.
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96+16, 16, 16, 16);
	}

	// Método para inicializar outros métodos.
	public void tick() {
		if(isColiddingWithPlayer() == false) {
			/*
			Condição em que determina que se o inimigo estiver colidindo com o personagem ou com as estruturas,
			o movimento será bloqueado, se não, o inimigo se movimentará.
			*/
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			x += speed;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColidding((int)(x-speed), this.getY())) {
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& !isColidding (this.getX() ,(int)(y+speed))) {
			y += speed;
		} else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			y -= speed;
		}
		} else {
			/*
			Condição em que determina que, se o inimigo estiver colidindo com o jogador,
			tocará um efeito sonoro e a cada colisão, a vida do personagem reduzirá 3 pontos
			de sua vida máxima.
			*/
			if(Game.rand.nextInt(100) < 10) {
				Sons.hit.tocar();
				Sons.hit.setVolume(-25);

				Game.player.vida -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}
		frames++;

		// Condição para determinar a velocidade em que a imagem será renderizada.
		if (frames == maxFrames) {
			frames = 0;
			index ++;
			if (index > maxIndex) 
				index = 0;
		}

		isColiddingFlecha();

		// Condição em que determina que se a vida do inimigo for menor ou igual que 0, ele chamará o método "seAutoDestrói".
		if(vida <=0 ) {
			seAutoDestroi();
			return;
		}
		// Condição em que determina se o inimigo está tomando dano.
		if(estaTomandoDano) {
			this.danoAtual++;
			if(this.danoAtual == this.danoFrames) {
				this.danoAtual = 0;
				this.estaTomandoDano = false;
			}
		}
}

	// Método em que executa a retirada do inimigo do array entidades e inimigo se ele for eliminado.
	public void seAutoDestroi () {
		Game.inimigos.remove(this);
		Game.entidades.remove(this);
	}

	// Método em que se o inimigo colidir com a flecha, perderá 3 pontos de sua vida máxima.
	public void isColiddingFlecha() {
		for (int i = 0; i < Game.flechas.size(); i++) {
			Entidade e = Game.flechas.get(i);
				if(Entidade.isColidding(this, e)) {
					estaTomandoDano = true;
					vida -= 3;
					Game.flechas.remove(i);
					return;
				}
			}
		}

		// Método em que deternmina se o inimigo está colidindo com o jogador.
	public boolean isColiddingWithPlayer () {
		Rectangle atualInimigo = new Rectangle(this.getX() + maskx ,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return atualInimigo.intersects(player);
	}

	// Método para definir se o inimigo está colidindo com as estruturas.
	public boolean isColidding(int xnext, int ynext) {
		Rectangle atualInimigo = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		
		for(int i = 0; i < Game.inimigos.size(); i++ ) {
			Inimigo e = Game.inimigos.get(i);
			if(e == this) {
				continue;
			}
			Rectangle destinoInimigo = new Rectangle(e.getX() + maskx,e.getY() + masky , maskw , maskh);
			if (atualInimigo.intersects(destinoInimigo)) {
				return true;
			}
		}
		
		return false;
	}

	// Método que em que define qual imagem será exibida dependendo da condição.
	public void render(Graphics g) {
		if(!estaTomandoDano) 
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		 else 
			g.drawImage(Entidade.InimigoTomandoDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}