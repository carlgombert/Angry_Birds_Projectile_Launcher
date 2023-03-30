//game class handles all the functionality of the game

package com.projectile_launcher.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -5505267217615912489L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 15 * 11;
	private Thread thread;
	private boolean running = false;
	public static Projectile projectile;
	public static Target target;
	public Image backDrop = addImage(WIDTH, HEIGHT, "res/backdrop.png");
	public Image distanceGuide = addImage(WIDTH, HEIGHT, "res/distanceGuide.png");
	
	
	public Game () {
		projectile = new Projectile(0, HEIGHT, this);
		target = new Target(this);
		new Window(WIDTH, HEIGHT, "PROJECTILE LAUNCHER", this);
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
	//starts running the game
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	//stops running the game
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//runtime algorthm THIS ISN'T MINE, I believe this is the runtime algoritm from minecraft or
	//another popular Java game
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
			
		}
		stop();
	}
	
	//updates data for projectile and target as well as checks for collisions
	private void tick() {
		projectile.tick();
		target.tick();
		collision();
	}
	
	//if the projectile and target intersect they are both removed from the screen and the round is
	//reset
	public void collision() {
		if(projectile.getBounds().intersects(target.getBounds())) {
			Game.projectile.cancelLaunch();
			Game.target.randomPosition();
		}
	}
	
	//updating graphics for the whole game
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//drawing backround
		g.drawImage(backDrop, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(distanceGuide, 0, 0, WIDTH, HEIGHT, null);
		
		//rendering game objects
		projectile.render(g);
		target.render(g);
		
		g.dispose();
		bs.show();
	}
	
	//method to upload files into my program as images
	public Image addImage(int width, int height, String file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
}
