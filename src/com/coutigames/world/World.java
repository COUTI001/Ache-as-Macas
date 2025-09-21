package com.coutigames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.coutigames.entities.Apple1;
import com.coutigames.entities.Bushes1;
import com.coutigames.entities.Bushes2;
import com.coutigames.entities.Bushes3;
import com.coutigames.entities.Bushes4;
import com.coutigames.entities.Bushes5;
import com.coutigames.entities.CheckPoint_01;
import com.coutigames.entities.CheckPoint_02;
import com.coutigames.entities.CheckPoint_03;
import com.coutigames.entities.CheckPoint_04;
import com.coutigames.entities.CheckPoint_05;
import com.coutigames.entities.CheckPoint_06;
import com.coutigames.entities.CheckPoint_07;
import com.coutigames.entities.CheckPoint_08;
import com.coutigames.entities.CheckPoint_09;
import com.coutigames.entities.CheckPoint_10;
import com.coutigames.entities.CheckPoint_11;
import com.coutigames.entities.CheckPoint_12;
import com.coutigames.entities.CheckPoint_13;
import com.coutigames.entities.CheckPoint_14;
import com.coutigames.entities.CheckPoint_15;
import com.coutigames.entities.Cogumelo1;
import com.coutigames.entities.Coracao;
import com.coutigames.entities.Enemy;
import com.coutigames.entities.Entity;
import com.coutigames.entities.Player;
import com.coutigames.entities.SpeedBoost;
import com.coutigames.entities.Tree1;
import com.coutigames.entities.Tree2;
import com.coutigames.entities.Tree3;
import com.coutigames.graficos.Spritesheet;
import com.coutigames.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 32;
	
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000){
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
					}
					else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_WALL);
						
					}
					else if(pixelAtual == 0xFF0026FF) {
						
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					   													
					} 
					else if(pixelAtual == 0xFF808080) {//Cor cinza // Cruz I
					CheckPoint_01 point_01 = new CheckPoint_01(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_1);
				       Game.entities.add(point_01);
				            
					} 
					else if(pixelAtual == 0xFFD67FFF) {//Cor cinza // Cruz II
						CheckPoint_02 point_02 = new CheckPoint_02(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_2);
					       Game.entities.add(point_02);
					            
						} 
					else if(pixelAtual == 0xFFFFE97F) {//Cor cinza // Cruz III
						CheckPoint_03 point_03 = new CheckPoint_03(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_3);
					       Game.entities.add(point_03);
					            
						} 
					else if(pixelAtual == 0xFFFF2323) {//Cor cinza // Cruz III
						CheckPoint_04 point_04 = new CheckPoint_04(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_4);
					       Game.entities.add(point_04);
					            
						} 
					else if(pixelAtual == 0xFF66B7FF) {//Cor cinza // Cruz III
						CheckPoint_05 point_05 = new CheckPoint_05(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_5);
					       Game.entities.add(point_05);
					            
						} 
					else if(pixelAtual == 0xFFFF5B0F) {//Cor cinza // Cruz III
						CheckPoint_06 point_06 = new CheckPoint_06(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_6);
					       Game.entities.add(point_06);
					            
						} 
					else if(pixelAtual == 0xFFD154FF) {//Cor cinza // Cruz III
						CheckPoint_07 point_07 = new CheckPoint_07(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_7);
					       Game.entities.add(point_07);
					            
						} 
					else if(pixelAtual == 0xFF6726FF) {//Cor cinza // Cruz III
						CheckPoint_08 point_08 = new CheckPoint_08(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_8);
					       Game.entities.add(point_08);
					            
						} 
					else if(pixelAtual == 0xFF0C5DFF) {//Cor cinza // Cruz III
						CheckPoint_09 point_09 = new CheckPoint_09(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_9);
					       Game.entities.add(point_09);
					            
						} 
					else if(pixelAtual == 0xFFFF2D7E) {//Cor cinza // Cruz III
						CheckPoint_10 point_10 = new CheckPoint_10(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_10);
					       Game.entities.add(point_10);
					            
						} 
					else if(pixelAtual == 0xFFA3FF2B) {//Cor cinza // Cruz III
						CheckPoint_11 point_11 = new CheckPoint_11(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_11);
					       Game.entities.add(point_11);
					            
						} 
					else if(pixelAtual == 0xFFFF6528) {//Cor cinza // Cruz III
						CheckPoint_12 point_12 = new CheckPoint_12(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_12);
					       Game.entities.add(point_12);
					            
						} 
					else if(pixelAtual == 0xFFFF8E16) {//Cor cinza // Cruz III
						CheckPoint_13 point_13 = new CheckPoint_13(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_13);
					       Game.entities.add(point_13);
					            
						} 
					else if(pixelAtual == 0xFFFFF716) {//Cor cinza // Cruz III
						CheckPoint_14 point_14 = new CheckPoint_14(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_14);
					       Game.entities.add(point_14);
					            
						}
					else if(pixelAtual == 0xFFD8FF19) {//Cor cinza // Cruz III
						CheckPoint_15 point_15 = new CheckPoint_15(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE-10, TILE_SIZE-10, Entity.CHECKPOINT_15);
					       Game.entities.add(point_15);
					            
						}
					
					else if(pixelAtual == 0xFF00FF90) {//Cor verde claro // arvore1
						Tree1 tr1 = new Tree1(xx * TILE_SIZE, yy * TILE_SIZE + 4, TILE_SIZE, TILE_SIZE, Entity.TREE_1);
						tr1.setDepth(-20); // Define um depth fixo(profundidade)
						Game.entities.add(tr1);					            
					
					} 
					else if(pixelAtual == 0xFF4CFF00) {//Cor verde medio // arvore2
						Tree2 tr2 = new Tree2(xx * TILE_SIZE, yy * TILE_SIZE +10, TILE_SIZE, TILE_SIZE, Entity.TREE_2);
						tr2.setDepth(-20); // Define um depth fixo(profundidade)
						Game.entities.add(tr2);
						
			      }
					else if(pixelAtual == 0xFF34AF00) {//Cor verde escuro // arvore3
						Tree3 tr3 = new Tree3(xx * TILE_SIZE, yy * TILE_SIZE - 1, TILE_SIZE, TILE_SIZE, Entity.TREE_3);
						tr3.setDepth(-20); // Define um depth fixo(profundidade)
						Game.entities.add(tr3);
			      
			      }
					else if(pixelAtual == 0xFF227200) {//Cor verde escuro // arvore3
						Bushes1 bs1 = new Bushes1(xx * TILE_SIZE, yy * TILE_SIZE -1, TILE_SIZE, TILE_SIZE, Entity.BUSHES_1);
						bs1.setDepth(210); // Define um depth fixo(profundidade)
						Game.entities.add(bs1);
			      
			      }
					else if(pixelAtual == 0xFF0C2800) {//Cor verde escuro // arvore3
						Bushes2 bs2 = new Bushes2(xx * TILE_SIZE, yy * TILE_SIZE + 4, TILE_SIZE, TILE_SIZE, Entity.BUSHES_2);
						bs2.setDepth(-20); // Define um depth fixo(profundidade)
						Game.entities.add(bs2);
					}
					
					else if(pixelAtual == 0xFF00FFFF) {//Cor verde escuro // arvore3
						Bushes3 bs3 = new Bushes3(xx * TILE_SIZE, yy * TILE_SIZE -4, TILE_SIZE, TILE_SIZE, Entity.BUSHES_3);
						bs3.setDepth(0); // Define um depth fixo(profundidade)
						Game.entities.add(bs3);
						
					}
					else if(pixelAtual == 0xFF800037) {//Cor verde escuro // arvore3
						Bushes4 bs4 = new Bushes4(xx * TILE_SIZE, yy * TILE_SIZE - 26, TILE_SIZE, TILE_SIZE, Entity.BUSHES_4);
						bs4.setDepth(0); // Define um depth fixo(profundidade)
						Game.entities.add(bs4);
						
					}
					else if(pixelAtual == 0xFF503F7F) {//Cor verde escuro // arvore3
						Bushes5 bs5 = new Bushes5(xx * TILE_SIZE, yy * TILE_SIZE +12, TILE_SIZE, TILE_SIZE, Entity.BUSHES_5);
						bs5.setDepth(-21); // Define um depth fixo(profundidade)
						Game.entities.add(bs5);
						
					}
					else if(pixelAtual == 0xFF9E0000) {//Cor vermelho // maça
						Apple1 ap1 = new Apple1(xx * TILE_SIZE - 20, yy * TILE_SIZE + 1, TILE_SIZE - 20, TILE_SIZE-20, Entity.APPLE_1);
						ap1.setDepth(-23); // Define um depth fixo(profundidade)
						Game.entities.add(ap1);
						Game.macaContagem++; //Aqui para colocar o total de maças no cabeçalho
			      }
					else if(pixelAtual == 0xFFFF7FB6) {//Cor rosa // 
						Cogumelo1 cg1 = new Cogumelo1(xx * TILE_SIZE +48, yy * TILE_SIZE +14, TILE_SIZE -20, TILE_SIZE -20, Entity.COGUMELO_1);
						cg1.setDepth(-22); // Define um depth fixo(profundidade)
						Game.entities.add(cg1);
					}
					else if(pixelAtual == 0xFFFF7F23) {//Cor laranja // 
						Coracao cr1 = new Coracao(xx * TILE_SIZE +20, yy * TILE_SIZE +4, TILE_SIZE -20, TILE_SIZE - 20, Entity.CORACAO_1);
						cr1.setDepth(-23); // Define um depth fixo(profundidade)
						Game.entities.add(cr1);
					}
					else if(pixelAtual == 0xFFFFFF00) {//Cor amarela // Speed Boost
						SpeedBoost sb1 = new SpeedBoost(xx * TILE_SIZE +8, yy * TILE_SIZE +8, TILE_SIZE -16, TILE_SIZE -16, Entity.SPEED_BOOST);
						sb1.setDepth(-23); // Define um depth fixo(profundidade)
						Game.entities.add(sb1);
					}
		       } 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	
	public static void restartGame(String level) {
	    // Limpa listas de entidades e inimigos
	    Game.entities.clear();
	    Game.enemies.clear();
	    
	    // Recria a lista de entidades
	    Game.entities = new ArrayList<Entity>();
	    
	    // Recarrega spritesheet e recria o jogador
	    Game.spritesheet = new Spritesheet("/spritesheet.png");
	    Game.player = new Player(0, 0, 28, 28, Game.spritesheet.getSprite(0, 32, 28, 28));	
	    
	    // Recarrega o mundo com o novo nível
	    Game.world = new World("/" + level);
	    
	    // Reseta variáveis globais relacionadas ao progresso do jogo
	    Game.pegaMaca = 0; // Reinicia a contagem de maçãs coletadas
	    
	    Game.macaContagem = World.calculateTotalMacas(); // Recalcula total de maçãs no nível
	    // Adiciona o jogador às entidades
	    
	    Game.entities.add(Game.player);

	   
	}

	
	private static int calculateTotalMacas() {
	    int totalMacas = 0;

	    // Percorre a lista de entidades para contar as maçãs
	    for (Entity entity : Game.entities) {
	        if (entity instanceof Apple1) { // Supondo que MacaEntity representa maçãs
	            totalMacas++;
	        }
	    }

	    return totalMacas;
	}

	public void render(Graphics g){
		int xstart = Camera.x >> 6;
		int ystart = Camera.y >> 6;
		
		int xfinal = xstart + (Game.WIDTH >> 3);
		int yfinal = ystart + (Game.HEIGHT >> 3);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
