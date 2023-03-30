//window class handles visual aspects of the game

package com.projectile_launcher.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class Window {
	
	public static JButton startButton;
	public static JButton stopButton;
	public static JSlider gravitySlider;
	public static JLabel gravityLabel;
	public static JSlider velocitySlider;
	public static JLabel velocityLabel;
	public static JSlider angleSlider;
	public static JLabel angleLabel;
	
	public Window(int width, int height, String title, Game game)
	{
		//crating window
		JFrame frame = new JFrame(title); 
		
		frame.setLayout(null);
		
		frame.setSize(width + width / 2, height);
		
		//creating the backround panel
		JPanel backPanel =  createPanel(new JPanel(), null, 0, 0, width + width / 2, height, Color.GRAY);
		
		//creating the foreground panel which holds the simulation
		JPanel gamePanel = createPanel(new JPanel(), new BorderLayout(), width / 2, 0, width, height, Color.black);
		
		gamePanel.add(game);
		
		backPanel.add(gamePanel);
		
		startButton = createButton(new JButton(), "LAUNCH", (width/4)-50, (height / 4) * 3, 100, 25);
		
		stopButton = createButton(new JButton(), "CANCEL", (width/4)-50, (height / 4) * 3, 100, 25);
		stopButton.setVisible(false);
		
		gravitySlider = createSlider(new JSlider(), 0, 20, 9, (width/4)-100, (int) ((height / 4) * 0.5), 200, 50, 1, 10);
		
		gravityLabel = createLabel(gravityLabel, "GRAVITY (N)", (width/4)-100, (int) ((height / 4) * 0.25), 200, 50);
		
		velocitySlider = createSlider(new JSlider(), 0, 20, 10, (width/4)-100, (int) ((height / 4) * 1.25), 200, 50, 1, 10);
		
		velocityLabel = createLabel(velocityLabel, "VELOCITY INITIAL (M/S)", (width/4)-100, (height / 4), 200, 50);
		
		angleSlider = createSlider(new JSlider(), 0, 90, 45, (width/4)-100, (int) ((height / 4) * 2), 200, 50, 5, 45);
		
		angleLabel = createLabel(angleLabel, "LAUNCH ANGLE (Θ)", (width/4)-100, (int) ((height / 4) * 1.75), 200, 50);
		
		backPanel.add(gravitySlider);
		backPanel.add(gravityLabel);
		backPanel.add(velocitySlider);
		backPanel.add(velocityLabel);
		backPanel.add(angleSlider);
		backPanel.add(angleLabel);
		backPanel.add(startButton);
		backPanel.add(stopButton);
		
		frame.add(backPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		try {
  			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
  		} catch(Exception e){
  			  e.printStackTrace(); 
  		}
		
		frame.setVisible(true);
		game.start();
	}
	
	public static JPanel createPanel(JPanel panel, LayoutManager layout, int x, int y, int width, int height, Color backgroundColor) {
		panel = new JPanel(layout);
		panel.setBounds(x, y, width, height);
		panel.setBackground(backgroundColor);
		return panel;
	}
	
	public static JLabel createLabel(JLabel label, String text, int x, int y, int width, int height) {
		label = new JLabel(text);
		label.setBounds(x, y, width, height);
		return label;
	}
	
	public static JSlider createSlider(JSlider slider, int min, int max, int start, int x, int y, int width, int height, int minor, int major) {
		slider = new JSlider(min,max,start);
		
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(minor);
		  
		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(major);
		slider.setPaintLabels(true);
		
		slider.setBounds(x, y, width, height);
		return slider;
	}
	
	public static JButton createButton(JButton button, String text, int x, int y, int width, int height) {
		button = new JButton(text);
		button.addActionListener(new KeyInput());
		button.setBounds(x, y, width, height);
		button.setBackground(Color.black);
		button.setForeground(Color.GRAY);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(true);
		return button;
	}
}
