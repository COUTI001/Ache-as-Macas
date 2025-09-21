package com.coutigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.coutigames.graficos.Spritesheet;
import com.coutigames.main.Game;
import com.coutigames.main.Sound;
import com.coutigames.world.Camera;

public class SpeedBoost extends Entity {
	
	private int frames = 0;
	private int maxFrames = 8;
	private int index = 0;
	private int maxIndex = 3;
	private BufferedImage[] sprites;
	
	public SpeedBoost(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		sprites = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			sprites[i] = Game.spritesheet.getSprite(0 + (i*16), 224, 16, 16);
		}
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		// Verificar colisão com o jogador
		if(Entity.isColliding(this, Game.player)) {
			Game.player.hasSpeedBoost = true;
			Game.player.speedBoostFrames = 0;
			Sound.Colis_Check.play();
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
} 