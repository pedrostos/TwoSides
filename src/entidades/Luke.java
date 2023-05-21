package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import game.Sons;
import world.Camera;
import world.World;

public class Luke extends Entidade{
	
	private double speed = 1.1;
	private int maskx = -4, masky = 4, maskw = 16, maskh = 16;
	private int frames = 0;
	private int maxFrames = 4;
	private int index = 0;
	private int maxIndex = 2;
	private int vida = 30;
	private boolean estaTomandoDano = false;
	private int danoFrames = 10,danoAtual = 0;
	private BufferedImage[] rightLuke;
	private BufferedImage[] leftLuke;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = right_dir;
	private  boolean moved = false;

	// Construtor para a criação do Luke.
	public Luke(int x, int y, int width, int height, BufferedImage Luke) {
		super(x, y, width, height, null);
		rightLuke = new BufferedImage[4];
		leftLuke = new BufferedImage[4];

		// Laço de repetição que define qual imagem será exibida a partir do movimento do Luke.
		for (int i=0; i < 4; i++) {
			rightLuke[i] = Game.spritesheet.getSprite(32 + (i*16),112, 16, 16);
		}
			for (int i=0; i < 4; i++) {
			leftLuke[i] = Game.spritesheet.getSprite(80 - (i*16), 128, 16, 16);
		
	}
	}

	// Método para inicializar outros métodos.
	public void tick() {
		moved = false;
		if(isColiddingWithPlayer() == false) {
			/*
			Condição em que determina que se o Luke estiver colidindo com o personagem ou com as estruturas,
			o movimento será bloqueado, se não, o Luke se movimentará.
			*/
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& Game.levelAtual !=7 && !isColidding((int)(x+speed), this.getY())) {
			moved = true;
			direcao = right_dir;
			x += speed;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& Game.levelAtual !=7 && !isColidding((int)(x-speed), this.getY())) {
			moved = true;
			direcao = left_dir;
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& Game.levelAtual !=7 && !isColidding (this.getX() ,(int)(y+speed))) {
			y += speed;
		} else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& Game.levelAtual !=7 && !isColidding(this.getX(),(int)(y-speed))) {
			y -= speed;
		}
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index ++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		} else {
			/*
			Condição em que determina que, se o Luke estiver colidindo com o jogador,
			tocará um efeito sonoro e a cada colisão, a vida do personagem reduzirá 10 pontos
			de sua vida máxima.
			*/
			if(Game.rand.nextInt(100) < 10 && Game.levelAtual !=7) {
				Sons.hit.tocar();
				Sons.hit.setVolume(-20);
				
				Game.player.vida -=  10;
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

		// Condição em que determina que se a vida do Luke for menor ou igual que 0, ele chamará o método "seAutoDestrói".
		if(vida <=0 ) {
			seAutoDestroi();
			return;
		}

		// Condição em que determina se o Luke está tomando dano.
		if(estaTomandoDano) {
			this.danoAtual++;
			if(this.danoAtual == this.danoFrames) {
				this.danoAtual = 0;
				this.estaTomandoDano = false;
			}
		}
}

	// Método em que executa a retirada do Luke do array entidades e inimigo se ele for eliminado.
	public void seAutoDestroi () {
		Game.chefao.remove(this);
		Game.entidades.remove(this);
	}

	// Método em que se o Luke colidir com a flecha, perderá 3 pontos de sua vida máxima.
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

	// Método em que deternmina se o Luke está colidindo com o jogador.
	public boolean isColiddingWithPlayer () {
		Rectangle atualLuke = new Rectangle(this.getX() + maskx ,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return atualLuke.intersects(player);
	}

	// Método para definir se o Luke está colidindo com as estruturas.
	public boolean isColidding(int xnext, int ynext) {
		Rectangle atualLuke = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		
		for(int i = 0; i < Game.chefao.size(); i++ ) {
			Luke e = Game.chefao.get(i);
			if(e == this) {
				continue;
			}
			Rectangle destinoLuke = new Rectangle(e.getX() + maskx,e.getY() + masky , maskw , maskh);
			if (atualLuke.intersects(destinoLuke)) {
				return true;
			}
		}
		
		return false;
	}

	// Método que em que define qual imagem será exibida dependendo da condição.
	public void render(Graphics g) {
		if(!estaTomandoDano) {
			if(direcao == right_dir && Game.levelAtual ==7) {
				g.drawImage(Entidade.ChefaoMorto, this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
			}
		if(direcao == right_dir && Game.levelAtual !=7) {
			g.drawImage(rightLuke[index], this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
		}else if(direcao == left_dir && Game.levelAtual !=7) {
		g.drawImage(leftLuke[index], this.getX()+8  - Camera.x, this.getY() -1 - Camera.y, null);
		} else if (estaTomandoDano) {
			
		}
	}  else if(direcao == right_dir) {
		g.drawImage(Entidade.ChefaoTomandoHitDireita, this.getX() - Camera.x, this.getY() - Camera.y, null);
	} else
		g.drawImage(Entidade.ChefaoTomandoHitEsquerda, this.getX() - Camera.x, this.getY() - Camera.y, null);
}
}