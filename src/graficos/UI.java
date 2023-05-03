package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entidades.Player;
import game.Game;

public class UI {

	public void render (Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 4,50, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.vida/Game.player.maxVida)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,8));
		g.drawString((int)Game.player.vida+"/"+(int)Game.player.maxVida, 18, 11);
		g.setFont(new Font("arial",Font.BOLD,10));
		g.drawString("Fase: " + Game.levelAtual, 100, 8);
	}
	
}