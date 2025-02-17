package net.patyhank.noTridentDupe;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentHashMap;

public final class NoTridentDupe extends JavaPlugin implements Listener {

    private static ConcurrentHashMap<HumanEntity, Integer> projectileCancelMap = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLaunchProjectile(PlayerLaunchProjectileEvent event) {
        if (event.getItemStack().getType() != Material.TRIDENT) {
            return;
        }
        event.getPlayer().closeInventory();
        Integer tick = projectileCancelMap.getOrDefault(event.getPlayer(), -1);
        if (getServer().getCurrentTick() == tick) {
            event.setCancelled(true);
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerStopUsingItem(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() != Material.TRIDENT) {
            return;
        }

        if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
            projectileCancelMap.put(event.getWhoClicked(), getServer().getCurrentTick());
            event.setResult(Event.Result.DENY);
        }
    }
}
