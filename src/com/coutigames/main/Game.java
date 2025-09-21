/***Produzido por André Luiz Coutinho
 * 
 */

package com.coutigames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import com.coutigames.entities.Enemy;
import com.coutigames.entities.Entity;
import com.coutigames.entities.Player;
import com.coutigames.graficos.Spritesheet;
import com.coutigames.graficos.UI;
import com.coutigames.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{

	private static final long serialVersionUID = 1L;
	 public static JFrame frame;
	  private Thread thread;
	   private boolean isRunning = true;
	    public static final int WIDTH = 440;
	     public static final int HEIGHT = 290;
	      public static final int SCALE = 2;	
	
	public static int CUR_LEVEL = 1, MAX_LEVEL = 18;
	 private BufferedImage image;	
	  public static List<Entity> entities;
	   public static List<Enemy> enemies;		
	    public static Spritesheet spritesheet;	
	     public static World world;	
	      public static Player player;	
	       public static Random rand;	
	        public UI ui;
	
	public static String gameState = "MENU";
	 private boolean showMessageGameOver = true;
	 private boolean showMessageGameWins = true;
	  private int framesGameOver = 0;
	   private int framesGameWins = 0;
	    public static boolean restartGame = false;	
	     public Menu menu;
	      public static int[] pixels;
	       public static int[] lightMap;
	        public static int pegaMaca = 0;
	         public static int macaContagem = 0;
	         
	         // Sistema de pontuação
	         public static int score = 0;
	         public static int highScore = 0;
	         public static long levelStartTime;
	         public static long totalGameTime = 0;
	         public static boolean isFirstLevel = true;
	         
	         // Sistema de dificuldade
	         public static double difficultyMultiplier = 1.0;
	         public static int enemiesPerLevel = 0;
	
	public Game(){
	//Sound.Music_01.loop();
	 rand = new Random();
	  addKeyListener(this);
	   addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		 initFrame();
		//Inicializando objetos.
	ui = new UI();
	 lightMap = new int[WIDTH*HEIGHT];
	  image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	   pixels =((DataBufferInt)image.getRaster().getDataBuffer()).getData();		
		entities = new ArrayList<Entity>();		
	enemies = new ArrayList<Enemy>();		
	 spritesheet = new Spritesheet("/spritesheet.png");
	  player = new Player(0,0,28,28,spritesheet.getSprite(0, 32,28,28));
	   entities.add(player);
		world = new World("/level1.png");
		
		
		menu = new Menu();
	}
	
	public void initFrame(){
		frame = new JFrame("Ache as Maçãs");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
	    if (gameState.equals("NORMAL")) {
	        Game.restartGame = false; // Reseta apenas aqui
	        
	        // Inicializar tempo do nível se for o primeiro
	        if (isFirstLevel) {
	            levelStartTime = System.currentTimeMillis();
	            isFirstLevel = false;
	        }
	        
	        for (int i = 0; i < entities.size(); i++) {
	            Entity e = entities.get(i);
	            e.tick();
	        }
	        
	        // Avançar para o próximo nível
	        if (Game.pegaMaca == Game.macaContagem) {
	            // Calcular pontuação do nível baseada no tempo
	            long levelTime = System.currentTimeMillis() - levelStartTime;
	            int timeBonus = Math.max(0, 10000 - (int)(levelTime / 100)); // Bônus de tempo
	            int healthBonus = (int)(Game.player.life * 50); // Bônus de vida
	            int difficultyBonus = (int)(CUR_LEVEL * 200 * difficultyMultiplier); // Bônus de dificuldade
	            int levelScore = timeBonus + healthBonus + difficultyBonus;
	            Game.score += levelScore;
	            
	            // Aumentar dificuldade
	            Game.difficultyMultiplier += 0.1;
	            
	            CUR_LEVEL++;
	            if (CUR_LEVEL > MAX_LEVEL) {
	                // Jogo completo - salvar high score
	                if (Game.score > Game.highScore) {
	                    Game.highScore = Game.score;
	                }
	                CUR_LEVEL = 1;
	                Game.gameState = "GAME_WIN";
	            }
	            String newWorld = "level" + CUR_LEVEL + ".png";
	            World.restartGame(newWorld);
	            levelStartTime = System.currentTimeMillis(); // Resetar tempo para próximo nível
	        }
	    } else if (gameState.equals("GAME_OVER")) {
	        this.framesGameOver++;
	        if (this.framesGameOver == 30) {
	            this.framesGameOver = 0;
	            this.showMessageGameOver = !this.showMessageGameOver;
	        }
	        
	        // Reiniciar se o jogador pressionou "Enter"
	        if (restartGame) {
	            Game.restartGame = false;
	            Game.gameState = "NORMAL"; // Voltar ao estado normal
	            CUR_LEVEL = 1; // Reinicia para o primeiro nível
	            Game.score = 0; // Resetar pontuação
	            Game.isFirstLevel = true; // Resetar flag do primeiro nível
	            String newWorld = "level" + CUR_LEVEL + ".png";
	            World.restartGame(newWorld);
	        }
	    } else if (gameState.equals("MENU")) {
	        Game.restartGame = false; // Reseta no menu
	        menu.tick();
	    } else if (gameState.equals("GAME_WIN")) {
	        this.framesGameWins++;
	        if (this.framesGameWins == 30) {
	            this.framesGameWins = 0;
	            this.showMessageGameWins = !this.showMessageGameWins;
	        }
	        
	        // Reiniciar se o jogador pressionou "Enter"
	        if (restartGame) {
	            Game.restartGame = false;
	            Game.gameState = "NORMAL"; // Voltar ao estado normal
	            CUR_LEVEL = 1; // Reinicia para o primeiro nível
	            Game.score = 0; // Resetar pontuação
	            Game.isFirstLevel = true; // Resetar flag do primeiro nível
	            String newWorld = "level" + CUR_LEVEL + ".png";
	            World.restartGame(newWorld);
	        }
	    }
	    
	}
	

	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);	
		entities.sort(Comparator.comparingInt(Entity::getDepth));//para utilização de profundidade das entities
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		ui.render(g);
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("arial",Font.BOLD,20));
		g.setColor(Color.white);		
		if (gameState.equals("GAME_OVER")) { // Comparação segura de Strings
		    Graphics2D g2 = (Graphics2D) g;

		    // Fundo semi-transparente
		    g2.setColor(new Color(0, 0, 0, 100));
		    g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		    // Texto "Game Over"
		    g.setFont(new Font("Arial", Font.BOLD, 36));
		    g.setColor(Color.WHITE);
		    String gameOverText = "Game Over";
		    int textWidth = g.getFontMetrics().stringWidth(gameOverText); // Calcula a largura do texto
		    g.drawString(gameOverText, (WIDTH * SCALE - textWidth) / 2, (HEIGHT * SCALE) / 2 - 20);

		    // Mensagem para reiniciar
		    g.setFont(new Font("Arial", Font.BOLD, 32));
		    String restartMessage = ">Pressione Enter para reiniciar<";
		    if (showMessageGameOver) {
		        int restartWidth = g.getFontMetrics().stringWidth(restartMessage); // Calcula a largura do texto
		        g.drawString(restartMessage, (WIDTH * SCALE - restartWidth) / 2, (HEIGHT * SCALE) / 2 + 40);
		    }
		    
		} else if (gameState.equals("MENU")) { // Comparação segura de Strings
		    menu.render(g); // Renderiza o menu
		} else if (gameState.equals("GAME_WIN")) {
		    Graphics2D g2 = (Graphics2D) g;

		    // Fundo semi-transparente
		    g2.setColor(new Color(0, 0, 0, 100));
		    g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		    // Texto "Parabéns!"
		    g.setFont(new Font("Arial", Font.BOLD, 36));
		    g.setColor(Color.GREEN);
		    String winText = "Parabéns!";
		    int textWidth = g.getFontMetrics().stringWidth(winText);
		    g.drawString(winText, (WIDTH * SCALE - textWidth) / 2, (HEIGHT * SCALE) / 2 - 60);

		    // Pontuação final
		    g.setFont(new Font("Arial", Font.BOLD, 24));
		    g.setColor(Color.YELLOW);
		    String scoreText = "Pontuação Final: " + Game.score;
		    int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
		    g.drawString(scoreText, (WIDTH * SCALE - scoreWidth) / 2, (HEIGHT * SCALE) / 2 - 20);

		    // Mensagem para reiniciar
		    g.setFont(new Font("Arial", Font.BOLD, 20));
		    g.setColor(Color.WHITE);
		    String restartMessage = ">Pressione Enter para jogar novamente<";
		    if (showMessageGameWins) {
		        int restartWidth = g.getFontMetrics().stringWidth(restartMessage);
		        g.drawString(restartMessage, (WIDTH * SCALE - restartWidth) / 2, (HEIGHT * SCALE) / 2 + 40);
		    }
		}

		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();		
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;			
			if(gameState == "MENU") {
				menu.down = true;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X){
			player.shoot = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {    
		    if (gameState.equals("GAME_OVER")) {
		        // Reinicia o jogo ao pressionar Enter no estado GAME_OVER
		        restartGame = true;
		    } else if (gameState.equals("MENU")) {
		        menu.enter = true; // Interação no menu
		    } else if (gameState.equals("GAME_WIN")) {
		        // Reinicia o jogo ao pressionar Enter no estado GAME_WIN
		        restartGame = true;
		    }
		}

		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX() / 3);
		player.my = (e.getY() / 3);
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
