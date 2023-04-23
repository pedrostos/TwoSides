package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	
		private Tile[] tiles;
		public static  int WIDTH,HEIGHT;
	
		public World (String path) {
			try {
				BufferedImage map = ImageIO.read(getClass().getResource(path));
				int[] pixels = new int[map.getWidth() * map.getHeight()];
				WIDTH = map.getWidth();
				HEIGHT = map.getHeight();
				tiles = new Tile[map.getWidth() * map.getHeight()];
				map.getRGB(0, 0, map.getWidth(), map.getHeight() ,pixels,0,map.getWidth());
				
				// looping para rodar cada posição do mapa e reconhecer as cores para representar itens,chão e outra coisas
				for (int xx =0; xx < map.getWidth(); xx++) {
					for(int yy =0; yy < map.getHeight(); yy++) {
						int pixelAtual = pixels[xx + (yy*map.getWidth())];
						
						if(pixelAtual == 0xFF000000) {	
							// pixel do chão
							tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_Chao);
						} else if (pixelAtual == 0xFFFFFFFF) {
							// pixel da parede
							tiles[xx + (yy*WIDTH)] = new ParedeTile(xx*16,yy*16,Tile.Tile_Parede);
						}else if (pixelAtual == 0xFF5fcde4) {
							// pixel do personagem
							tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_Chao);
						} else {
							tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_Chao);
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void render(Graphics g) {
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					Tile tile = tiles[xx + (yy*WIDTH)];
					tile.render(g);
				}
			}
		}
}
