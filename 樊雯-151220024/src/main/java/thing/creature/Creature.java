package thing.creature;

import java.util.Random;

import space.Constants;
import space.Position;
import thing.Thing;

import javafx.scene.image.Image;

public abstract class Creature extends Thing implements Runnable, Constants{
	
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
		int x = position.getX()+random.nextInt(3)-1;
		int y = position.getY()+random.nextInt(3)-1;

		/**
		 * 使用key某个方向的可能性改变 失败了
		 */
		if(y<= 1 ) y = y+1;
		else if(y > COLUMN-2) y = y-1;
		else if(y>10) y = y-random.nextInt(2); //1/2的几率原地或者左
		else if(y<4) y = y+random.nextInt(2); //1/2的几率原地或者→
		else y = y+random.nextInt(3)-1;

		if(x<=1) x+= 1;
		else if(x == ROW-1) x-=1;
		else if(x>9) x = x-random.nextInt(2); //1/2的几率原地或者左
		else if(y<4) x = y+random.nextInt(2); //1/2的几率原地或者→
		else y = y+random.nextInt(3)-1;

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
