package enums;

public enum RoomType {

    SINGLE("Single Room", 1500),
    DOUBLE("Double Room", 2500),
    DELUXE("Deluxe Room", 4000),
    SUITE("Suite Room", 6000);

    private final String description;
    private final double defaultPrice;

    RoomType(String description, double defaultPrice) {
        this.description = description;
        this.defaultPrice = defaultPrice;
    }

    public String getDescription() {
        return description;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    @Override
    public String toString() {
        return description;
    }

}
