package day9;

public class Position {
    
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void changePosition(Position pos) {
        this.x += pos.getX();
        this.y += pos.getY();
    }

    public String toString() {
        return String.valueOf(x) + "," + String.valueOf(y);
    }

    public Position getDistanceTo(Position nearPoint) {
        return new Position(this.x - nearPoint.getX(), this.y - nearPoint.getY());
    }

}
