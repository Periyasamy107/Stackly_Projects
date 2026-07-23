package enums;

public enum BookCategory {

    JAVA("Programming"),
    DATABASE("Database"),
    SCIENCE("Science"),
    HISTORY("History"),
    MATHEMATICS("Maths");

    private String description;

    BookCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return name() + " - " + description;
    }

}
