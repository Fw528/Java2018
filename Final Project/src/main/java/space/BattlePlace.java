package space;

import Record.CreatureRecord;
import Record.FiledRecord;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import thing.creature.Creature;
import thing.creature.Good;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class BattlePlace implements Constants {
    private int x;
    private int y;

    private ExecutorService battleEventThreadPool;
    //处理正在进行的战斗

    private ArrayList<FiledRecord> filedRecords = new ArrayList<FiledRecord>();
    private Creature[][] WholeBattlePlace = new Creature[Constants.ROW][Constants.COLUMN];
    public BattlePlace(){
    }
    public void setCreature(Creature creature,int x,int y){
        if(judgePlace(x,y))WholeBattlePlace[x][y] = creature;
        else System.out.println("error in set"+creature.getName()+"to"+x+" "+y);
    }
    public boolean judgePlace(int x, int y){
        if ((x>=0 && x<ROW)&&(y>= 0 && y < COLUMN))
            return true;
        else
            return false;
    }
    public void RESTALL(){
        for(int i = 0 ; i <ROW;i++)
            for(int j = 0 ; j <COLUMN;j++)
                WholeBattlePlace[i][j] = null;
    }

    public boolean judgeExist(int x, int y){
        if(WholeBattlePlace[x][y] == null)
            return false;
        else return true;
    }

    public boolean judgeExitsGood(int x, int y){
        if((judgeExist(x,y))&&WholeBattlePlace[x][y] instanceof Good)
            return  true;
        else return false;
    }
    private boolean judgeExitsMonster(int x, int y){
        if (judgeExist(x,y)&&(!judgeExitsGood(x,y)))
            return true;
        else return false;
    }
    public void PaintWhole(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        FiledRecord filedRecord = new FiledRecord();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for(int i = 0 ; i <ROW;i++)
            for(int j = 0 ; j <COLUMN;j++){
                if(judgeExist(i,j)){
                    try{
                        gc.drawImage(WholeBattlePlace[i][j].getImage(),XBorder+i*COORDWIDTH,YBorder+j);
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        System.out.println("图片载入问题");
                    }
                    CreatureRecord record = new CreatureRecord(WholeBattlePlace[i][j],i,j);
                    gc.setLineWidth(0);
                    double pct = WholeBattlePlace[i][j].getHPPCT();
                    gc.setFill(Color.GREEN);
                    gc.fillRect(j*COORDWIDTH, i*COORDHEIGHT-3, PicLength*pct, 5);
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(j*COORDWIDTH+PicLength*pct, i*COORDHEIGHT-3, PicLength*(1-pct), 5);
                    filedRecord.Field.add(record);
                }
            }
            filedRecords.add(filedRecord); //加入整个链条
    }
    public Creature getCreature(int x, int y){
        return WholeBattlePlace[x][y];
    }

}
