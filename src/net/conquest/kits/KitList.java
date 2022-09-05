package net.conquest.kits;

import net.conquest.entities.abilities.storage.assassin.SharpShooterAbility;
import net.conquest.entities.abilities.storage.firemage.FireBreathAbility;
import net.conquest.entities.abilities.storage.maniac.IrresponsibleAbility;
import net.conquest.entities.abilities.storage.maniac.SuicideAbility;
import net.conquest.entities.abilities.storage.mechanic.AutoTurretAbility;
import net.conquest.entities.abilities.storage.mechanic.MineAbility;
import net.conquest.entities.abilities.storage.assassin.ShadowDashAbility;
import net.conquest.entities.abilities.storage.assassin.WallRunAbility;
import net.conquest.menu.item.game.ItemList;
import org.bukkit.Material;

import java.util.ArrayList;

public class KitList {
    private static final ArrayList<Kit> kits;

    static {
        kits = new ArrayList<>();

        //Assassin
        Kit kit = new Kit("Assassin", "An agile and dangerous unit lurking in the shadows.", Material.BOW,
                ItemList.IRON_DAGGER,
                ItemList.BOW,
                ItemList.ARROW,
                ItemList.WHITE_HOOD,
                ItemList.RED_CLOAK,
                ItemList.BROWN_LEGGINGS,
                ItemList.BLACK_BOOTS
        ); //TODO: Wall jump, wall run
        kit.setAbilities(new WallRunAbility(), new ShadowDashAbility(), new SharpShooterAbility());
        kits.add(kit);

        kit = new Kit("Maniac", "He changed. He is different now.//He just got released from the madhouse.////The third time...", Material.FLINT_AND_STEEL,
                ItemList.RED_HOOD,
                ItemList.WHITE_CLOAK,
                ItemList.RED_LEGGINGS,
                ItemList.BLACK_BOOTS,
                ItemList.ELVEN_SWORD
        );
        kit.setAbilities(new IrresponsibleAbility(), new SuicideAbility());
        kits.add(kit);

        kit = new Kit("Mechanic", "A unit that crafts its materials itself.", Material.IRON_INGOT,
                ItemList.SAFETY_HELMET,
                ItemList.BOILER_SUIT_CHEST,
                ItemList.BOILER_SUIT_LEGGINGS,
                ItemList.BLACK_BOOTS,
                ItemList.WRENCH
        );
        kit.setAbilities(new MineAbility(), new AutoTurretAbility());

        kits.add(kit);
    }

    public static ArrayList<Kit> getKits() {
        return kits;
    }
}
