package net.conquest.entities.abilities.storage.assassin;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class CreepBonusAbility extends PassiveAbility {

    public CreepBonusAbility() {
        super(AbilityList.CREEP);
    }

    @Override
    public Ability copy() {
        return new CreepBonusAbility();
    }

    @Override
    protected void onMove(ConquestPlayer player) {

    }

    @Override
    protected void onDeath(ConquestPlayer player) {

    }

    @Override
    protected void onSpawn(ConquestPlayer player) {

    }

    @Override
    protected void onLaunchArrow(ConquestPlayer player, Arrow arrow) {

    }

    @Override
    protected void onAttack(ConquestPlayer player, ConquestEntity toAttack) {
        Vector pDir = player.getOwner().getLocation().getDirection();
        Vector eDir = toAttack.getBukkitEntity().getLocation().getDirection();
        double xv = pDir.getX() * eDir.getZ() - pDir.getZ() * eDir.getX();
        double zv = pDir.getX() * eDir.getX() + pDir.getZ() * eDir.getZ();
        double angle = Math.atan2(xv, zv); // Value between -π and +π
        double angleInDegrees = (angle * 180) / Math.PI;

        if (angleInDegrees <= 90 && angleInDegrees >= -90) {
            //Deal bonus damage
            Util.playSoundAtAll(Sound.ITEM_TRIDENT_HIT, toAttack.getBukkitEntity().getLocation());
            toAttack.damageAsync(6, player);
        }
    }

    @Override
    protected void onAttacked(ConquestPlayer player, ConquestEntity attacker, EntityDamageEvent.DamageCause cause) {

    }

    @Override
    protected void onRegenerateHealth(ConquestPlayer player) {

    }
}
