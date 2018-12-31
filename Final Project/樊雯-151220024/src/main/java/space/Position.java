package space;

public class Position {
	
    private int x;
    private int y;
    
    public Position() {
        x = -1;
        y = -1;
    }

    public Position(int row,int column) {
        this.x = row;
        this.y = column;
    }

    public void setX(int row) {
        this.x = row;
    }

    public void setY(int column) {
        this.y = column;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}