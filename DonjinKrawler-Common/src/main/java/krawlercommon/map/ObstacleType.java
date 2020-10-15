package krawlercommon.map;

public enum ObstacleType {
    LAVA("1"),
    SPIKES("2"),
    SLIME("3");

    public final String number;

    private ObstacleType(String number) {
        this.number = number;
    }

    public static ObstacleType getTypeByNumber(String number) {
        if (number.equals("1")) {
            return LAVA;
        } else if (number.equals("2")) {
            return SPIKES;
        } else {
            return SLIME;
        }
    }
}
