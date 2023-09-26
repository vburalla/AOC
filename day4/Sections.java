package day4;

class Sections {
    
    int init;
    int end;

    public Sections(int init, int end) {
        this.init = init;
        this.end = end;
    }

    public Sections(String[] sections) {

        this.init = Integer.valueOf(sections[0]);
        this.end = Integer.valueOf(sections[1]);
    }
}