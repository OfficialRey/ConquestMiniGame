package net.conquest.entities.abilities;

import net.conquest.menu.item.BaseItem;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum AbilityList {


    //ASSASSIN
    SHADOW_DASH("Shadow Dash", Arrays.asList("Perform a dash to risk a strategic escape.", "Damages all opponents in your path."), Material.FEATHER, 5, 7, 12),
    WALL_RUN("Wall Run", Arrays.asList("Allows vertical and horizontal wall running"), Material.LEATHER_BOOTS, 0, 0),
    SHARPSHOOTER("Sharpshooter", Arrays.asList("Grants a chance to launch a", "highly precise arrow at tremendous speed.", "The next shot also costs no ammunition."), Material.ARROW, 0, 7),

    //MANIAC
    IRRESPONSIBLE("Irresponsibility", Arrays.asList("You have never shown responsibility for everything.", "You know the aftermath of your actions", "but you care less about them."), Material.LEATHER_CHESTPLATE, 0, 0),
    MINE("Mine Field", Arrays.asList("Places a mine on the ground for your opponents to step onto."), Material.STONE_PRESSURE_PLATE, 10, 15),
    SUICIDE("Suicide", Arrays.asList("Leave with style."), Material.TNT, 30, 40),

    //FIREMAGE
    FIRE_BREATH("Fire Breath", Arrays.asList("Become a flamethrower."), Material.FLINT_AND_STEEL, 5, 7, 35),

    //MECHANIC
    AUTO_TURRET("Automatic Turret", Arrays.asList("Shoots your opponents.", "Literally does your job but is unpaid."), Material.DISPENSER, 5, 11, 0),


    //NULL_ABILITY
    NONE("", Arrays.asList(""), Material.AIR, 0, 0, 0);

    public final String TITLE;
    public final List<String> DESCRIPTION;
    public final Material MATERIAL;
    public final int DAMAGE;
    public final int COOLDOWN;
    public final int REPETITIONS;

    AbilityList(String TITLE, List<String> DESCRIPTION, Material MATERIAL, int DAMAGE, int COOLDOWN, int REPETITIONS) {
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.MATERIAL = MATERIAL;
        this.DAMAGE = DAMAGE;
        this.COOLDOWN = COOLDOWN;
        this.REPETITIONS = REPETITIONS;
    }

    AbilityList(String TITLE, List<String> DESCRIPTION, Material MATERIAL, int DAMAGE, int COOLDOWN) {
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.MATERIAL = MATERIAL;
        this.DAMAGE = DAMAGE;
        this.COOLDOWN = COOLDOWN;
        this.REPETITIONS = 0;
    }
}