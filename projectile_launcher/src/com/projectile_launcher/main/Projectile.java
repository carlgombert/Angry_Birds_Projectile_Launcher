/*the projectile class handles the projectile and handles all of its own physics.
all of the physics for the whole game here*/

package com.projectile_launcher.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Projectile {
	
	public boolean launching = false;
	public boolean friction = false;

	public Game game;
	public Image bird;
	
	public double gravity, mewKineticFriction = 0.5;
	public double mass = 10.0;
	
	private double x, y;
	
	private double startTime = System.currentTimeMillis(), time = 0;
	
	private double velocityInitial = 13;
	private double angle = (Math.PI) / 4;
	private double xInitial, yInitial;
	
	private double velocity;
	private double velocityX, velocityY;
	
	private double changeInX, changeInY;
	
	//initializing the projectile
	public Projectile(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		
		bird = game.addImage(100, 100, "res/bird.png");
	}
	
	//render method updates graphics for the projectile
	public void render(Graphics g) {
		
		//drawing the bird
		g.drawImage(bird, (int) Math.round(x), (int) Math.round(y) - 60, 40, 40, null);
		
		//writing the data at the bottom of screen
		g.drawString("V: " + Math.round(velocity) + " m/s, Δt: " + (Math.round(time * 10) / 10.0) + " s, ΔX: " + (Math.round(changeInX * 100) / 100.0) + " m,  ΔY: " + (Math.round(changeInY * 100) / 100.0) + " m", Game.WIDTH / 2 - 150, Game.HEIGHT - 50);
	}
	
	//tick method updates data for the projectile
	public void tick() {
		if(launching) {
			time = (System.currentTimeMillis() - startTime) / 1000;
			
			projectileMotion();
			
			//if the bird hits the ground it is no longer in projectile motion so I switch to friction
			if(y > (double) (Game.HEIGHT) + 0.0000001) {
				startFriction();
			}
		}
		if(friction) {
			time = (System.currentTimeMillis() - startTime) / 1000;
			
			friction();
			
			//if the bird stops moving there is no more kinetic friction
			if(Math.round(velocity) == 0) {
				friction = false;
			}
			
		}
	}
	
	//initial launch of the bird. takes all the variables needed for projectile motion as parameters
	public void launch(double gravity, double velocityInitial, double angle, double Xi, double Yi) {
		Window.startButton.setVisible(false);
		Window.stopButton.setVisible(true);
		this.gravity = gravity;
		this.velocityInitial = velocityInitial;
		this.angle = Math.toRadians(angle);
		startTime = System.currentTimeMillis();
		xInitial = Xi;
		yInitial = Yi;;
		launching = true;
		friction = false;
	}
	
	//canceles the simulation and resets the bird to initial position
	public void cancelLaunch() {
		launching = false;
		friction = false;
		Window.startButton.setVisible(true);
		Window.stopButton.setVisible(false);
		x = 0; 
		y = Game.HEIGHT;
	}
	
	//begins the friction calculations
	public void startFriction(){
		startTime = System.currentTimeMillis();
		velocityInitial = velocityX;
		velocityY = 0;
		xInitial = x / 43.0;
		yInitial = (Game.HEIGHT - y) / 43.0;
		friction = true;
		launching = false;
	}
	
	//returns the general area of the bird
	public Rectangle getBounds() {
		return new Rectangle((int) Math.round(x), (int) (Math.round(y) - 50), 30, 30);
	}
	
	//calculates friction
	public void friction() {
		y = Game.HEIGHT - frictionY();
		x = frictionX();
		velocity = velocityX;
	}
	
	//calculates change in X and velocity in X during friction. physics is shown below
	public double frictionX() {
		changeInX = velocityInitial * time + 0.5 * (-1 * gravity * mewKineticFriction) * Math.pow(time, 2);
		velocityX = velocityInitial + (-1 * gravity * mewKineticFriction) * time;
		return (xInitial + changeInX) * 43;
		/*
		 ΣFy = FN - Fg = ma y = m (0) = 0
		 => FN = Fg = mg
		 
		 ΣFx = -Fkf = ma x
		 => -FN µk = ma x
		 => -mg µk = ma x
		 => -g µk = a x
		 
		 Vfx = Vix + ax Δt
		 => Vfx = Vix + (-g µk) Δt
		 
		 Δx = VixΔt + 0.5 ax Δt^2
		 => Δx = VixΔt + 0.5 (-g µk) Δt^2
		 
		 */
	}
	
	//calculates the friction in the Y direction. will always be zero because the bird is on the
	//ground and no forces are acting on it besides the force of gravity and force normal
	public double frictionY() {
		changeInY = 0;
		velocityY = 0;
		return (yInitial + changeInY) * 43;
	}
	
	//calculates the y and x positions and velocities during projectile motion
	public void projectileMotion() {
		//y has to be the screen hight minus the Y position because computer pixels dont start at the
		//bottom left of the screen, (0, 0) is at the top left of the screen
		y = Game.HEIGHT - projectileMotY();
		x = projectileMotX();
		velocity = Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
		// V = √ Vx^2 + Vy^2
	}
	
	//calculating the change in X and velocity in the X during projectile motion. physics is shown below.
	public double projectileMotX() {
		changeInX = velocityInitial * Math.cos(angle) * time;
		velocityX = velocityInitial * Math.cos(angle);
		/*
		 	Δx = Vix(Δt) + 1/2(ax)Δt^2
		 
		 => Δx = (Vi cos(ø)) (Δt)
		 
		 => x = xi + Δx
		 
		 	Vfx = Vix + ay(Δt)
		 	
		 => Vfy = (Vi cos(ø))
		 
		*/
		//returning the change in X times 43 because the change in X is in meters and in my 
		//simulation 43 pixels is equal to one meter
		return (xInitial + changeInX) * 43;
	}
	
	//calculating the change in Y and velocity in the Y during projectile motion. physics is shown below.
	public double projectileMotY() {
		changeInY = velocityInitial * Math.sin(angle) * time + -0.5 * gravity * Math.pow(time, 2);
		velocityY = (velocityInitial * Math.sin(angle)) + (-1 * gravity * time);
		/*
		 	Δy = Viy(Δt) + 1/2(ay)Δt^2
		 
		 => Δy = (Vi sin(ø)) (Δt) + 1/2(-g)Δt^2
		 
		 => y = yi + Δy
		 
		 	Vfy = Viy + ay(Δt)
		 	
		 => Vfy = (Vi sin(ø)) + -g(Δt)
		 
		*/
		//returning the change in Y times 43 because the change in X is in meters and in my 
		//simulation 43 pixels is equal to one meter
		return (yInitial + changeInY) * 43;
	}
}
