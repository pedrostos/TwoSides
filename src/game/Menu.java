package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Menu {
	
	public String[] opcoes = {"Novo Jogo","Sair"};
	public int opcaoAtual = 0;
	public int maxOpcao = opcoes.length - 1;
	public boolean up,down,enter;
	public boolean pausa = false;
	private BufferedImage telaDeFundo;
	
	public Menu () {
		try {
			telaDeFundo = ImageIO.read(getClass().getResource("/fundoMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick () {
		if(up) {
			up = false;
			opcaoAtual --;
			if(opcaoAtual < 0)
				opcaoAtual = maxOpcao;
		}
		if(down) {
			down = false;
			opcaoAtual ++;
			if(opcaoAtual > maxOpcao)
				opcaoAtual = 0;
		}
		if(enter) {
			enter = false;
			if(opcoes[opcaoAtual] == "Novo Jogo" || opcoes[opcaoAtual] == "Continuar") {
				Game.estadoDoJogo = "Normal";
				pausa = false;
			}else if (opcoes[opcaoAtual] == "Sair") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		//menu
		
		Graphics2D g2 = (Graphics2D) g;
		//g2.setColor(new Color(0,0,0,100));
		//g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.drawImage(telaDeFundo, 0, 0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE, null);
		g.setColor(Color.black);
		g.setFont(new Font("arial",Font.BOLD,50));
		g.drawString("TWO SIDES", (Game.WIDTH*Game.SCALE) /2 - 150, (Game.WIDTH*Game.SCALE) /2 - 250 );
		
		// opções do menu
		g.setFont(new Font("arial",Font.BOLD,25));
		g.setColor(Color.black);
		if(pausa == false)
		g.drawString("Novo Jogo ", (Game.WIDTH*Game.SCALE) /2 - 80, (Game.WIDTH*Game.SCALE) /2 - 175);
		else
			g.drawString("Continuar", (Game.WIDTH*Game.SCALE) /2 - 80, (Game.WIDTH*Game.SCALE) /2 - 175);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) /2 - 48, (Game.WIDTH*Game.SCALE) /2 - 125);
		
		if(opcoes[opcaoAtual] == "Novo Jogo") {
			g.drawString("--> ", (Game.WIDTH*Game.SCALE) /2 - 120, (Game.WIDTH*Game.SCALE) /2 - 175);
		}else if (opcoes[opcaoAtual] == "Sair") {
			g.drawString("-->", (Game.WIDTH*Game.SCALE) /2 - 88, (Game.WIDTH*Game.SCALE) /2 - 125);
			
		}
	}
	
}
