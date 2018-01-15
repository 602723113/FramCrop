package cc.zoyn.farmcrop;

import cc.zoyn.farmcrop.listener.BlockBreakListener;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author Zoyn
 * @since 2018-01-14
 */
public class FarmCrop extends JavaPlugin {

    private static FarmCrop instance;
    private List<ClaimedResidence> residences;
    private String tick;

    @Override
    public void onEnable() {
        instance = this;
        residences = Lists.newArrayList();

        saveDefaultConfig();
        List<String> residenceNames = getConfig().getStringList("residences");
        residenceNames.forEach(
                s -> residences.add(((Residence) Bukkit.getPluginManager().getPlugin("Residence")).getResidenceManager().getByName(s))
        );

        tick = getConfig().getString("tick");
        // 设置生长速度
        residences.forEach(residence -> {
            if (residence == null) {
                getLogger().info("加载领地世界时发生了错误!");
                return;
            }
            World world = Bukkit.getWorld(residence.getWorld());
            Validate.notNull(world).setGameRuleValue("randomTickSpeed", tick);
        });

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    public static FarmCrop getInstance() {
        return instance;
    }

    public List<ClaimedResidence> getResidences() {
        return residences;
    }
}
