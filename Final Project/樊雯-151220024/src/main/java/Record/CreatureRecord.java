package Record;
import thing.creature.Creature;
import thing.creature.CreatureState;

public class CreatureRecord {
    public Creature creature;
    public CreatureRecord(){}
    public CreatureRecord (Creature t,int x,int y){
        creature.setName(t.getName());
        creature.setBlood(t.getBlood());
        creature.setPosition(x,y);
        creature.setState(t.getState());
        creature.setImage(t.getImage());
    }
}
