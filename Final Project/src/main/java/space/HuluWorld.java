package space;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import loginfo.CreatureInfo;
import loginfo.FrameInfo;
import thing.Thing;
import thing.creature.CreatureState;
import thing.creature.Good;
import thing.creature.Hululist;
import thing.creature.Monster;
import thing.creature.Monsterlist;
import GUI.GuiPainter;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class HuluWorld implements Runnable,Constants{

	private BattleField battleField = new BattleField(Constants.ROW, Constants.COLUMN);
	
	ArrayList<Good> goods = new ArrayList<>();
	ArrayList<Monster> monsters = new ArrayList<>();
	
	int battleResult = 0; // 战斗结果，1为胜利，0为失败
	
	private int goodsStartRow;
	private int goodsStartColumn;

	private int badsStartRow;
	private int badsStartColumn;
	private int badsCrtFmt = 2;
	
	private Canvas battleFieldCanvas;
	
	private Button saveLogBtn;
	private Button discardBtn;
	
	private GuiPainter guiPainter;

	private ExecutorService creatureThreadPool = Executors.newCachedThreadPool(); // 所有生物线程
	private ExecutorService guiThread = Executors.newSingleThreadExecutor(); // GUI绘制是一个线程
	private ExecutorService battleEventThreadPool = Executors.newCachedThreadPool(); // 所有战斗事件
	private ExecutorService skillThingThreadPool = Executors.newCachedThreadPool(); // 所有释放的技能


	private String formname;
	private Formation formation;
	private Hululist hululist;
	private Monsterlist monsterlist;

	public HuluWorld(Canvas battleFieldCanvas, Button saveLogBtn, Button discardBtn) {
		/**
		 * 默认是长蛇阵型的
		 */
		this.formname = "Changeshe";
		this.battleFieldCanvas = battleFieldCanvas;
		this.saveLogBtn = saveLogBtn;
		this.discardBtn = discardBtn;
		guiPainter = new GuiPainter(battleFieldCanvas, battleField);
		
		hululist = new Hululist();
		hululist.init();
		// 葫芦娃阵营
		for(Good temp: hululist.HuluCollection) {
			goods.add(temp);
		}
		monsterlist = new Monsterlist();
		monsterlist.init();
		for(Monster temp:monsterlist.MonsterCollection)
		{
			monsters.add(temp);
		}
		formation = new Formation(formname);
		formation.setBattlePlace(battleField, goods,monsters);
		guiPainter.drawBattleField();
		
		Thing.setField(battleField);
		battleField.setEventThreadPool(battleEventThreadPool, skillThingThreadPool);
	}

	public void setFormname(String name){formname = name;}

	public void ChangeFormation() {
		battleField.clearAll();
		formation = new Formation(formname);
		formation.setBattlePlace(battleField, goods,monsters);
		guiPainter.drawBattleField();
	}

	public void gameRoundStart() {
		
		for (Good good : goods) {
			creatureThreadPool.execute(good);
		}
		
		for (Monster monster : monsters) {
			creatureThreadPool.execute(monster);
		}
		creatureThreadPool.shutdown();
		guiThread.execute(guiPainter);
		guiThread.shutdown();

		while (!(allBadsDead()||allGoodsDead())) {}
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		guiPainter.kill();
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		if (allBadsDead() && !allGoodsDead()) {
			battleResult = 1;
			gc.drawImage(new Image("victory.jpg"), 20, 0);
			saveLogBtn.setVisible(true);
			discardBtn.setVisible(true);
		} else if (allGoodsDead() && !allBadsDead()) {
			gc.drawImage(new Image("failed.jpg"), 20, 0);
			battleResult = 0;
			saveLogBtn.setVisible(true);
			discardBtn.setVisible(true);
		}
		killAllTheThread();
	}

	@Override
	public void run() {
		System.out.println("葫芦世界线程开始");
		gameRoundStart();
		System.out.println("葫芦世界线程退出");
	}


	public void killAllTheThread() {
		for (Good good : goods) {
			good.kill();
		}
		for (Monster monster : monsters) {
			monster.kill();
		}
		//battleField.kill();
		guiPainter.kill();
		battleEventThreadPool.shutdown();
		skillThingThreadPool.shutdown();

	}
	
	private boolean allGoodsDead() {
		for (Good good : goods) {
			if (good.getState() != CreatureState.DEAD && !good.isKilled()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean allBadsDead() {
		for (Monster monster : monsters) {
			if (monster.getState() != CreatureState.DEAD && !monster.isKilled()) {
				return false;
			}
		}
		return true;
	}
	
	public void saveGameLog(File file) throws Exception {
		BufferedWriter fout = null;
		try {
			fout = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<FrameInfo> frameInfos = guiPainter.getFrameInfos();
		fout.write(frameInfos.size()+"\n");
		for (int i = 0; i < frameInfos.size(); i++) {
			int creatureInfoNum = frameInfos.get(i).creatureInfos.size();
			fout.write(creatureInfoNum+"\n");
			for (CreatureInfo creatureInfo : frameInfos.get(i).creatureInfos) {
				fout.write(creatureInfo.toString());
			}
		}
		fout.write(battleResult+"\n");
		fout.flush();
		fout.close();
	}
}

	
