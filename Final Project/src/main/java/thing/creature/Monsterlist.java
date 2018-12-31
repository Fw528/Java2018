package thing.creature;

import javafx.scene.image.Image;
import space.Constants;

import java.util.ArrayList;
import java.util.List;

public class Monsterlist implements Constants{
    public List<Monster> MonsterCollection= new ArrayList<Monster>();
    private Snake SHE;
    private Monster Xiezi;
    private Monster[] Loulo = new Monster[LouloNumber];

    public void init() {

        Image image = new Image("xiezi2.png");
        Xiezi.setImage(image);
        Xiezi.setName("蝎子");
        Xiezi.setBlood(10);
        Xiezi.setForce(20);  //高攻击高血量选手
        Xiezi.setSpeed(1000);

        MonsterCollection.add(SHE);
        MonsterCollection.add(Xiezi);

        image = new Image("Loulo.png");
        for (int i = 0; i < LouloNumber; i++) {
            String t = i + "";
            Loulo[i].setName("喽啰" + t);
            Loulo[i].setForce(8);
            Loulo[i].setBlood(18);
            Loulo[i].setImage(image);
            Loulo[i].setSpeed(1000);
            MonsterCollection.add(Loulo[i]);
        }
    }
    public Monsterlist() {
        SHE = new Snake();
        /**
         * 蛇精的初始化在类里面 地位等同于爷爷
         */
        Xiezi = new Monster();//全场目前最强选手
        for (int i = 0; i < LouloNumber; i++) {
            Loulo[i] = new Monster();
        }
    }
    /*
	@Override
	public void shoot(BattleField field) {
		field.createSkillThing(new Knife(position.getRow(), position.getColumn()-1));

	}
    */
}
