package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;

public class CaptureAttack {

    public final ConquestTeam ConquestTEAM;
    public final int ATTACKERS;

    public CaptureAttack(ConquestTeam ConquestTEAM, int ATTACKERS) {
        this.ConquestTEAM = ConquestTEAM;
        this.ATTACKERS = ATTACKERS;
    }
}