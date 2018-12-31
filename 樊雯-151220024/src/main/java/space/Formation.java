package space;

import thing.creature.Creature;

import java.util.ArrayList;

public class Formation {
    public Position[] list= new Position[8];
    public Formation(String type){
        initList(type);
    }
    private void initList(String type){
        if (type == "Fangmen"){
            list[0] = new Position(5,4);
            list[1] = new Position(4,3);
            list[2] = new Position(6,3);
            list[3] = new Position(3,2);
            list[4] = new Position(7,2);
            list[5] = new Position(4,1);
            list[6] = new Position(6,1);
            list[7] = new Position(5,0);
        }
        else if (type == "Heyi"){
            list[0] = new Position(5,4);
            list[1] = new Position(4,3);
            list[2] = new Position(6,3);
            list[3] = new Position(3,2);
            list[4] = new Position(7,5);
            list[5] = new Position(2,1);
            list[6] = new Position(8,1);
            list[7] = new Position(5,1);
        }
        else if(type == "Henge"){
            list[0] = new Position(2,2);
            list[1] = new Position(3,1);
            list[2] = new Position(4,2);
            list[3] = new Position(5,1);
            list[4] = new Position(6,2);
            list[5] = new Position(7,1);
            list[6] = new Position(8,2);
            list[7] = new Position(5,0);
        }
        else{
            //默认长蛇吧
            for(int i = 0 ; i <8 ;i++){
                list[i] = new Position(i+2,2);
            }
        }
    }
    public void setBattlePlace(BattleField field,ArrayList<? extends Creature>hululist,ArrayList<? extends Creature>monsterlist){
        int i = 0;
        for(Creature creature:monsterlist){
            creature.setPosition(i+2,110);
            field.setCreatrue(creature,2+i,10);
            i++;
        }
        i = 0 ;
        for(Creature creature:hululist){
            creature.setPosition(list[i].getX(),list[i].getY());
            field.setCreatrue(creature,list[i].getX(),list[i].getY());
            i++;
        }

    }
    public Position[] getPosition(){
        return list;
    }
}
