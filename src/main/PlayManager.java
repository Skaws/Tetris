package main;

import entity.ShapeHandler;
import java.awt.Graphics2D;
import tile.BoxManager;
import tile.TileManager;

public class PlayManager {
	int currFrame = 0;
	GamePanel gp;
    TileManager tileM;
    BoxManager boxM;
    public ShapeHandler sHandler;
	public PlayManager(GamePanel gp) {
		this.gp = gp;
		sHandler = gp.sHandler;
		tileM=gp.tileM;
		boxM = gp.boxM;
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

	}
}
