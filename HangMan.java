package hangMan1;

import javax.swing.JFrame;
import java.awt.Color;

public class HangMan {
	
	public static void main(String args[]){
		JFrame f = new JFrame("HANGMAN!!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.WHITE);
		HangManPanel hm = new HangManPanel();
		f.add(hm);
		f.pack();
		f.setVisible(true);
	}

}