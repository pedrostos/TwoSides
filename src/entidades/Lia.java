package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;
import world.World;

public class Lia extends Entidade{
	
	private double speed = 1.3;
	private int maskx = 7, masky = 4, maskw = 20, maskh = 20;
	private int frames = 0;
	private int maxFrames = 4;
	private int index = 0;
	private int maxIndex = 2;
	private boolean estaTomandoDano = false;
	private BufferedImage[] jumpBoss;
	private BufferedImage[] rightBoss;
	private BufferedImage[] leftBoss;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = right_dir;



	public Lia(int x, int y, int width, int height, BufferedImage boss_Entidade) {
		super(x, y, width, height, null);
		jumpBoss = new BufferedImage[5];
		rightBoss = new BufferedImage[4];
		leftBoss = new BufferedImage[4];
		
		for (int i=0; i < 4; i++) {
			rightBoss[i] = Game.spritesheet.getSprite(0 + (i*16),144, 16, 16);
		}
			for (int i=0; i < 4; i++) {
			leftBoss[i] = Game.spritesheet.getSprite(96 - (i*16), 144, 16, 16);
			}
			for (int i=0; i < 5; i++) {
				jumpBoss[i] = Game.spritesheet.getSprite(80 + (i*16),64, 16, 16);
			}
		
	}
	

	public void tick() {
		if(isColiddingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			direcao = right_dir;
			x += speed;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColidding ((int)(x-speed), this.getY())) {
			direcao = left_dir;
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& !isColidding (this.getX() ,(int)(y+speed))) {
			y += speed;
		} else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			y -= speed;
		}

	}  
	
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index ++;
			if (index > maxIndex) 
				index = 0;
		}
		
	}
	
	public boolean isColiddingWithPlayer () {
		Rectangle atualLia = new Rectangle(this.getX() + maskx ,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return atualLia.intersects(player);
	}
		
	public boolean isColidding(int xnext, int ynext) {
		Rectangle atualInimigo = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		
		for(int i = 0; i < Game.lia.size(); i++ ) {
			Lia e = Game.lia.get(i);
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
	
	public void render(Graphics g) {
		if(!estaTomandoDano) {
		if(direcao == right_dir && Game.levelAtual != 7) {
			g.drawImage(rightBoss[index], this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
			if(direcao == right_dir && Game.levelAtual == 7) {
				g.drawImage(jumpBoss[index], this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
			}
		}else if(direcao == left_dir) {
		g.drawImage(leftBoss[index], this.getX()+8  - Camera.x, this.getY() -1 - Camera.y, null);	
		} 	
	}  
}
}
