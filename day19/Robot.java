package day19;

public class Robot implements Comparable<Robot>{
    String type;
    Resource produces;
    Resource costs;
    boolean inConstruction;

    public Robot(String type) {

        this.produces = new Resource();
        this.costs = new Resource();
        this.inConstruction = true;
        this.type = type;
        switch (type) {
            case "ore":
                this.produces.ore = 1;
                break;
            case "clay":
                this.produces.clay = 1;
                break;
            case "obsidian":
                this.produces.obsidian = 1;
                break;
            case "geode":
                this.produces.geode = 1;
                break;
        }
    }

    public String getType() {
        return this.type;
    }

    public void addCost(String cost) {

        String[] costInfo = cost.split(" ");

        switch (costInfo[1]) {
            case "ore":
                this.costs.ore = Integer.valueOf(costInfo[0]);
                break;
            case "clay":
                this.costs.clay = Integer.valueOf(costInfo[0]);
                break;
            case "obsidian":
                this.costs.obsidian = Integer.valueOf(costInfo[0]);
                break;
        }
    }

    public Resource collect() {

        Resource resource = new Resource();
        if(this.inConstruction) {
            this.inConstruction = false;
        } else {
            resource = this.produces;
        }
        return resource;
    }

    public boolean canBuild(Resource resource) {
        return resource.ore >= this.costs.ore && resource.clay >= this.costs.clay
                && resource.geode >= this.costs.geode && resource.obsidian >= this.costs.obsidian;
    }

    @Override
    public int compareTo(Robot o) {
        if(o == null) {
            return 1;
        } else {
            Robot oR = (Robot) o;
            return this.produces.compareTo(oR.produces);
        }
    }

}
