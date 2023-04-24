package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;
import world.World;

public class Inimigo extends Entidade{
	
	private double speed = 0.6;
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private int frames = 0;
	private int maxFrames = 3;
	private int index = 0;
	private int maxIndex = 1;
	private BufferedImage[] sprites;

	public Inimigo(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96+16, 16, 16, 16);
	}

	public void tick() {
		if(isColiddingWithPlayer() == false) {
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
			// estamos colidindo
			if(Game.rand.nextInt(100) < 10) {
				Game.player.vida -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			
			
		}
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index ++;
			if (index == maxIndex) {
				index = 0;
			}
		}
		}
	 
}
	
	public boolean isColiddingWithPlayer () {
		Rectangle atualInimigo = new Rectangle(this.getX() + maskx ,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return atualInimigo.intersects(player);
	}
	
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
	
	public void render(Graphics g) {
		super.render(g);
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
 