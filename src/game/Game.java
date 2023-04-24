package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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

import entidades.Entidade;
import entidades.Inimigo;
import entidades.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static JFrame jframe;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	private final int SCALE = 3;
	public static  Player player;
	private BufferedImage image;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigos;
	public static Spritesheet spritesheet;
	public static World world;
	public static Random rand;
	public UI ui;
	
	
	public Game ( ) {
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objeto
		ui = new UI();
		image = new BufferedImage (WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entidade>();
		inimigos = new ArrayList<Inimigo>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(0, 0, 16, 16));
		entidades.add(player);
		world = new World("/map.png");
		//fim
		
	}
	
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
	
	public synchronized void start () {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop () {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.start();
	}
	
	public void tick () {
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.tick();
		}
	}
	
	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//renderização do jogo
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.render(g);
		}
		ui.render(g);
		//fim
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		bs.show();
				
	}

	@Override
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
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			// mover para direita pressionando a seta pra direita ou a tecla D
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			//mover para esquerda pressionando a seta da esquerda ou a tecla A
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			// mover para cima pressionando a seta pra cima ou a tecla W
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			//mover para baixo pressionando a seta pra baixo ou a tecla S
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
			// parar de mover para direita quando não pressionar a seta pra direita ou a tecla D
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
			//parar de mover para esquerda quando não pressionar a seta da esquerda ou a tecla A
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
			// parar de mover para cima quando não pressionar a seta pra cima ou a tecla W
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
			//parar de mover para baixo quando não pressionar a seta pra baixo ou a tecla S
		}	
		
	}

}
