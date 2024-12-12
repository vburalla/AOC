package utils;

import java.util.Objects;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x  = x;
        this.y = y;
    }

    public Point(String coordinates) {
        String[] coord = coordinates.split(", ");
        this.x = Integer.parseInt(coord[0].replace("x=",""));
        this.y = Integer.parseInt(coord[1].replace("y=", ""));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point otherPoint = (Point) obj;
        return Objects.equals(x, otherPoint.x) && Objects.equals(y, otherPoint.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Point clone() {
        return new Point(x,y);
    }

    public Point add(Point point) {
        return new Point(this.x + point.x, this.y + point.y);
    }

    public Point substract(Point point) {

        return new Point(this.x - point.x, this.y - point.y);
    }

    public String toString() {
        return "x = " + this.x + ", y = " + this.y;
    }

}
