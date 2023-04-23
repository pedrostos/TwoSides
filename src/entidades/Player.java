package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;




public class Player extends Entidade{
	
	public boolean right,up,down,left;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = 0;
	public double speed = 1.2;
	private int frames = 0;
	private int maxFrames =5;
	private int index = 0;
	private int maxIndex = 4;
	private  boolean moved = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

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
		if(right) {
			moved = true;
			direcao = right_dir;
			x += speed;
		} else if (left) {
			moved = true;
			direcao = left_dir;
			x -= speed;
		} if (up) {
			moved = true;
			y -= speed;
		}else if (down) {
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
		// fazer a camera acompanhar o player
		// pega a posição do jogador e verifica quanto falta pra ele ir para o centro da tela
		Camera.x = this.getX() - (Game.WIDTH/2);
		Camera.y = this.getY() - (Game.HEIGHT/2);
	}
	
	public void render(Graphics g) {
		if(direcao == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(direcao == left_dir) {
		g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
