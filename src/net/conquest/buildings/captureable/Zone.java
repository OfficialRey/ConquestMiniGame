package net.conquest.buildings.captureable;

public enum Zone {
    THRONE_ROOM("Throne Room", "throne", 0, 60),
    YARD("Courtyard", "yard", 1, 45),
    VILLAGE("Village", "village", 2, 45),
    FARMLAND("Farmland", "farm", 3, 30),
    FOREST("Forest", "woods", 4, 30);

    public final String NAME;
    public final String CFG;
    public final int ID;
    public final int CAPTURE_TIME;

    Zone(String name, String cfg, int id, int captureTime) {
        NAME = name;
        CFG = cfg;
        ID = id;
        CAPTURE_TIME = captureTime;
    }

}
