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
	private Coord[][] coords;
	private int row;
	private int column;

	
	private ExecutorService battleEventThreadPool;
	private ExecutorService skillThingThreadPool;

	/*
	private Image cureBrosImage = new Image("cureBrothers.gif");
	private Image cureMonsImage = new Image("cureMonsters.gif");
	*/
	public BattleField(int r, int c) {
		row = r;
		column = c;
		coords = new Coord[r][c];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				coords[i][j] = new Coord();
			}
		}
		
	}
	
	public int getRow() { return row; }
	public int getColumn() { return column; }
	
	public int getWidth() {
		return COORDWIDTH*column;
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
	
	public void setEventThreadPool(ExecutorService battleEventThreadPool, ExecutorService skillThingThreadPool) {
		this.battleEventThreadPool = battleEventThreadPool;
		this.skillThingThreadPool = skillThingThreadPool;
	}
	
	public void createBattleEvent(Creature cala, Creature mons) {
		battleEventThreadPool.execute(new BattleEvent(cala, mons));
	}

	public ArrayList<Huluwa> getRunningBrothers() {
		ArrayList<Huluwa> brothers = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Creature creature = coords[i][j].getCreatrue();
					if (creature instanceof Huluwa && creature.getState() == CreatureState.RUNNING) {
						brothers.add((Huluwa) creature);
					}
				}
			}
		}
		return brothers;
	}
	
	public ArrayList<Monster> getRunningMonsters() {
		ArrayList<Monster> monsters = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Creature creature = coords[i][j].getCreatrue();
					if (creature instanceof Monster && creature.getState() == CreatureState.RUNNING) {
						monsters.add((Monster) creature);
					}
				}
			}
		}
		return monsters;
	}
}
