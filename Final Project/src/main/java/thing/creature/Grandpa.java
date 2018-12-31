package thing.creature;

import java.util.concurrent.TimeUnit;

import thing.creature.Creature;
import thing.creature.CreatureState;

import javafx.scene.image.Image;
import thing.creature.Good;

public class Grandpa extends Good {
	
	public Grandpa() {
		image = new Image("yeye.png");
		name = "爷爷";
		
		fullBlood = 20;
		Force = 4;
		Blood = fullBlood;
		Speed = 500;
	}
	/*
	public void cure() {
		ArrayList<Huluwa> brothers = field.getRunningBrothers();
		if (brothers.isEmpty()) {
			return;
		}
		state = CreatureState.CURE;
		for (int i = 0; i < brothers.size(); i++) {
			brothers.get(i).getCured(CURE_ADDHP);
		}
		System.out.println("爷爷完成了一次治疗");
	}*/

	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if (state == CreatureState.RUNNING) {
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getX(), position.getY()+1)) {
						Creature monster = field.getCreature(position.getX(), position.getY()+1);
						if (monster.getState() == CreatureState.RUNNING ){
							field.createBattleEvent(this, monster);
						} else{
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
						field.clearCreature(position.getX(), position.getX());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
				break;
			case RUNNING:
				// 每走7步触发一次治疗
				if (step%7 == 0) {
					//cure();
				}
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
