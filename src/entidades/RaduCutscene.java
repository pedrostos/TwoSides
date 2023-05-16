package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import game.Game;
import world.Camera;
import world.World;

public class RaduCutscene extends Entidade{
	
	private double speed = 0.9;
	private int frames = 0;
	private int maxFrames = 2;
	private int index = 0;
	private int maxIndex = 1;
	public static int vida = 15;
	public int right_dir = 0 , left_dir = 1;
	public int direcao = right_dir;
	private  boolean moved = false;

	public RaduCutscene(int x, int y, int width, int height, BufferedImage boss_Entidade) {
		super(x, y, width, height, null);
	}

	public void tick() {
		moved = false;
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			direcao = right_dir;
			x += speed;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())){
			moved = true;
			direcao = left_dir;
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))){
			y += speed;
		} else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))){
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
	
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index ++;
			if (index > maxIndex) 
				index = 0;
		}
		
		if(vida <=0 ) {
			seAutoDestroi();
			return;
		}	
}
	public void seAutoDestroi () {
		Game.bossCutscene.remove(this);
		Game.entidades.remove(this);
	}
	
	public void render(Graphics g) {
		
		if(direcao == right_dir && Game.levelAtual == 5) {
			g.drawImage(BossDoBem, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
}
}