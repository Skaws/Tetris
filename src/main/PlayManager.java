package main;

import entity.RowManager;
import entity.ShapeHandler;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.FileWriter;
import java.io.IOException;
import tile.BoxManager;
import tile.TileManager;
public class PlayManager {
	int currFrame = 0;
	GamePanel gp;
	FileWriter fWriter;
    TileManager tileM;
    BoxManager boxM;
    public ShapeHandler sHandler;
	RowManager rowM;
	public int dropInterval = 60;
	int[] scoreList = {212385,189357,165840,143823,128900};
	String[] playerList = {"AKing","RDJ","T-Spin","AJK", "LBlock"};
	public PlayManager(GamePanel gp) {
		this.gp = gp;
		sHandler = gp.sHandler;
		tileM=gp.tileM;
		boxM = gp.boxM;
		rowM = new RowManager();
	}
	public void update() {
		currFrame++;
		//System.out.println("Currently on frame: " +currFrame);
		if(currFrame>=dropInterval){
			sHandler.moveShape(sHandler.mainShape,"auto down");
			currFrame=0;
		}
		if(sHandler.shapeSliding==true){
			System.out.println(" SUPER SHAPE SLIDING TIME");
			System.out.println(" slide counter is at: " +sHandler.slideCounter);
			sHandler.slideCounter++;
			if(sHandler.slideCounter>=45 && sHandler.underShape==true){
				sHandler.restShape();
				sHandler.shapeSliding=false;
			}
		}
	}
	public void drawTextInterface(Graphics2D g2){

		g2.setColor(Color.WHITE);
		g2.drawRect(12 * gp.tileSize, 4*gp.tileSize, 12*gp.tileSize, 12*gp.tileSize);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(12 * gp.tileSize, 4*gp.tileSize, 12*gp.tileSize, 12*gp.tileSize);
		g2.setColor(Color.WHITE);
		
		g2.setStroke(new BasicStroke(10f));
		g2.setFont(new Font("Arial", Font.PLAIN, 40));
		g2.drawString("NEW HIGH SCORE!", (gp.screenWidth/2) - (gp.tileSize *5), 5*gp.tileSize+10);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 35));
		g2.drawString("Enter Username Below:", (gp.screenWidth/2) - (gp.tileSize *5), 8*gp.tileSize+10);
	}
	public void drawScoreboard(Graphics2D g2){

		g2.setColor(Color.WHITE);
		g2.drawRect(12 * gp.tileSize, 4*gp.tileSize, 12*gp.tileSize, 12*gp.tileSize);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(12 * gp.tileSize, 4*gp.tileSize, 12*gp.tileSize, 12*gp.tileSize);
		g2.setColor(Color.WHITE);
		
		g2.setStroke(new BasicStroke(10f));
		g2.setFont(new Font("Arial", Font.PLAIN, 50));
		g2.drawString("GAME OVER!", (gp.screenWidth/2) - (gp.tileSize *4), 5*gp.tileSize+10);

		// Set font size for the score text
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		// go through each of the top 5 high scores
		for(int i=0; i<5; i++){
			g2.drawString(playerList[i] +"'s Score Was: " + scoreList[i], (gp.screenWidth/2) - ((gp.tileSize *11)/2), (8+i)*gp.tileSize+10);
		}
	}
	public void draw(Graphics2D g2){
		
        int xOffset = 25 * gp.tileSize;
        int yOffset = 4 * gp.tileSize;
		
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(10f));
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SCORE:" + gp.score, xOffset + gp.tileSize + 12, yOffset - 20);
		
        g2.drawString("LEVEL:" + gp.level, xOffset + gp.tileSize + 12, yOffset - 20 +gp.tileSize*2);

        g2.drawString("LINES:" + gp.lines, xOffset + gp.tileSize + 12, yOffset - 20 +gp.tileSize*4);
		if(gp.gameOver==true){
			if(gp.enterScore==true){
				drawTextInterface(g2);
			}
			else{
				drawScoreboard(g2);
			}
			
			
		}
		if(gp.pauseGame==true){
			g2.setColor(Color.YELLOW);
			
			g2.setStroke(new BasicStroke(10f));
			g2.setFont(new Font("Arial", Font.PLAIN, 50));
			g2.drawString("PAUSED!", (gp.screenWidth/2) - (gp.tileSize *3), gp.screenHeight/2);
		}
		
	}
	public void writeScore(){
		try {
			fWriter = new FileWriter("scoreboard.txt",true);
			fWriter.append(("Score was:" + gp.score));
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
