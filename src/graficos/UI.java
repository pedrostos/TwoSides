package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entidades.Player;
import game.Game;


public class UI {
	private boolean mostrarMensagemGameOver = true;
	private boolean mostrarMensagemDoFim = true;
	
	

	public void render (Graphics g) {
		if(Game.levelAtual != 7) {
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
		if(Game.levelAtual == 7) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,12));
			g.setColor(Color.white);
			g.drawString("Parabéns, Você venceu o game",30,35);
			g.setFont(new Font("Arial",Font.BOLD,12));
			g.setColor(Color.white);
			g.drawString("Aperte enter para sair ",65,150);
		}
	}
	
}