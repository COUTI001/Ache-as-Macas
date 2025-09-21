package com.coutigames.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.coutigames.main.Game;
import com.coutigames.world.Camera;

public class Entity {
	
	protected int depth; // Controle de profundidade
	
	public static BufferedImage CHECKPOINT_1 = Game.spritesheet.getSprite(292, 162, 24, 32);//Cruz I
	public static BufferedImage CHECKPOINT_2 = Game.spritesheet.getSprite(292, 33, 24, 33);//Cruz II
	public static BufferedImage CHECKPOINT_3 = Game.spritesheet.getSprite(292, 66, 24, 32);//Cruz III
	public static BufferedImage CHECKPOINT_4 = Game.spritesheet.getSprite(292, 98, 24, 32);//Cruz IV
	public static BufferedImage CHECKPOINT_5 = Game.spritesheet.getSprite(291, 130, 24, 32);//Cruz V
	public static BufferedImage CHECKPOINT_6 = Game.spritesheet.getSprite(262, 33, 24, 33);//Cruz VI
	public static BufferedImage CHECKPOINT_7 = Game.spritesheet.getSprite(262, 1, 24, 33);//Cruz VII
	public static BufferedImage CHECKPOINT_8 = Game.spritesheet.getSprite(292, 0, 24, 33);//Cruz VIII
	public static BufferedImage CHECKPOINT_9 = Game.spritesheet.getSprite(262, 130, 24, 34);//Cruz IX
	public static BufferedImage CHECKPOINT_10 = Game.spritesheet.getSprite(262, 165, 24, 34);//Cruz X
	public static BufferedImage CHECKPOINT_11 = Game.spritesheet.getSprite(262, 200, 24, 34);//Cruz XI
	public static BufferedImage CHECKPOINT_12 = Game.spritesheet.getSprite(293, 200, 24, 34);//Cruz XII
	public static BufferedImage CHECKPOINT_13 = Game.spritesheet.getSprite(262, 235, 24, 34);//Cruz XIII
	public static BufferedImage CHECKPOINT_14 = Game.spritesheet.getSprite(294, 249, 24, 34);//Cruz XIV
	public static BufferedImage CHECKPOINT_15 = Game.spritesheet.getSprite(229, 254, 24, 34);//Cruz XV
	
	
	public static BufferedImage TREE_1 = Game.spritesheet.getSprite(192, 38, 32, 42);//Arvore1
	public static BufferedImage TREE_2 = Game.spritesheet.getSprite(227, 64, 27, 32);//Arvore2
	public static BufferedImage TREE_3 = Game.spritesheet.getSprite(191, 1, 32, 37);//Arvore2
	
	public static BufferedImage BUSHES_1 = Game.spritesheet.getSprite(225, 288, 30, 32);//Arbusto1
	public static BufferedImage BUSHES_2 = Game.spritesheet.getSprite(258, 293, 26, 25);//Arbusto2
	public static BufferedImage BUSHES_3 = Game.spritesheet.getSprite(291, 284, 29, 36);//Arbusto3
	public static BufferedImage BUSHES_4 = Game.spritesheet.getSprite(97, 261, 29, 59);//Arbusto4(De uma folh e pedra)
	public static BufferedImage BUSHES_5 = Game.spritesheet.getSprite(227, 7, 23, 21);//Arbusto5 achatado
	
	public static BufferedImage APPLE_1 = Game.spritesheet.getSprite(139, 267, 9, 10);//maça1
	public static BufferedImage COGUMELO_1 = Game.spritesheet.getSprite(167, 263, 18, 18);//
	public static BufferedImage CORACAO_1 = Game.spritesheet.getSprite(195, 261, 27, 23);//
	public static BufferedImage SPEED_BOOST = Game.spritesheet.getSprite(0, 224, 16, 16);//speed boost
	
	
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	public boolean debug = false;
	
	private BufferedImage sprite;
	
	public int maskx,masky,mwidth,mheight;
	
	public Entity(int x,int y,int width,int height,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.depth = y;//para profundidade
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	 public int getDepth() {//para profundidadee
	        return depth;
	    }
	 public void setDepth(int depth) {
	        this.depth = depth;
	    }
	
	public void tick(){
		
	}
	
	public static boolean isColliding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y,mwidth,mheight);
	}
	
}
