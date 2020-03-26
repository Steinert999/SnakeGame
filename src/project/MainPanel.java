package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import characters.Apple;
import characters.EntityCharactersImpl;
import characters.Rock;
import characters.SnakePart;

public class MainPanel extends JPanel implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	private Thread thread;
	private long targetTime;
	private Graphics2D g2d;
	private BufferedImage image;

	private int level;
	private boolean gameOver;
	
	public static final int 
	BOARD_WIDTH = 450, 
	BOARD_HEIGHT = 450,
	BOARD_X = 25,
	BOARD_Y = 40;
	
	
	//snake attributes
	private List<SnakePart> snake;
	private SnakePart head;
	private Apple apple;
	private Rock rock;
	private int dx, dy, score;
	private boolean	up,	down, left,	right, start;
	
	private Random random;
	
	public MainPanel() {
		random = new Random();
		setPreferredSize(new Dimension(500, 500));
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
		
	
	@Override
	public void paint(Graphics g) {}
	
	@Override
	public void run() {
		if(running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while (running) {
			startTime = System.nanoTime();
			update();
			requestRender();
			elapsed = System.nanoTime() - startTime;
			wait = targetTime - elapsed / 1000000;
			if(wait > 0) {
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

	
	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_UP) up = true;
		if(k == KeyEvent.VK_DOWN) down = true;
		if(k == KeyEvent.VK_RIGHT) right = true;
		if(k == KeyEvent.VK_LEFT) left = true;
		if(k == KeyEvent.VK_ENTER) start = true;
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_UP) up = false;
		if(k == KeyEvent.VK_DOWN) down = false;
		if(k == KeyEvent.VK_RIGHT) right = false;
		if(k == KeyEvent.VK_LEFT) left = false;
		if(k == KeyEvent.VK_ENTER) start = false;
		
	}
	
	
	private void setFPS(int fps) {
		targetTime = 1000/fps;
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	
	private void init() {
		image = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.g2d = image.createGraphics();
		running = true;
		setUpLevel();
		gameOver = false;
		level = 1;
		setFPS(level * 10);
	}
	private void setUpLevel() {
		score = 0;
		gameOver = false;
		level = 0;
		targetTime = 100;
		dx = dy = 0;
		this.snake = new ArrayList<SnakePart>();
		this.head = new SnakePart(BOARD_WIDTH / 2,  BOARD_HEIGHT / 2);
		this.head.setColor(Color.green);
		
		snake.add(head);
		for(int i = 1; i < 4; i++) {
			SnakePart s = new SnakePart(head.getxCoor() + (i * head.getSize() ), head.getyCoor());
			snake.add(s); 
		}
		
		setApple();
		setRock();
	}
	
	private void update(){
		if(gameOver) {
			if(start) {
				setUpLevel();
			}
			return;
		}
		
		if(up && dy == 0) {
			dy = -EntityCharactersImpl.SIZE;
			dx = 0;
		}
		
		if(down && dy == 0) {
			dy = EntityCharactersImpl.SIZE;
			dx = 0;
		}
		
		if(left && dx == 0) {
			dy = 0;
			dx = -EntityCharactersImpl.SIZE;
			
		}
		if(right && dx == 0 && dy != 0) {
			dy = 0;
			dx = EntityCharactersImpl.SIZE;
			
		}
		
		if(dx != 0 || dy != 0) {
			
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setxCoor(snake.get(i - 1).getxCoor());
				snake.get(i).setyCoor(snake.get(i - 1).getyCoor());
			}
			head.move(dx, dy);
			
			for(SnakePart s : snake) {
				if(s.isCollision(head)) {
					gameOver = true;
					break;
				}
				
			}			
			
			if(level > 2 ) 
				if(head.isCollision(rock)){
					gameOver = true;
				}
			
			
			if(apple.isCollision(head)) {
				score++;
				setApple();
				
				SnakePart s = new SnakePart(-100, -100);
				snake.add(s);
				
				
				if( score % 10 == 0) {
					level++;
					if(level > 10) level = 10;
					setFPS(level * 10); 
				}
			}
			
		}
		
		
		
		if(head.getxCoor() < 45) head.setxCoor(BOARD_WIDTH -5);
		if(head.getyCoor() < 45) head.setyCoor(BOARD_HEIGHT - 5);
		if(head.getxCoor() > BOARD_WIDTH) head.setxCoor(45);
		if(head.getyCoor() > BOARD_HEIGHT) head.setyCoor(45);
	}
	
	private void requestRender() {
		
		render(g2d);
		Graphics g = getGraphics();
		g.setColor(Color.WHITE);
		g.drawRect(35, 10, 425, 25);
		
		g.setColor(Color.WHITE);
		g.drawString("Snake Game", 220, 28);
		
		g.setColor(Color.WHITE);
		g.drawRect(44, 44, BOARD_WIDTH - 44, BOARD_HEIGHT - 44);
		g.drawImage(image,0,0, null);
		
	
		
		g.dispose();

	}
	
	private void render(Graphics2D g2d) {
		
		g2d.clearRect(45, 45, BOARD_WIDTH, BOARD_HEIGHT);
		g2d.drawString("Score: " + score, 50, 55);
		g2d.drawString("Level: " + level, 52, 70);
		
		if(gameOver) {
			g2d.drawString("Game Over !!", 225, 225);
			g2d.drawString("Press ENTER to restart", 225, 250);
		}
		if(dx == 0 && dy == 0) {
			g2d.drawString("Ready!!", 225, 200);
		}
		
				
		for (SnakePart s : snake) {
			s.render(g2d);
		}	
		
		apple.render(g2d);
		
		if(level > 2) {
			rock.render(g2d);
		}
		
	}	
	public void setApple() {
		Point p = getRandomPoint();
		apple = new Apple(p.x, p.y);
		apple.setColor(Color.RED);
	}
	
	public void setRock() {
		Point p = getRandomPoint();
		rock = new Rock(p.x, p.y);
		rock.setColor(new Color(205,133,63));
	}
	
	
	private Point getRandomPoint() {
		int x = getRandomNumberInRange(45, BOARD_WIDTH - 45);
		int y = getRandomNumberInRange(45, BOARD_WIDTH - 45); 
		for (SnakePart snakePart : snake) {
			if(snakePart.getxCoor() == x || snakePart.getyCoor() == y) {
				x = getRandomNumberInRange(45, BOARD_WIDTH - 45);
				y = getRandomNumberInRange(45, BOARD_HEIGHT - 45);
			}
		}
		return new Point(x, y);
	}
	
	
	private int getRandomNumberInRange(int min, int max) {
		
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return random.nextInt((max - min) + 1) + min;
	}
	
}
