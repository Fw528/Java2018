package thing.creature;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Hululist {
    public List<Good> HuluCollection = new ArrayList<Good>();
    private Huluwa Red;
    private Huluwa Orrange;
    private Huluwa Yellow;
    private Huluwa Green;
    private Huluwa Cyan;
    private Huluwa Blue;
    private Huluwa Purple;
    private Grandpa Yeye;

    public void init(){
        Image t = new Image("1.png") ;
        Red.setImage(t);
        Red.setImageName("1.png");
        Red.setName("大娃");
        Red.setBlood(22);
        Red.setForce(10);  //大娃力大无穷因此高攻击
        Red.setSpeed(1000);  //正常速度

        Orrange.setImageName("2.png");
        t = new Image("2.png") ;
        Orrange.setImage(t);
        Orrange.setName("二娃");
        Orrange.setBlood(24);
        Orrange.setForce(6); //千里眼二娃不是攻击系的因此force较少
        Orrange.setSpeed(200);  //攻击频率高

        Yellow.setImageName("3.png");
        t = new Image("3.png") ;
        Yellow.setImage(t);
        Yellow.setName("三娃");
        Yellow.setBlood(30);
        Yellow.setForce(4);  //三娃刀枪不入 因此血最厚
        Yellow.setSpeed(1000); //正常速度

        Green.setImageName("4.png");
        t = new Image("4.png") ;
        Green.setImage(t);
        Green.setName("四娃");
        Green.setBlood(20);
        Green.setForce(10);  //四娃火系高攻击选手
        Green.setSpeed(1200); //偏慢

        Cyan.setImageName("5.png");
        t = new Image("5.png") ;
        Cyan.setImage(t);
        Cyan.setName("五娃");
        Cyan.setBlood(22);
        Cyan.setForce(8);  //五娃水系平衡选手
        Cyan.setSpeed(600); //攻击速度较快

        Blue.setImageName("6.png");
        t = new Image("6.png") ;
        Blue.setImage(t);
        Blue.setName("六娃");
        Blue.setBlood(22);
        Blue.setForce(8);  //六娃特质系平衡选手
        Blue.setSpeed(600); //攻击频率较高

        Purple.setImageName("7.png");
        t = new Image("7.png") ;
        Purple.setImage(t);
        Purple.setName("七娃");
        Purple.setBlood(18);
        Purple.setForce(10);  //七娃特质系高攻击选手
        Purple.setSpeed(1500);//频率低

        //爷爷的初始化在类里面

        HuluCollection.add(Red);
        HuluCollection.add(Orrange);
        HuluCollection.add(Yellow);
        HuluCollection.add(Green);
        HuluCollection.add(Cyan);
        HuluCollection.add(Blue);
        HuluCollection.add(Purple);
        HuluCollection.add(Yeye);

    }

    public Hululist(){
        Red = new Huluwa();
        Orrange =new Huluwa();
        Yellow = new Huluwa();
        Green = new Huluwa();
        Cyan = new Huluwa();
        Blue = new Huluwa();
        Purple = new Huluwa();
        Yeye = new Grandpa();
    }

}
