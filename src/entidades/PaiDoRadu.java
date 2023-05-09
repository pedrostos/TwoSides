package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import game.Sons;
import world.Camera;
import world.World;

public class PaiDoRadu extends Entidade{
	
	private double speed = 1.1;
	private int maskx = -4, masky = 4, maskw = 16, maskh = 16;
	private int frames = 0;
	private int maxFrames = 4;
	private int index = 0;
	private int maxIndex = 2;
	private int vida = 1;
	private boolean estaTomandoDano = false;
	private int danoFrames = 10,danoAtual = 0;
	private BufferedImage Bosstomandohitdireita;
	private BufferedImage Bosstomandohitesquerda;
	private BufferedImage[] rightBoss;
	private BufferedImage[] leftBoss;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = right_dir;
	private  boolean moved = false;


	public PaiDoRadu(int x, int y, int width, int height, BufferedImage boss_Entidade) {
		super(x, y, width, height, null);
		rightBoss = new BufferedImage[4];
		leftBoss = new BufferedImage[4];
		//playerDamage = Game.spritesheet.getSprite(0, 48, 16, 16);
		
		for (int i=0; i < 4; i++) {
			rightBoss[i] = Game.spritesheet.getSprite(32 + (i*16),112, 16, 16);
		}
			for (int i=0; i < 4; i++) {
			leftBoss[i] = Game.spritesheet.getSprite(80 - (i*16), 128, 16, 16);
		
	}
	}

	public void tick() {
		moved = false;
		if(isColiddingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& Game.levelAtual !=6 && !isColidding((int)(x+speed), this.getY())) {
			moved = true;
			direcao = right_dir;
			x += speed;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& Game.levelAtual !=6 && !isColidding((int)(x-speed), this.getY())) {
			moved = true;
			direcao = left_dir;
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& Game.levelAtual !=6 && !isColidding (this.getX() ,(int)(y+speed))) {
			y += speed;
		} else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& Game.levelAtual !=6 && !isColidding(this.getX(),(int)(y-speed))) {
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
			// estamos colidindo
			if(Game.rand.nextInt(100) < 10 && Game.levelAtual !=6) {
				// adicionando som ao tomar hit
				Sons.hit.tocar();
				Sons.hit.setVolume(-20);
				
				Game.player.vida -=  10; //Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
			
		}
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index ++;
			if (index > maxIndex) 
				index = 0;
		}
		
		isColiddingFlecha();
		if(vida <=0 ) {
			seAutoDestroi();
			return;
		}
		if(estaTomandoDano) {
			this.danoAtual++;
			if(this.danoAtual == this.danoFrames) {
				this.danoAtual = 0;
				this.estaTomandoDano = false;
			}
		}
		
		

}
	
	
	public void seAutoDestroi () {
		Game.boss.remove(this);
		Game.entidades.remove(this);
	}
	
	public void isColiddingFlecha() {
		for (int i = 0; i < Game.flechas.size(); i++) {
			Entidade e = Game.flechas.get(i);
			//if (e instanceof TiroDeFlecha) {
				if(Entidade.isColidding(this, e)) {
					estaTomandoDano = true;
					vida -= 3;
					Game.flechas.remove(i);
					return;
				}
			}
		}
	//}
	
	public boolean isColiddingWithPlayer () {
		Rectangle atualInimigo = new Rectangle(this.getX() + maskx ,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return atualInimigo.intersects(player);
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle atualInimigo = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		
		for(int i = 0; i < Game.chefao.size(); i++ ) {
			PaiDoRadu e = Game.chefao.get(i);
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
			if(direcao == right_dir && Game.levelAtual ==6) {
				g.drawImage(Entidade.ChefaoMorto, this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
			}
		if(direcao == right_dir && Game.levelAtual !=6) {
			g.drawImage(rightBoss[index], this.getX() +8  - Camera.x, this.getY() -1 - Camera.y, null);
		}else if(direcao == left_dir && Game.levelAtual !=6) {
		g.drawImage(leftBoss[index], this.getX()+8  - Camera.x, this.getY() -1 - Camera.y, null);	
		} else if (estaTomandoDano) {
			
		}
	}  else if(direcao == right_dir) {
		g.drawImage(Entidade.ChefaoTomandoHitDireita, this.getX() - Camera.x, this.getY() - Camera.y, null);
	} else
		g.drawImage(Entidade.ChefaoTomandoHitEsquerda, this.getX() - Camera.x, this.getY() - Camera.y, null);
}
}