package cc.zoyn.farmcrop.listener;

import cc.zoyn.farmcrop.FarmCrop;
import cc.zoyn.farmcrop.util.BlockUtils;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

/**
 * @author Zoyn
 * @since 2018-01-14
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ClaimedResidence residence = Residence.getInstance().getResidenceManager().getByLoc(block.getLocation());
        if (residence != null) {
            if (FarmCrop.getInstance().getResidences().contains(residence)) {
                if (BlockUtils.isCrop(block)) {
                    // 判断是否已成熟
                    if (!BlockUtils.isMature(block)) {
                        player.sendMessage(FarmCrop.getInstance().getConfig().getString("tip").replaceAll("&", "§"));
                        event.setCancelled(true);
                    } else {
                        Material material = block.getType();
                        World world = block.getWorld();
                        Location location = block.getLocation();

                        if (material.equals(Material.CROPS)) {
                            block.breakNaturally();
                            event.setCancelled(true);
                            world.getBlockAt(location).setType(Material.CROPS);
                            Crops crops = (Crops) block.getState().getData();
                            // 设置为发芽
                            crops.setState(CropState.GERMINATED);
                        }
                        if (material.equals(Material.CARROT)) {
                            block.breakNaturally();
                            event.setCancelled(true);
                            world.getBlockAt(location).setType(Material.CARROT);
                            Crops crops = (Crops) block.getState().getData();
                            // 设置为发芽
                            crops.setState(CropState.GERMINATED);
                        }
                        if (material.equals(Material.POTATO)) {
                            block.breakNaturally();
                            event.setCancelled(true);
                            world.getBlockAt(location).setType(Material.POTATO);
                            Crops crops = (Crops) block.getState().getData();
                            // 设置为发芽
                            crops.setState(CropState.GERMINATED);
                        }

                        if (material.equals(Material.NETHER_WARTS)) {
                            block.breakNaturally();
                            event.setCancelled(true);
                            world.getBlockAt(location).setType(Material.NETHER_WARTS);

                            NetherWarts netherWarts = (NetherWarts) block.getState().getData();
                            netherWarts.setState(NetherWartsState.SEEDED);
                        }
                    }
                }
            }
        }
    }


}
