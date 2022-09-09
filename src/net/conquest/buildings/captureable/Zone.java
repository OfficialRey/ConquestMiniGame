package net.conquest.buildings.captureable;

public enum Zone {
    THRONE_ROOM("Throne Room", "throne", 60),
    YARD("Courtyard", "yard", 45),
    VILLAGE("Village", "village", 45),
    FARMLAND("Farmland", "farm", 30),
    FOREST("Forest", "woods", 30);

    public final String NAME;
    public final String CFG;
    public final int CAPTURE_TIME;

    Zone(String name, String cfg, int captureTime) {
        NAME = name;
        CFG = cfg;
        CAPTURE_TIME = captureTime;
    }

}
