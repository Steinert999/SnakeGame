package project;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	
	public Main() {
		var jf = new JFrame("Snake Game");
		var main = new MainPanel();
		jf.setContentPane(main);
		jf.setResizable(false);
		jf.pack();
		jf.setBackground(Color.DARK_GRAY);
		jf.setPreferredSize(new Dimension(main.getWidth(), main.getHeight()));
		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Main();
	}

}
