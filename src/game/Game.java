package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

import entidades.Radu;
import entidades.RaduCutscene;
import entidades.Cutscene;
import entidades.Cutscene2;
import entidades.Entidade;
import entidades.Inimigo;
import entidades.Lia;
import entidades.Luke;
import entidades.Anahi;
import entidades.TiroDeFlecha;
import graficos.Spritesheet;
import graficos.UI;
import world.Camera;
import world.World;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static JFrame jframe;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	public static Anahi player;
	private BufferedImage image;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigos;
	public static List<Radu> boss;
	public static List<Luke> chefao;
	public static List<RaduCutscene> bossCutscene;
	public static List<TiroDeFlecha> flechas;
	public static List<Cutscene> cut;
	public static List<Cutscene2> cut2;
	public static Spritesheet spritesheet;
	public static World world;
	public static Random rand;
	public static List<Lia> lia;
	public Menu menu;
	public UI ui;
	
	public static int levelAtual = 1, maxLevel = 7;
	public static String estadoDoJogo = "Menu";
	public  boolean mostrarMensagemGameOver = true;
	private int framesGameOver = 0;
	private boolean reiniciarOJogo = false;

	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = entrada ; 
	public int timeCena = 0, maxTimeCena = 60*15;

	// Construtor que inicializa o jogo.
	public Game () {
		// Gera um número randômico e atribui a uma variável.
		rand = new Random();
		// Chamada do método da interface KeyListener.
		addKeyListener(this);
		// Chamada do método da setPreferred e alterando a dimensão da interface gráfica.
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		// Chamada do método da inteface gráfica.
		initFrame();

		// Instânciando objetos.
		ui = new UI();
		image = new BufferedImage (WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entidade>();
		inimigos = new ArrayList<Inimigo>();
		boss = new ArrayList<Radu>();
		chefao = new ArrayList<Luke>();
		bossCutscene = new ArrayList<RaduCutscene>();
		flechas = new ArrayList<TiroDeFlecha>();
		cut = new ArrayList<Cutscene>();
		cut2 = new ArrayList<Cutscene2>();
		spritesheet = new Spritesheet("/spritesheeet.png");
		player = new Anahi(0,0,16,16,spritesheet.getSprite(0, 0, 16, 16));
		lia = new ArrayList<Lia>();
		entidades.add(player);
		world = new World("/level1.png");
		menu = new Menu();
		// Fim da instância de objetos.
	}

	// Método inicializador da interface gráfica.
	public void initFrame() {

		jframe = new JFrame();
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("TwoSides");
	}

	// Método que instânica as threads, otimizando a performance do jogo.
	public synchronized void start () {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	// Método que encerra as threads.
	public synchronized void stop () {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Método main que instância o jogo e inicializa.
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	// Método que possui condicionais para definir os estados e leveis do jogo.
	public void tick () {
		if(estadoDoJogo == "Normal") {
			reiniciarOJogo = false;
			
			if(Game.levelAtual == 1) {
			if (Game.estado_cena == Game.jogando) {
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.tick();
		}
		for(int i = 0; i < flechas.size(); i++) {
			flechas.get(i).tick();
		}
			} else {
				if (Game.estado_cena == Game.entrada) {
					if(player.getX() < -55) {
						player.x++;
					} else {
						Game.estado_cena = Game.comecar;
					}
				} else if (Game.estado_cena == Game.comecar) {
					timeCena++;
					if(timeCena == maxTimeCena) {
						Game.estado_cena = Game.jogando;
						timeCena = 0;
					}
				}
				}
				}

			if(Game.levelAtual == 2) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						player.y = 144;
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			if(Game.levelAtual == 3) {
				player.y = 80;
				Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
				if (Game.estado_cena == Game.jogando) {
					
					for(int i=0; i <= Cutscene.vida; i++) {
						Cutscene.vida --;
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}	
			
			if(Game.levelAtual == 4) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			
			if(Game.levelAtual == 5) {
				player.y = 96;
				Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
				if (Game.estado_cena == Game.jogando) {
					for(int i=0; i <= Cutscene2.vida; i++) {
						Cutscene2.vida--;
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}
			
			if(Game.levelAtual == 6) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			
			if(Game.levelAtual == 7) {
				Sons.musica.setVolume(-80); 
				player.y = 144;
				Camera.y = 90;
				if (Game.estado_cena == Game.jogando) {
					for(int i=0; i <= Cutscene.vida; i++) {
						Cutscene.vida --;	
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}	

			// Estrutura condicional que dependendo da condição, decide avançar de level ou reiniciar o jogo.
			if(inimigos.size() == 0  && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual < 4) {
				levelAtual++;
				if(levelAtual > maxLevel ) {
					levelAtual = levelAtual ;
				}
				String novoMundo = "level" + levelAtual + ".png";
				World.reiniciarOJogo(novoMundo);
				Game.estado_cena = Game.entrada;
				timeCena = 0;
				timeCena++;
				}
				if(inimigos.size()  <= 0  && boss.size() <= 0 && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual >= 4 && Game.levelAtual < 6) {
					// avançar para o prox level 
					levelAtual++;
					if(levelAtual > maxLevel ) {
						levelAtual = levelAtual ;
					}
					String novoMundo = "level" + levelAtual + ".png";
					World.reiniciarOJogo(novoMundo);
					Game.estado_cena = Game.entrada;
					timeCena = 0;
					timeCena++;
					}
				if(inimigos.size() <= 0  && chefao.size() <= 0 && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual >= 6 ) {
					levelAtual++;
					if(levelAtual > maxLevel ) {
						levelAtual = levelAtual ;
					}
					String novoMundo = "level" + levelAtual + ".png";
					World.reiniciarOJogo(novoMundo);
					Game.estado_cena = Game.entrada;
					timeCena = 0;
					timeCena++;
					}
			
		} else if (estadoDoJogo == "Game_Over" ) {
			// Condicional que se o jogador falhar na fase, mostrará uma mensagem de "Game Over".
			this.framesGameOver ++;
			if (this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.mostrarMensagemGameOver) 
					this.mostrarMensagemGameOver = false;
					else 
						this.mostrarMensagemGameOver = true;
				
			} if(reiniciarOJogo) {
				// Condicional que reinicia o jogo, caso o jogador queira.
				reiniciarOJogo = false;
				estadoDoJogo = "Normal";
				levelAtual = levelAtual ;
				if (Game.levelAtual == 6) {
					
				}
				String novoMundo = "level" + levelAtual + ".png";
				World.reiniciarOJogo(novoMundo);
			}
		} else if (estadoDoJogo == "Menu") {
			menu.tick();
		}
		}

	// Método para renderização do jogo.
	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		world.render(g);
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.render(g);
		}
		for(int i = 0; i < flechas.size(); i++) {
			flechas.get(i).render(g);;
		}
		ui.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("arial",Font.BOLD,20));
		g.setColor(Color.white);
		if (Game.levelAtual != 7) {
		g.drawString("Flechas: " + player.flechas , 600, 20);
		}

		// Condicional em que se o jogador falhar na fase, aparecerá uma mensagem de "Game Over" e "Reiniciar" na tela.
		if (estadoDoJogo == "Game_Over") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.setColor(Color.white);
			g.drawString("Game Over !!!", (WIDTH*SCALE) / 2 - 110, (HEIGHT*SCALE) /2 - 15);
			g.setFont(new Font("arial",Font.BOLD,30));
			if(mostrarMensagemGameOver)
			g.drawString("--> Para reiniciar o jogo aperte a tecla Enter <--", (WIDTH*SCALE) / 2 - 330, (HEIGHT*SCALE) /2 +40);
		} else if (estadoDoJogo == "Menu") {
			menu.render(g);
		}

		// Condicional que define a mensagem (instruções) que será exibida na fase 1.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 1) {
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Instruções:", 30,50);
			g.drawString("1: Use W/A/S/D ou as teclas da seta para fazer o personagem andar", 50,95);
			g.drawString("2: Use a tecla espaço para atirar", 50,125);
			g.drawString("3: Se quiser pausar o jogo use a tecla ESC ou a tecla P", 50,155);
			g.drawString("4: Ao colidir com os objetos, esses objetos serão coletados", 50,185);
			g.drawString("5: Não deixe os inimigos chegarem perto, pois eles vão dar dano", 50,215);
		}

		// Condicional que define a mensagem (dicas) que será exibida na fase 2.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 2) {
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 80,50);
			g.drawString("1: Lia foi raptada encontre-a", 100,95);
			g.drawString("2: No caminho tem inimigos derrote eles", 100,125);
			g.drawString("3: Procure quem fez isso com a Lia.", 100,155);
		}

		// Condicional que define a mensagem que será exibida na fase 3.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 3 && timeCena > 60*2) {
			maxTimeCena = 60*6;
			g.setColor(Color.black);
			g.fillOval(120,120, 450,70);
			g.setFont(new Font("Arial",Font.BOLD,15));
			g.setColor(Color.white);
			g.drawString("Anahí encontra lia ",260,150 );	
		}

		// Condicional que define a mensagem (dicas) que será exibida na fase 4.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 4) {
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 100,50);
			g.drawString("1: Anahí e Lia avistam algo", 100,95);
			g.drawString("2: Radu está queimando a floresta", 100,125);
			g.drawString("3: Derrote-o.", 100,155);
		}

		// Condicional que define a mensagem (diálogo) que será exibida na fase 5.
				if(Game.estado_cena == Game.comecar && Game.levelAtual == 5 ){
					maxTimeCena = 60*10;
					g.setColor(Color.black);
					g.fillOval(70,100, 600,90);
					g.setFont(new Font("Arial",Font.BOLD,20));
					g.setColor(Color.white);
					g.drawString("- Obrigada por me salvar... Estou de volta, Anahí", 115,140);
					g.drawString("- Meu pai Luke estava me manipulando", 115,165);
				}

		// Condicional que define a mensagem (dicas) que será exibida na fase 6.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 6){
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 100,50);
			g.drawString("1: Luke aparece", 100,95);
			g.drawString("2: Lia percebe que foi esse homem que a raptou", 100,125);
			g.drawString("3: Derrote-o e salve a floresta.", 100,155);
		}

		// Condicional que define o tempo da cutscene da fase 7.
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 7) {
			maxTimeCena = 60*4;
		}
		bs.show();		
	}
	
	@Override
	// Método que executa cálculo para mostrar o FPS do jogo.
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta > 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	// Método que escuta os eventos de teclas do jogador, com a inteface KeyListener.
	public void keyPressed(KeyEvent e) {
			// Move para direita quando pressionado a seta pra direita ou a tecla D.
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// Move para esquerda quando pressionado a seta pra esquerda ou a tecla A.
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// Move para cima quando pressionado a seta pra cima ou a tecla W.
			player.up = true;
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// Move para baixo quando pressionado a seta pra baixo ou a tecla S.
			player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Seleciona a opção do menu com o enter.
			reiniciarOJogo = true;
			if(estadoDoJogo == "Menu") {
				menu.enter = true;
			}
			if (Game.levelAtual == 7)
				System.exit(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P ) {
			// Condição em que se as teclas "ESC" ou "P" forem apertadas, abrirá o menu de pause.
			estadoDoJogo = "Menu";
			menu.pausa = true;
		}
	}

	@Override
	// Método que possui condionais que definem quando o jogador para de pressionar alguma tecla.
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// Para de mover para direita quando deixa de pressionar a seta pra direita ou a tecla D.
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// Para de mover para esquerda quando deixa de pressionar a seta pra esqueda ou a tecla A.
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// Para de mover para cima quando deixa de pressionar a seta pra cima ou a tecla W.
			player.up = false;
			// Se o estado do jogo for no menu, o jogo moverá para a opção acima.
			if (estadoDoJogo == "Menu") {
				menu.up = true;
			}
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// Para de mover para baixo quando deixa de pressionar a seta pra baixo ou a tecla S.
			player.down = false;
			// Se o estado do jogo for no menu, o jogo moverá para a opção abaixo.
			if (estadoDoJogo == "Menu") {
				menu.down = true;
			}
			
		} if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// Condicional que se apertado a tecla "ESPAÇO", haverá o disparo da flecha.
			player.tiro =true;
		}
	}
}
