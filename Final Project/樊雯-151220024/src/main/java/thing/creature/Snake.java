package thing.creature;

import java.util.concurrent.TimeUnit;

import thing.creature.Creature;
import thing.creature.CreatureState;

import javafx.scene.image.Image;
import thing.creature.Monster;

public class Snake extends Monster {

	//private final int CURE_ADDHP = 15;
	
	public Snake() {
		image = new Image("SHE.png");
		name = "蛇精";
		
		fullBlood = 40; //超血量
		Force = 8;
		Blood = fullBlood;
		Speed = 1500;
	}

	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if (state == CreatureState.RUNNING) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getX(), position.getY()-1)) {
						Creature good = field.getCreature(position.getX(), position.getY()-1);
						if (good.getState() == CreatureState.RUNNING) {
							field.createBattleEvent(good, this);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
					}
				}
			}
			switch (state) {
			case DEAD:
				try {
					TimeUnit.SECONDS.sleep(2);
					synchronized (field) {
						field.clearCreature(position.getX(), position.getY());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
				break;
			case RUNNING:
				// 每走10步触发一次治疗
				if (step%10 == 0) {
					//cure();
				}
				break;
				case ATTAK:
				// 治疗时间持续1秒后蛇精恢复运动状态
				state = CreatureState.RUNNING;
				break;
			default:
				break;
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
