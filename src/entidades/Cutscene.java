package entidades;

import java.awt.image.BufferedImage;

import game.Game;

public class Cutscene extends Entidade{
	
	// Construtor para a criação da cutscene da fase 3.
	public Cutscene(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	// Duração do tempo da cutscene.
	public static int vida = 10;

	// Método para quando a cutscene acabar, ele será retirado do array de cutscenes e entidades.
	public void seAutoDestroi () {
		Game.cut.remove(this);
		Game.entidades.remove(this);
	}

	// Método para iniciar outros métodos.
	public void tick() {
		// Condição em que determina que, se a vida for menor ou igual a 0 chamará o método "seAutoDestrói".
	if(vida <= 0 ) {
		vida--;
		seAutoDestroi();
		return;	
	}
	}
}
	