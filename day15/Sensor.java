package day15;

import utils.Point;

public class Sensor {
    Point ubication;
    Point beaconUbication;

    public Sensor(Point ubication, Point beacon){
        this.ubication = ubication;
        this.beaconUbication = beacon;
    }

    public Point getUbication() {
        return this.ubication;
    }

    public Point getBeaconUbication() {
        return this.beaconUbication;
    }

    public int getUbicationX() {
        return this.ubication.getX();
    }

    public int getManhattan() {
        return Math.abs(this.ubication.getX() - this.beaconUbication.getX()) + Math.abs(this.ubication.getY() - this.beaconUbication.getY());
    }

    public int getDistanceToRow(int row) {
        return Math.abs(this.ubication.getY() - row);
    }
}
