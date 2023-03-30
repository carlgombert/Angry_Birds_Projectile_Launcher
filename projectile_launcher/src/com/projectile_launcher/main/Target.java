package com.projectile_launcher.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Target {
	
	public Game game;
	
	public Image pig;
	
	private double x, y;
	
	public Target(Game game) {
		randomPosition();
		this.game = game;
		pig = game.addImage(100, 100, "res/pig.png");
	}
	
	public void render(Graphics g) {
			
		g.drawImage(pig, (int) Math.round(x), (int) Math.round(y) - 60, 40, 40, null);
			
	}
	
	public void tick() {
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) Math.round(x), (int) Math.round(y) - 60, 30, 30);
	}
	
	public void randomPosition() {
		y = randY() + 60;
		x = randX();
	}
	
	public int randX() {
		int max = Game.WIDTH - 50;
		int min = 50;
		int randInt = (int)Math.floor(Math.random() * (max - min + 1) + min);
		return randInt;
	}
	
	public int randY() {
		int max = Game.HEIGHT - 50;
		int min = 50;
		int randInt = (int)Math.floor(Math.random() * (max - min + 1) + min);
		return randInt;
	}
}
