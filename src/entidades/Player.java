package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import game.Game;
import graficos.Spritesheet;
import world.Camera;
import world.World;




public class Player extends Entidade{
	
	public boolean right,up,down,left;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = right_dir;
	public double speed = 1.4;
	private int frames = 0;
	private int maxFrames =5;
	private int index = 0;
	private int maxIndex = 4;
	private  boolean moved = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage playerDamage;
	
	private boolean arco = false;
	private int damageFrames = 0;
	public int flechas = 0; 
	public  double vida = 100 , maxVida = 100;
	public boolean isDamaged = false;
	public boolean tiro = false;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		rightPlayer = new BufferedImage[5];
		leftPlayer = new BufferedImage[5];
		playerDamage = Game.spritesheet.getSprite(0, 48, 16, 16);
		
		for (int i=0; i < 5; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*16), 0, 16, 16);
		}
			for (int i=0; i < 5; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(64 - (i*16), 16, 16, 16);
		}
		
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x + speed),this.getY())) {
			moved = true;
			direcao = right_dir;
			x += speed;
		}else if (left && World.isFree((int)(x - speed),this.getY())) {
			moved = true;
			direcao = left_dir;
			x -= speed;
		} if (up && World.isFree(this.getX(),(int)(y - speed))) {
			moved = true;
			y -= speed;
		}else if (down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			y += speed;
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
		
		this.checkCollisionPacoteDeVida();
		this.checkCollisionFlechas();
		this.checkCollisionArco();
		
		// metodo para piscar ao tomar dano
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(tiro ) {
			//Criar flechas para atirar
			tiro = false;
			// verificar se tem o arco e flechas para atirar na direção certa
			if(arco && flechas > 0) {
			flechas --;
			int dx = 0;
			int px = 0;
			int py = 0;
			if(direcao == right_dir) {
				 px = 15;
				 dx = 1;
				 py = 8;
			} else {
				 py = 6;
				 px = 6;
				 dx = -1;
			}
			TiroDeFlecha flecha = new TiroDeFlecha(this.getX()  + px ,this.getY() + py ,3,3,null,dx,0);
			Game.flechas.add(flecha);
			}
		}
		
		// se a vida chegar a 0 o jogo acaba = game over
		if(vida <= 0) {
			vida = 0;
			Game.estadoDoJogo = "Game_Over";
		}
		
		// fazer a camera acompanhar o player e limitando até onde a camera pode ir
		// pega a posição do jogador e verifica quanto falta pra ele ir para o centro da tela 
		
		
		Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
	}

	
	public void checkCollisionPacoteDeVida() {
		for (int i=0; i < Game.entidades.size(); i++ ) {
			Entidade atual = Game.entidades.get(i);
			if(atual instanceof PacoteDeVida) {
				if(Entidade.isColidding(this, atual)) {
					vida+=10;
					if(vida > 100) {
						vida = 100;
					}
					Game.entidades.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionFlechas() {
		for (int i=0; i < Game.entidades.size(); i++ ) {
			Entidade atual = Game.entidades.get(i);
			if(atual instanceof Flechas) {
				if(Entidade.isColidding(this, atual)) {
					flechas += 10;
					Game.entidades.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionArco() {
		for (int i=0; i < Game.entidades.size(); i++ ) {
			Entidade atual = Game.entidades.get(i);
			if(atual instanceof Arco) {
				if(Entidade.isColidding(this, atual)) {
					arco = true;
					Game.entidades.remove(atual);
				}
			}
		}
	}
	

	public void render(Graphics g) {
		if(!isDamaged) {
		if(direcao == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(arco) {
			//arco para direita
			g.drawImage(Entidade.ArcoParaDireita, this.getX() +6 - Camera.x, this.getY() + 2 - Camera.y, null);
		}
		
		}else if(direcao == left_dir) {
		g.drawImage(leftPlayer[index], this.getX() +8 - Camera.x, this.getY() -1 - Camera.y, null);
		if(arco) {
			//arco para esquerda
			g.drawImage(Entidade.ArcoParaEsquerda, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
		
}
}
