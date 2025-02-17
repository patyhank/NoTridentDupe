package net.patyhank.noTridentDupe;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoTridentDupe extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
