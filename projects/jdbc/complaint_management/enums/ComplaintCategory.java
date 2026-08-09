package enums;

public enum ComplaintCategory {

    ROAD_DAMAGE("Road Damage"),
    WATER_LEAKAGE("Water Leakage"),
    STREET_LIGHT("Street Light"),
    GARBAGE("Garbage"),
    DRAINAGE("Drainage"),
    ELECTRICITY("Electricity"),
    OTHER("Other");

    private final String description;

    ComplaintCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
