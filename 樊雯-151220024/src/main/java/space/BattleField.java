package space;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import loginfo.CreatureInfo;
import loginfo.FrameInfo;
import thing.creature.Creature;
import thing.creature.CreatureState;
import thing.creature.Monster;
import thing.creature.Huluwa;
import thing.creature.Good;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BattleField implements Constants{
	private Creature[][] battlePlace = new Creature[Constants.ROW][Constants.COLUMN];

	
	private ExecutorService battleEventThreadPool;

	public BattleField() {

		battlePlace = new Creature[COLUMN][ROW]
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				battlePlace[i][j] = new Creature() ;
			}
		}
		
	}
	
	public boolean setCreatrue(Creature creature, int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column)
			return false;
		if (coords[x][y].setCreature(creature)) {
			creature.setPosition(x, y);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String ret = "BattleField\n";
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					ret += (coords[i][j].getCreatrue().getName() + "\t");
				} else {
					ret += "*\t";
				}
			}
			ret += "\n";
		}		
		return ret;
	}
	
	public void clearAll() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				coords[i][j].clearCreature();
			}
		}
	}
	
	public void clearCreature(int x, int y) {
		coords[x][y].clearCreature();
	}
	
	public boolean existCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			System.err.println("error at BattleField.existCreature");
			return true;
		}
		else {
			return coords[x][y].existCreature();
		}
	}
	
	public boolean existGoodCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			return false;
		} else {
			if (coords[x][y].existCreature() && coords[x][y].getCreatrue() instanceof Good) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean existBadCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			return false;
		} else {
			if (coords[x][y].existCreature() && coords[x][y].getCreatrue() instanceof Monster) {
				return true;
			} else {
				return false;
			}
		}
	}

	public void guiDisplay(Canvas canvas, ArrayList<FrameInfo> frameInfos) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		FrameInfo frameInfo = new FrameInfo();
		
		/* 绘制生物 */
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Creature creature = coords[i][j].getCreatrue();
					Image image = creature.getImage();
			        gc.drawImage(image, j*COORDWIDTH, i*COORDHEIGHT, PicLength, PicLength);
			        
			        CreatureInfo creatureInfo = new CreatureInfo(creature.getName(), i, j);
			        creatureInfo.setState(creature.getState());

			        if (creature.getState() != CreatureState.DEAD) {
			        	gc.setLineWidth(0);
				        double pct = creature.getHPPCT();
				        creatureInfo.setHpPCT(pct);
				        gc.setFill(Color.GREEN);
			        	gc.fillRect(j*COORDWIDTH, i*COORDHEIGHT-3, PicLength*pct, 5);
			        	gc.setFill(Color.RED);
			        	gc.fillRect(j*COORDWIDTH+PicLength*pct, i*COORDHEIGHT-3, PicLength*(1-pct), 5);
			        }
			        
			        /* 加入记录 */
			        frameInfo.creatureInfos.add(creatureInfo);
			     }
			}
		}

		
		if (frameInfos != null) {
			frameInfos.add(frameInfo);
		}
	}
	
	public Creature getCreature(int x, int y) {
		return coords[x][y].getCreatrue();
	}
	
	public void setEventThreadPool(ExecutorService battleEventThreadPool) {
		this.battleEventThreadPool = battleEventThreadPool;
	}
	

}
