package Tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import deckPackage.Cards;
import deckPackage.DarthMaulDeck;
import deckPackage.DarthVaderDeck;
import deckPackage.Deck;
import deckPackage.EmperorDeck;
import deckPackage.HanSoloDeck;
import deckPackage.JangoDeck;
import deckPackage.LukeSkyWalkerDeck;
import deckPackage.ObiWanDeck;
import deckPackage.SpriteSheet;
import deckPackage.YodaDeck;

public class TestSpriteSheet extends JFrame{
	
	private JPanel frame;
	private JButton draw;
	private SpriteSheet sprite;
	private BufferedImage currentCardImage;
	private Deck d;
	private Cards card;
	private int numCards;
	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;
	
	private final static ImageIcon cardBack = new ImageIcon(baseDir
			+ "DM_Cardback.jpg");
	
	public static void main(String[] args) {
		new TestSpriteSheet().setVisible(true);
	}
	
	public TestSpriteSheet () {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocation(100, 100);
		setLayout(null);
		setResizable(false);
		setTitle("Test Sprite Sheet");
		
		draw = new JButton();
		draw.setIcon(cardBack);
		draw.setSize(95,135);
		draw.setLocation(17, 40);
		draw.addActionListener(new DrawListener());
		add(draw);
		
		frame = new frame(); 
		frame.setSize(210, 300);
		frame.setLocation(117, 40);
		add(frame);
	}
	
	public class frame extends JPanel
	{
		public frame()
		{
			d = new YodaDeck("Yoda");
			sprite = new SpriteSheet(d);	
		}

		/**
		 * The paint method for this panel, this will be called any time the state
		 * of the panel changes
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// If there is no current card, then don't attempt to draw it
			if (currentCardImage != null)
				g2.drawImage(currentCardImage, 0, 0, null);
		}
	}
	
	private class DrawListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
	
			card = d.draw();
			d.graveYard(card);
			currentCardImage = sprite.getCardImage(card);

			repaint();
		
			
		}
		
	}
}
