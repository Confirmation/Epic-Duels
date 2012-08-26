package deckPackage;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BigSpriteSheet {
	
	private BufferedImage spriteSheet;
	private int defenseValue;
	private String character;
	private int specialNum;
	private String fileName;
	private String deckName;
	private int attackValue;
	private int row;
	private int col;
	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;
	
	
	public BigSpriteSheet(Deck d)
	{
		deckName = d.getName();
		setFileName();
		// Use ImageIO to read in the card sheet
		try
		{
			spriteSheet = ImageIO.read(new File(baseDir + fileName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	


	public BufferedImage getCardImage(Cards c)
	{
		defenseValue = c.getDefenseValue();
		attackValue = c.getAttackValue();
		character = c.getCharacter();
		specialNum = c.getCardNum();
		row = getRow();
		col = getCol();

		// The size of 1 card is 91 x 130
		// The call to getSubimage has the following parameters: startX, startY,
		// width, height
		// leaving these print statements for testing later
		//System.out.println("row: " + row);
		//System.out.println("col: " + col);
		//System.out.println("defenseValue: " + defenseValue);
		//System.out.println("attackValue: " + attackValue);
		//System.out.println("specialNum: " + specialNum);
		//System.out.println("character: " + character);
		
		BufferedImage thisCard = spriteSheet.getSubimage(210 * col, 300 * row,
				210, 300);

		return thisCard;
	}


	private int getCol() {
		if (character.equals("major") && specialNum == 0) {
			if (attackValue == 1 && defenseValue == 4) {
				return 0; 
			} else if (attackValue == 1 && defenseValue == 5) {
				return 1;
			} else if (attackValue == 2 && defenseValue == 2) {
				return 2;
			} else if (attackValue == 2 && defenseValue == 3) {
				return 3;
			} else if (attackValue == 2 && defenseValue == 4) {
				return 4; 
			} else if (attackValue == 3 && defenseValue == 1) {
				return 5; 
			} else if (attackValue == 3 && defenseValue == 2) {
				return 6;
			} else if (attackValue == 3 && defenseValue == 3) {
				return 7;
			} else if (attackValue == 4 && defenseValue == 1) {
				return 8;
			} else if (attackValue == 4 && defenseValue == 2) {
				return 9;
			} else if (attackValue == 5 && defenseValue == 1) {
				return 10;
			}
		} else if (character.equals("minor") && specialNum == 0) {
			if (attackValue == 1 && defenseValue == 2) {
				return 0; 
			} else if (attackValue == 1 && defenseValue == 4) {
				return 1;
			} else if (attackValue == 2 && defenseValue == 1) {
				return 2;
			} else if (attackValue == 2 && defenseValue == 3) {
				return 3;
			} else if (attackValue == 2 && defenseValue == 4) {
				return 4; 
			} else if (attackValue == 3 && defenseValue == 1) {
				return 5; 
			} else if (attackValue == 3 && defenseValue == 2) {
				return 6;
			} else if (attackValue == 4 && defenseValue == 1) {
				return 7;
			} else if (attackValue == 5 && defenseValue == 1) {
				return 8;
			}
		} else {
			if (specialNum == 1) {
				return 0;
			} else if (specialNum == 2) {
				return 1;
			} else if (specialNum == 3) {
				return 2; 
			} else if (specialNum == 4) {
				return 3; 
			} else if (specialNum == 5) {
				return 4; 
			} else if (specialNum == 6) {
				return 5;
			} else if (specialNum == 7) {
				return 6;
			}
		}
		return -1;
		
	}

   
	private int getRow() {
		if (character.equals("major") && specialNum == 0) {
			return 0;
		} else if (character.equals("minor") && specialNum == 0) {
			return 1;
		} else {
            return 2;
		}
	}
	
	private void setFileName() {
		if (deckName.equals("Darth Maul")) {
			fileName = "darthmaulbigsprite.gif";
		} else if (deckName.equals("Darth Vader")) {
			fileName = "darthvaderbigsprite.gif";
		} else if (deckName.equals("Emperor")) {
			fileName = "emperorbigsprite.gif";
		} else if (deckName.equals("Han")) {
			fileName = "hansolobigsprite.gif";
		} else if (deckName.equals("Jango")) {
			fileName = "jangobigsprite.gif";
		} else if (deckName.equals("Luke")) {
			fileName = "lukebigsprite.gif";
		} else if (deckName.equals("ObiWan")) {
			fileName = "obiwanbigsprite.gif";
		} else if (deckName.equals("Yoda")) {
			fileName = "yodabigsprite.gif";
		}
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getRowNum() {
		return row;
	}
	
	public int getColNum() {
		return col;
	}

}
