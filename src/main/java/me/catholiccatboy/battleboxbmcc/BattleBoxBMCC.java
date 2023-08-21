package me.catholiccatboy.battleboxbmcc;

import me.catholiccatboy.battleboxbmcc.listeners.CreeperKillCredit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BattleBoxBMCC extends JavaPlugin {

    public static BattleBoxBMCC plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getServer().getPluginManager().registerEvents(new CreeperKillCredit(),this);

        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BattleBoxBMCC getPlugin() {
        return plugin;
    }
}
