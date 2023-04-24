package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;
import world.World;




public class Player extends Entidade{
	
	public boolean right,up,down,left;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = 0;
	public double speed = 1.4;
	private int frames = 0;
	private int maxFrames =5;
	private int index = 0;
	private int maxIndex = 4;
	private  boolean moved = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public int flechas = 0; 
	public static double vida = 100 , maxVida = 100;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		rightPlayer = new BufferedImage[5];
		leftPlayer = new BufferedImage[5];
		
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
				if (index == maxIndex) {
					index = 0;
				}
			}
		}
		
		this.checkCollisionPacoteDeVida();
		this.checkCollisionFlechas();
		
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
					flechas += 3;
					Game.entidades.remove(atual);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(direcao == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(direcao == left_dir) {
		g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
