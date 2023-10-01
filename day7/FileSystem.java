package day7;

public class FileSystem {

    String name;
    String path;
    String parent;
    int size;

    public FileSystem(String path, String parent, String name) {

        this.name = name;
        this.path = path;
        this.parent = parent;
        this.size = 0;
    }

    public void addSize(int fileSize) {

        this.size += fileSize;
    }

    public Integer getSize() {
        return this.size;
    }

}
