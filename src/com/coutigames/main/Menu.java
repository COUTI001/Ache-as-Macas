package com.coutigames.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = {"Novo Jogo","Recordes","Sobre","Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up,down,enter;
	
	public boolean pause = false;
	
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "Novo Jogo" || options[currentOption] == "continue") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "Sair") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//g2.setColor(new Color(0,0,0,100));
		//g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.blue);
		g.setFont(new Font("arial",Font.BOLD,36));
		
		g.drawString("(((COUTIGAMES)))", (Game.WIDTH*Game.SCALE) / 2 - 145, (Game.HEIGHT*Game.SCALE) / 2 - 160);
		
		// Subtítulo
		g.setColor(Color.green);
		g.setFont(new Font("arial",Font.BOLD,24));
		g.drawString("ACHE AS MAÇÃS", (Game.WIDTH*Game.SCALE) / 2 - 100, (Game.HEIGHT*Game.SCALE) / 2 - 120);
		
		// Instruções
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("Use WASD ou setas para mover", (Game.WIDTH*Game.SCALE) / 2 - 130, (Game.HEIGHT*Game.SCALE) / 2 + 62);
		g.drawString("Colete todas as maçãs vermelhas", (Game.WIDTH*Game.SCALE) / 2 - 137, (Game.HEIGHT*Game.SCALE) / 2 + 80);
		g.drawString("Evite os cogumelos venenosos", (Game.WIDTH*Game.SCALE) / 2 - 127, (Game.HEIGHT*Game.SCALE) / 2 + 100);
		g.drawString("Colete corações para recuperar vida", (Game.WIDTH*Game.SCALE) / 2 - 145, (Game.HEIGHT*Game.SCALE) / 2 + 120);
		
		//Opcoes de menu
		g.setColor(Color.yellow);
		g.setFont(new Font("arial",Font.BOLD,26));
		g.drawString("Novo Jogo", (Game.WIDTH*Game.SCALE) / 2 - 60, 240);
		g.drawString("Recordes", (Game.WIDTH*Game.SCALE) / 2 - 50, 268);
		g.drawString("Sobre", (Game.WIDTH*Game.SCALE) / 2 - 30, 295);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 - 20, 325);
		
		// Indicador de seleção
		g.setColor(Color.yellow);
		if(options[currentOption] == "Novo Jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 80, 240);
		}else if(options[currentOption] == "Recordes") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 70, 268);
		}else if(options[currentOption] == "Sobre") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 50, 295);
		}else if(options[currentOption] == "Sair") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 40, 325);
		}
		
		// High Score atual
		g.setColor(Color.orange);
		g.setFont(new Font("arial",Font.BOLD,20));
		g.drawString("Recorde Atual: " + Game.highScore, (Game.WIDTH*Game.SCALE) / 2 - 70, 200);
	}
	
}
