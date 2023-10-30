package day18;

import java.util.ArrayList;
import java.util.List;

public class Cube {

    private int x;
    private int y;
    private int z;

    List<String> freeFaces;

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.freeFaces = new ArrayList<>();
    }

    public Cube(String coordinates) {

        String[] coord = coordinates.split(",");
        this.x = Integer.valueOf(coord[0]);
        this.y = Integer.valueOf(coord[1]);
        this.z = Integer.valueOf(coord[2]);
        this.freeFaces = new ArrayList<>();
        createFreeFaces();
    }

    public void removeFreeFaces(List<Cube> cubes) {

        for(Cube cube : cubes) {
            this.removeFreeFace(cube);
        }
    }

    public void removeFreeFacesFromHash(List<String> cubes) {

        for(String cube : cubes) {
            this.removeFreeFaceFromHash(cube);
        }
    }

    public void removeFreeFace(Cube cube) {

        String hash = createHash(cube.x, cube.y, cube.z);
        this.freeFaces.remove(hash);
    }

    public void removeFreeFaceFromHash(String cube) {

        this.freeFaces.remove(cube);
    }

    public void createFreeFaces() {

        this.freeFaces.add(createFreeFace(this.x + 1, this.y, this.z));
        this.freeFaces.add(createFreeFace(this.x -1, this.y, this.z));
        this.freeFaces.add(createFreeFace(this.x, this.y + 1, this.z));
        this.freeFaces.add(createFreeFace(this.x, this.y - 1, this.z));
        this.freeFaces.add(createFreeFace(this.x, this.y, this.z + 1));
        this.freeFaces.add(createFreeFace(this.x, this.y, this.z - 1));
    }

    private String createFreeFace(int x, int y, int z) {

        return createHash(x, y, z);
    }

    public String createHash(int x, int y, int z) {

        return String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        } else {
            Cube objCube = (Cube) obj;
            return objCube.x == this.x && objCube.y == this.y && objCube.z == this.z;
        }
    }

    public void move(String axis, int steps) {

        if(axis.equals("x")) {
            this.x+=steps;
        } else if (axis.equals("y")) {
            this.y+=steps;
        } else if (axis.equals("z")) {
            this.z+=steps;
        } else {
            throw new IllegalArgumentException("Wrong axis selected");
        }
    }

    public boolean exceedsLimits(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {

        return (this.x < minX) || (this.x > maxX) || (this.y < minY) || (this.y > maxY) || (this.z < minZ) || (this.z > maxZ);
    }

}

