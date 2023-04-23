package entidades;

import java.awt.image.BufferedImage;

import game.Game;
import world.World;

public class Inimigo extends Entidade{
	
	private double speed = 1;

	public Inimigo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void tick() {
		if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
			x += speed;
		} else if (x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
			x -= speed;
		}
		if(y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))) {
			y += speed;
		} else if (y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))) {
			y -= speed;
		}
	}
	
}
