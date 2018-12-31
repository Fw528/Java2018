package thing;

import space.BattleField;

import javafx.scene.image.Image;

public abstract class Thing implements Runnable{
	public Image image;


	private String ImageName;
	
	protected String name;
	
	protected static BattleField field;
	
	public static void setField(BattleField field) {
		Thing.field = field;
	}
	
	public Image getImage() { return image; }
	public void setImage(Image t){image= t;}
	
	public String getName() {
		return name;
	}

	protected boolean isKilled = false;
	
	public boolean isKilled() { return isKilled; }

	public String getImageName() {
		return ImageName;
	}
	public void setName(String name){name = name;}
	public void setImageName(String name){ImageName = name;}
}
