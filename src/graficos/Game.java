package graficos;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static JFrame jframe;
	private final int WIDTH = 160;
	private final int HEIGHT = 160;
	private final int SCALE = 3;
	
	public Game ( ) {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		jframe = new JFrame();
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("TwoSides");
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		
	}

	@Override
	public void run() {
	
		
	}

}
