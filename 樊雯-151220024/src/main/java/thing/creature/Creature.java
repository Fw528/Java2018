package thing.creature;

import java.util.Random;

import space.Constants;
import space.Position;
import thing.Thing;

import javafx.scene.image.Image;

public class Creature extends Thing implements Constants{
	
	private static final Image deadImage = new Image("gost.png");

	protected Position position;
	
	protected int fullBlood;   //血量上限
	protected int Blood;  //剩余血量
	protected int Force;  //武力值
	//protected int DEF;
	protected int Speed; //决定每次sleep的时间
	protected  boolean isJustice;//备用的阵营判断标志
	
	protected CreatureState state = CreatureState.RUNNING;
	
	public Creature() {
		position = new Position();
	}

	public void setSpeed(int s){Speed = s;}
	public int getSpeed(){return Speed;}
	public double getHPPCT() {
		/*
		return ((Blood <= 0) ? 0 ：(double) Blood /(double) fullBlood);
		*/
		if (Blood <= 0) return 0;
		else return ((double) Blood /(double) fullBlood);
	}
	/**
	 * 血条百分比 但是显示貌似有点点问题
	 */
	@Override
	public void run(){
	}

	public void setPosition(int row, int column) {
		position.setX(row);
		position.setY(column);
	}

	
	public Position getPosition() {
		return position;
	}
	
	
	public void kill() {
		synchronized (field) {
			field.clearCreature(position.getX(), position.getY());
			isKilled = true;
		}
	}
	
	protected Position getNextPosition() {
		Random random = new Random();
		Position nextPos;
		int x = position.getX();
		if(x< (ROW/5)) x = x+1;
		else if(4*x > 3*ROW) x = x-1;
		else if(2*x>(ROW/3)) x = x-random.nextInt(2); //1/2的几率原地或者左
		else if(x<(ROW/3))	x = x+random.nextInt(2); //1/2的几率原地或者→
		else x = x+random.nextInt(3)-1;  //几率均等

		/**
		 * 使用key某个方向的可能性改变
		 */

		//x
		int y = position.getY()+random.nextInt(3)-1;
		if(x>=ROW) x = ROW-1;
		if(x<= 0) x = 0;
		if (y<= 0 ) y = 0;
		if (y>=COLUMN) y = COLUMN-1;
		nextPos = new Position(x,y);
		return nextPos;
	}
	
	protected void setCreatureOnNextPosition(Position nextPos) {
		int preX = position.getX();
		int preY = position.getY();
		if (field.setCreatrue(this, nextPos.getX(), nextPos.getY())) {
			field.clearCreature(preX, preY);
		}
	}
	
	public int getBlood() { return Blood; }
	public void setBlood(int t){Blood = t;fullBlood = Blood;}
	
	public int getForce() { return Force; }
	public void setForce(int f){Force = f;}
	
	public void beAttacked(int atk) {
		Blood -= atk ;
		if (Blood < 0) {
			Blood = 0;
		}
	}
	
	public CreatureState getState() { return state; }
	
	public void setState(CreatureState state) {
		this.state = state;
		if (state == CreatureState.DEAD) {
			image = deadImage;
		}
	}
}
