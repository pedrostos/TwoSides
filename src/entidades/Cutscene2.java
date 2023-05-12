package entidades;

import java.awt.image.BufferedImage;

import game.Game;

public class Cutscene2 extends Entidade{
	
	
	public Cutscene2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public static int vida = 10;
	

	
	public void seAutoDestroi () {
		Game.cut2.remove(this);
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
	