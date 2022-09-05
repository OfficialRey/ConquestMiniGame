package net.conquest.buildings.structures;

public enum StructureData {

    BARRACKS(1); //Custom intervals per barrack type

    public final int EXE_TIME;

    StructureData(int exeTime) {
        EXE_TIME = exeTime;
    }
}