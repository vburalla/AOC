package day19;

public class Robot {
    String product;
    Integer oreCost;
    Integer clayCost;
    Integer obsidianCost;

    public Robot(String type, Integer oreCost, Integer clayCost, Integer obsidianCost) {

        this.product = type;
        this.oreCost = oreCost;
        this.clayCost = clayCost;
        this.obsidianCost = obsidianCost;
    }

    public Robot(String type) {

        this.product = type;
        this.oreCost = 0;
        this.clayCost = 0;
        this.obsidianCost = 0;
    }

    public void addCost(String cost) {

        String[] costInfo = cost.split(" ");

        switch (costInfo[1]) {
            case "ore":
                this.oreCost = Integer.valueOf(costInfo[0]);
                break;
            case "clay":
                this.clayCost = Integer.valueOf(costInfo[0]);
                break;
            case "obsidian":
                this.obsidianCost = Integer.valueOf(costInfo[0]);
                break;
        }
    }
}
