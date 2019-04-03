package hangMan1;
	import java.awt.*;
	import javax.swing.*;
	import java.awt.event.*;
	import java.util.ArrayList;
	import java.util.concurrent.TimeUnit;
	@SuppressWarnings("serial")
	public class HangManPanel extends JPanel implements MouseListener{
		
		private int spaces = 0;
		private int rectWidth = 100;
		private int rectHeight = 100;
		private int xCoord = 50;
		private int yCoord = 50;
		private int xCoordDone = 400;
		private int yCoordDone = 520;
		private int yCoordAnswer = 850;
		private int xCoordSpace = 50;
		private int yCoordSpace = 650;
		private int xCoordLine = 50;
		private int misses = 0;
		private int index;
		private int count = 0;
		private int countAnswer = 0;
		private int numberForLetter;
		private boolean max = true;
		private boolean makingPassword = true;
		private boolean pressed = false;
		private boolean paint = false;
		private ArrayList<Integer> xCoordAnswerList = new ArrayList<Integer>();
		private ArrayList<ArrayList<Integer>> xyCoords = setXYCoords();
		private ArrayList<Integer> xCoordLetters = xyCoords.get(0);
		private ArrayList<Integer> yCoordArray = xyCoords.get(1);
		private String passphrase;
		private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		private ArrayList<String> alphaArray = makeArray();
		

		public HangManPanel(){
			setPreferredSize(new Dimension(1250,800));
			this.addMouseListener(this);
		}

		public void paintComponent(Graphics g){
			super.paintComponents(g);
			g.setFont(new Font("TimesRoman", Font.BOLD, 50));
			//creates alphabet in array
			//draws letters with boxes
			for(int i = 0; i <= alphaArray.size()-1; i++){
				g.setColor(Color.BLUE);
				g.fillRect(xCoordLetters.get(i), yCoordArray.get(i), rectWidth, rectHeight);
				//System.out.println(xCoordArray.get(i));
				//System.out.println(yCoordArray.get(i));
				g.setColor(Color.BLACK);
				g.drawString(alphaArray.get(i), xCoordLetters.get(i) + 35, yCoordArray.get(i) + 65);
			}
			//draws structure
			g.drawLine(1000, 850, 1200, 850);
			g.drawLine(1100, 850, 1100, 500);
			g.drawLine(1100, 500, 800, 500);
			g.drawLine(800, 500, 800, 550);
			g.setColor(Color.BLUE);
			//draws done button
			g.fillRect(xCoordDone, yCoordDone, 200, 80);
			//draws space button
			g.fillRect(xCoordSpace, yCoordSpace, 200, 80);
			g.setColor(Color.BLACK);
			g.drawString("SPACE", 75, 705);
			g.drawString("DONE", 425, 575);
			if(xCoordLine == 770 && max){
				pressed = false;
				g.drawString("MAX LETTERS: PRESS DONE", 50, 805);
			}
			if(!max){
				g.setColor(Color.WHITE);
				g.fillRect(50, 770, 705, 47);
				g.setColor(Color.BLACK);
			}
			if(makingPassword){
				if(pressed){
					if(count == 0){
						passphrase = alphaArray.get(numberForLetter);
					} else {
						passphrase += alphaArray.get(numberForLetter);
					}
					count++;
					xCoordAnswerList.add(60*count + 60*spaces);
					g.drawLine(xCoordLine, 860, xCoordLine + 50, 860);
					xCoordLine += 60;
					pressed = false;
					//System.out.println(passphrase);
				}
				paint = false;
			}
			
			if(!makingPassword && pressed){
				index = passphrase.indexOf(alphaArray.get(numberForLetter));
				//if letter not present in passphrase then counts as a miss
				if(index == -1){
					misses++;
					//System.out.println(misses);
					alphaArray.remove(numberForLetter); //removes guess from choices
					g.fillRect(xCoordLetters.get(numberForLetter), yCoordArray.get(numberForLetter),
							rectWidth, rectHeight);
					xCoordLetters.remove(numberForLetter);
					yCoordArray.remove(numberForLetter);
					pressed = false;
				} else {
					//checks and draws for all instances of the letter guessed
					for(int i = 0; i <= passphrase.length() -1; i++){
						if(passphrase.substring(i,i+1).equals(alphaArray.get(numberForLetter))){
							g.drawString(passphrase.substring(i, i + 1), xCoordAnswerList.get(i), yCoordAnswer);
							countAnswer++;
						}
					}
					alphaArray.remove(numberForLetter); //removes guess from choices
					g.fillRect(xCoordLetters.get(numberForLetter), yCoordArray.get(numberForLetter),
							rectWidth, rectHeight);
					xCoordLetters.remove(numberForLetter);
					yCoordArray.remove(numberForLetter);
					//System.out.println(alphaArray.size());
					pressed = false;
					if(countAnswer == count){
						g.drawString("YOU WIN!", 400, 500);
					}
				}
				paint = false;
			}
			//draws head
			if(misses == 1){
				g.drawOval(750, 550, 100, 100);
			}
			//draws body
			if(misses == 2){
				g.drawLine(800, 650, 800, 800);
			}
			//draws 1st leg
			if(misses == 3){
				g.drawLine(800, 800, 750, 850);
			}
			//  draws 2nd leg
			if(misses == 4){
				g.drawLine(800, 800, 850, 850);
			}
			//draws 1st arm
			if(misses == 5){
				g.drawLine(800, 700, 750, 650);
			}
			//draws 2nd arm
			if(misses == 6){
				g.drawLine(800, 700, 850, 650);
				g.setColor(Color.RED);
				for(int i = 0; i <= passphrase.length() -1; i++){
					g.drawString(passphrase.substring(i, i + 1), xCoordAnswerList.get(i), yCoordAnswer);
					countAnswer++;
				}
				g.setColor(Color.BLACK);
				g.drawString("GAME OVER", 400, 500);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int xmouse = e.getX();
			int ymouse = e.getY();
			for(int i = 0; i<=25-countAnswer-misses; i++){
				if(xCoordLetters.get(i) <= xmouse && xCoordLetters.get(i) + 100 >= xmouse && 
					yCoordArray.get(i) <= ymouse && yCoordArray.get(i) + 100 >= ymouse){
					pressed = true;
					numberForLetter = i;
					paint = true;
				}
			}
			if(xCoordDone <= xmouse && xCoordDone + 200 >= xmouse && 
				yCoordDone <= ymouse && yCoordDone + 80 >= ymouse){
				makingPassword = false;
				max = false;
			}
			if(xCoordSpace <= xmouse && xCoordSpace + 200 >= xmouse &&
				yCoordSpace <= ymouse && yCoordSpace + 80 >= ymouse){
				spaces++;
				xCoordLine += 60;
				paint = true;
			}
			if(paint = true){
				repaint();
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
		
		public ArrayList<String> makeArray(){
			ArrayList<String> aa = new ArrayList<String>();
			for(int i = 0; i<=25; i++){
				aa.add(alphabet.substring(i,i+1));
				//System.out.println(i + "\t" + aa.get(i));
			}
			//System.out.println(aa.size());
			return aa;
		}
		
		public ArrayList<ArrayList<Integer>> setXYCoords(){
			ArrayList<Integer> xList = new ArrayList<Integer>();
			ArrayList<Integer> yList = new ArrayList<Integer>();
			ArrayList<ArrayList<Integer>> xyCoordsMethod = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i<=25; i++){
				xList.add(xCoord);
				yList.add(yCoord);
				xCoord += 150;
				if(xCoord == 1250){
					yCoord += 150;
					xCoord = 50;
				}
			}
			xyCoordsMethod.add(xList);
			xyCoordsMethod.add(yList);
			return xyCoordsMethod;
			
		}
	
}

