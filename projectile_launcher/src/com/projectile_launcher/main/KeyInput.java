//this class handles all the player action in the program

package com.projectile_launcher.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyInput implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Window.startButton) {
			Game.projectile.launch(Window.gravitySlider.getValue(), Window.velocitySlider.getValue(), Window.angleSlider.getValue(), 0, 0);
		}
		if(e.getSource()==Window.stopButton) {
			Game.projectile.cancelLaunch();
			Game.target.randomPosition();
		}
	}
}
