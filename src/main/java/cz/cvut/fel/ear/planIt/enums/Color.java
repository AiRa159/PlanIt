package cz.cvut.fel.ear.planIt.enums;

public enum Color {
    GREEN("DONE TASK"),
    ORANGE("TASK IN PROGRESS"),
    YELLOW("TASK TO DO"),
    RED("IMMEDIATELY"),
    BLUE(""),
    PURPLE(""),
    BLACK(""),
    GREY(""),
    BROWN(""),
    PINK("");

    private String label;
    Color(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }
}
