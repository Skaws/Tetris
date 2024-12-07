package main;

import entity.RowManager;
import entity.ShapeHandler;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import tile.BoxManager;
import tile.TileManager;

public class PlayManager {
	int currFrame = 0;
	GamePanel gp;
    TileManager tileM;
    BoxManager boxM;
    public ShapeHandler sHandler;
	RowManager rowM;
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
		if(currFrame>=60){
			
			sHandler.moveShape(sHandler.mainShape,"auto down");
			currFrame=0;
		}
		if(sHandler.shapeSliding==true){
			System.out.println(" SUPER SHAPE SLIDING TIME");
			System.out.println(" slide counter is at: " +sHandler.slideCounter);
			sHandler.slideCounter++;
			if(sHandler.slideCounter>=60 && sHandler.underShape==true){
				sHandler.restShape();
				sHandler.shapeSliding=false;
			}
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

		if(gp.pauseGame==true){
			g2.setColor(Color.YELLOW);
			
			g2.setStroke(new BasicStroke(10f));
			
		g2.setFont(new Font("Arial", Font.PLAIN, 50));
			g2.drawString("PAUSED!", (gp.screenWidth/2) - (gp.tileSize *3), gp.screenHeight/2);
		}
	}
}
