package entidades;

import java.awt.image.BufferedImage;

import game.Game;

public class Cutscene extends Entidade{
	
	
	public Cutscene(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public static int vida = 10;
	

	
	public void seAutoDestroi () {
		Game.cut.remove(this);
		Game.entidades.remove(this);
	}
	
	public void tick() {
	if(vida <=0 ) {
		vida--;
		seAutoDestroi();
		return;	
	}
	}
}
	