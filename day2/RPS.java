package day2;

public enum RPS {
    A("rock", 1), B("paper", 2), C("scissors", 3),X("rock", 1),Y("paper", 2),Z("scissors", 3);

    String choice;
    int points;

    private RPS(String choice, int points){
        this.choice = choice;
        this.points = points;
    }

    public String getChoice() {
        return this.choice;
    }

    public int getPoints() {
        return this.points;
    }

    public static RPS getByChoice(String choice) {

        RPS element = RPS.A;
        if(choice.equals(B.choice)){
            element = RPS.B;
        } else if(choice.equals(C.choice)){
            element = RPS.C;
        }
        return element;
    }

    public String getWeakOpposite() {

        String weakOpposite = "paper";
        if(this.choice.equals("rock")) {
            weakOpposite = "scissors";
        } else if(this.choice.equals("paper")) {
            weakOpposite = "rock";
        } 
        return weakOpposite;
    }

    public String getStrongOpposite() {

        String strongOpposite = "rock";
        if(this.choice.equals("rock")) {
            strongOpposite = "paper";
        } else if(this.choice.equals("paper")) {
            strongOpposite = "scissors";
        } 
        return strongOpposite;
    }
}