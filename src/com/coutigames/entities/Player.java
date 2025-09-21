package com.coutigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.coutigames.graficos.Spritesheet;
import com.coutigames.main.Game;
import com.coutigames.main.Sound;
import com.coutigames.world.Camera;
import com.coutigames.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1, up_dir = 2, down_dir=3;
	public int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean arma = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false,mouseShoot = false;
	
	public double life = 100,maxLife=100;
	public int mx,my;
	
	// Sistema de power-ups
	public boolean hasSpeedBoost = false;
	public boolean hasShield = false;
	public int speedBoostFrames = 0;
	public int shieldFrames = 0;
	private final int SPEED_BOOST_DURATION = 300; // 5 segundos a 60 FPS
	private final int SHIELD_DURATION = 180; // 3 segundos a 60 FPS
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(64, 0,32,32);
		
		for(int i =0; i < 4; i++){
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 96, 32, 32);
		}
		for(int i =0; i < 4; i++){
			leftPlayer[i] = Game.spritesheet.getSprite(96 - (i*32), 128, 32, 32);
		}
		for(int i =0; i < 4; i++){
			upPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 160, 32, 32);
		}
		for(int i =0; i < 4; i++){
			downPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 192, 32, 32);
		}
		
	}
	
	public void tick(){
		depth = -10;//para profundidade(player na frente das entities)
		moved = false;
		
		// Aplicar speed boost se ativo
		double currentSpeed = speed;
		if (hasSpeedBoost) {
			currentSpeed = speed * 1.5;
			speedBoostFrames++;
			if (speedBoostFrames >= SPEED_BOOST_DURATION) {
				hasSpeedBoost = false;
				speedBoostFrames = 0;
			}
		}
		
		// Aplicar shield se ativo
		if (hasShield) {
			shieldFrames++;
			if (shieldFrames >= SHIELD_DURATION) {
				hasShield = false;
				shieldFrames = 0;
			}
		}
		
		if(right && World.isFree((int)(x+currentSpeed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=currentSpeed;
		}
		else if(left && World.isFree((int)(x-currentSpeed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=currentSpeed;
		}
		if(up && World.isFree(this.getX(),(int)(y-currentSpeed))){
			moved = true;
			dir = up_dir;
			y-=currentSpeed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+currentSpeed))){
			moved = true;
			dir = down_dir;
			y+=currentSpeed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
				
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
						
		if(life<=0) {
			 Game.gameState = "GAME_OVER";
			    life = 0;
		}
		
		
		verificarPegaFruta();
		vidaCoracao();
		danoCogumelo();
		updateCamera();
	
	}
	
	private void vidaCoracao() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Coracao) {
				if(Entity.isColliding(this,current)) {
					Sound.Colis_Vida.play();
					life +=5;
					 if(life >= maxLife)
						    life = maxLife;
					 Game.entities.remove(i);
					  return;
				}
			}
		}
		
	}
		
	private void danoCogumelo() {
		for(int l = 0; l < Game.entities.size(); l++) {
			  Entity ep = Game.entities.get(l);
			   if(ep instanceof Cogumelo1) {
			    if(Entity.isColliding(this, ep)) {
				 //Player.isDamaged = true;
				  Game.entities.remove(l);
				  
				  // Verificar se tem shield ativo
				  if (!hasShield) {
				    Sound.Dano.play();
				    //Sound.Dano.setVolume(-10.0f);
				     life -=15;
					  if(life <= 0) {
					       life = 0;
					    //Game.shouldRestart = true;
					     //Game.nextLevel = Game.currentLevel;
						  return;
				              }
				  } else {
				    // Shield ativo - não toma dano
				    hasShield = false;
				    shieldFrames = 0;
				  }
						   }
					   }
				   }
	            }

	private void verificarPegaFruta() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Apple1) {
				if(Entity.isColliding(this,current)) {
					Sound.Colis_Check.play();
					Game.pegaMaca ++;
					 Game.entities.remove(i);
					  return;
				}
			}
		}
		
	}

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*32 - Game.HEIGHT);
	}
	
	
		
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
				if(arma) {
					//Desenhar arma para direita.
					//g.drawImage(Entity.GUN_RIGHT, this.getX()+8 - Camera.x,this.getY() - Camera.y, null);
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
				if(arma) {
					//Desenhar arma para esquerda.
					//g.drawImage(Entity.GUN_LEFT, this.getX()-8 - Camera.x,this.getY() - Camera.y, null);
				}
			}else if(dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
				if(arma) {
				
			}
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
				if(arma) {
				
		}else {
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY() - Camera.y,null);
			if(arma) {
					//g.drawImage(Entity.GUN_DAMAGE_LEFT, this.getX()-8 - Camera.x,this.getY() - Camera.y, null);
				}else {
					//g.drawImage(Entity.GUN_DAMAGE_RIGHT, this.getX()+8 - Camera.x,this.getY() - Camera.y, null);
				}
			}
		}
			}
		}
	}
