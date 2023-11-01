package day19;

public class Resource implements Comparable<Resource>{
    Integer ore;
    Integer clay;
    Integer obsidian;
    Integer geode;

    public Resource() {
        this.ore = 0;
        this.clay = 0;
        this.obsidian = 0;
        this.geode = 0;
    }

    public void addResource(Resource resource) {
        this.ore += resource.ore;
        this.obsidian += resource.obsidian;
        this.clay += resource.clay;
        this.geode += resource.geode;
    }

    @Override
    public int compareTo(Resource o) {
        if(o == null) {
            return 1;
        } else {
            Resource rO = (Resource) o;
            int value1 = this.ore + 10 * this.clay + 100 * this.obsidian + 1000 * this.geode;
            int value2 = rO.ore + 10 * rO.clay + 100 * rO.obsidian + 1000 * rO.geode;
            if(value1 == value2) {
                return 0;
            } else {
                return value1 > value2 ? 1 : -1;
            }
        }
    }
}
