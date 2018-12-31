package thing.creature;

import java.util.concurrent.TimeUnit;

//import skill.Shoot;
import space.Position;

public abstract class Good extends Creature implements Runnable {

	public  Good(){
		isJustice = true;
		position = new Position();
	}
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getX(), position.getY()+1)) {
						Creature monster = field.getCreature(position.getX(), position.getY()+1);
						if(monster.getState() == CreatureState.RUNNING ) {
							field.createBattleEvent(this, monster);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
						/* 释放技能 */
						/*

						 */
					}
				}
			}
			if (state == CreatureState.DEAD) {
				try {
					TimeUnit.SECONDS.sleep(2);
					synchronized (field) {
						field.clearCreature(position.getX(), position.getY());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
			}
			try {
					TimeUnit.MILLISECONDS.sleep(((Huluwa)this).getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}
}
