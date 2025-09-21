package com.coutigames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.coutigames.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8,4,70,8);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/Game.player.maxLife)*70),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,8));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife,30,11);
		
		g.setColor(Color.white);
		g.setFont(new Font("CaLibri",Font.BOLD,15));
		g.drawString("Maçãs:"+Game.pegaMaca+"/"+Game.macaContagem, 360,10);
		
		g.setFont(new Font("Arial",Font.BOLD,12));
		g.drawString("Nível: " + Game.CUR_LEVEL, 360, 25);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial",Font.BOLD,12));
		g.drawString("Pontos: " + Game.score, 360, 40);
		
		g.setColor(Color.orange);
		g.drawString("Recorde: " + Game.highScore, 360, 55);
		
		if (Game.gameState.equals("NORMAL")) {
			long currentTime = System.currentTimeMillis();
			long levelTime = currentTime - Game.levelStartTime;
			int seconds = (int)(levelTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			
			g.setColor(Color.cyan);
			g.setFont(new Font("Arial",Font.BOLD,10));
			g.drawString(String.format("Tempo: %02d:%02d", minutes, seconds), 360, 70);
		}
		
		if (Game.player.hasSpeedBoost) {
			g.setColor(Color.orange);
			g.setFont(new Font("Arial",Font.BOLD,10));
			g.drawString("VELOCIDADE BOOST!", 8, 25);
		}
		
		if (Game.player.hasShield) {
			g.setColor(Color.cyan);
			g.setFont(new Font("Arial",Font.BOLD,10));
			g.drawString("ESCUDO ATIVO!", 8, 40);
		}
	}
	
}
