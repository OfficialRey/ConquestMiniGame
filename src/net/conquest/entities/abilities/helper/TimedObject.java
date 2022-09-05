package net.conquest.entities.abilities.helper;

public abstract class TimedObject {

    private int timeLived;
    private int maxTimeLived;

    public TimedObject(int maxTimeLived) {

    }

    public abstract void run();
}