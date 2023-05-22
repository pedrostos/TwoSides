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

	/*
	Construtor que possui try/catch, em que o programa tentará ler a imagem solicitada, se não obter sucesso,
	cairá no catch definindo e detalhando o erro causado.
	 */
	public Menu () {
		try {
			telaDeFundo = ImageIO.read(getClass().getResource("/fundoMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void tick () {
		// Estrutura condicional que ao pressionar uma tecla, o programa responderá dentro do menu, indo para a opção desejada.
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
			//Reproduz a música ao começar o jogo.
			Sons.musica.loop();
			Sons.musica.setVolume(-35); 
		
			enter = false;
			if(opcoes[opcaoAtual] == "Novo Jogo" || opcoes[opcaoAtual] == "Continuar") {
				Game.estadoDoJogo = "Normal";
				pausa = false;
			}else if (opcoes[opcaoAtual] == "Sair") {
				System.exit(1);
			}
		}
	}

	// Método que renderiza o menu.
	public void render(Graphics g) {
		// Menu.
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.drawImage(telaDeFundo, 0, 0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE, null);
		g.setColor(Color.green);
		g.setFont(new Font("arial",Font.BOLD,50));
		g.drawString("TWO SIDES", (Game.WIDTH*Game.SCALE) /2 - 150, (Game.WIDTH*Game.SCALE) /2 - 250 );
		
		// Opções do menu.
		Graphics2D g3 = (Graphics2D) g;
		g3.setColor(new Color(0,0,0,100));
		g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setFont(new Font("arial",Font.BOLD,25));
		g.setColor(Color.white);
		// Estrutura condicional que determina se o jogo será iniciado, pausado ou finalizado, dependendo da escolha do jogador.
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
