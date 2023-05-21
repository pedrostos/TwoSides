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

	// Construtor para a criação do RaduCutscene.
	public RaduCutscene(int x, int y, int width, int height, BufferedImage RaduCutscene) {
		super(x, y, width, height, null);
	}

	// Método para inicializar outros métodos.
	public void tick() {
		moved = false;

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

	// Condição em que determina que se a vida do RaduCutscene for menor ou igual que 0, ele chamará o método "seAutoDestrói".
	public void seAutoDestroi () {
		Game.bossCutscene.remove(this);
		Game.entidades.remove(this);
	}

	// Método que em que define qual imagem será exibida dependendo da condição.
	public void render(Graphics g) {
		
		if(direcao == right_dir && Game.levelAtual == 5) {
			g.drawImage(BossDoBem, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
}
}