package cc.zoyn.farmcrop.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

/**
 * @author Zoyn
 * @since 2018-01-14
 */
public final class BlockUtils {

    private BlockUtils() {
    }

    public static boolean isCrop(Block block) {
        boolean isCrop = false;
        Material material = Validate.notNull(block).getType();
        switch (material) {
            case CROPS:
            case POTATO:
            case CARROT:
            case NETHER_WARTS:
                isCrop = true;
        }
        return isCrop;
    }

    public static boolean isMature(Crops crops) {
        return Validate.notNull(crops).getState().equals(CropState.RIPE);
    }

    public static boolean isMature(Block block) {
        if (!isCrop(block)) {
            return false;
        } else {
            if (block.getType().equals(Material.NETHER_WARTS)) {
                return ((NetherWarts) block.getState().getData()).getState().equals(NetherWartsState.RIPE);
            }
            Crops crops = (Crops) block.getState().getData();
            return crops.getState().equals(CropState.RIPE);
        }
    }

}
