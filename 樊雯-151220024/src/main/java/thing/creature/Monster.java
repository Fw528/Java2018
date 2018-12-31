package thing.creature;

import java.util.concurrent.TimeUnit;

import thing.creature.Creature;
import thing.creature.CreatureState;

public class Monster extends Creature implements Runnable {
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getX(), position.getY()-1)) {
						// TODO: 触发战斗事件
						Creature cala = field.getCreature(position.getX(), position.getY()-1);
						/*if (cala.getState() == CreatureState.RUNNING || cala.getState() == CreatureState.CURE) {
							field.createBattleEvent(this, cala);
						} else */{
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
						/* 释放技能(每走3步释放一次技能) */
						/*
						if (this instanceof Shoot && (step+1)%3 == 0) {
							((Shoot)this).shoot(field);
						}
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
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}
}
