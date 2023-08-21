package me.catholiccatboy.battleboxbmcc.listeners;

import me.catholiccatboy.battleboxbmcc.BattleBoxBMCC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CreeperKillCredit implements Listener {

    @EventHandler
    public void CreeperSpawn(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action action = e.getAction();
        if(Bukkit.getWorld("world").getBlockAt(0,140,0).getType() == Material.CYAN_TERRACOTTA){
            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (p.getInventory().getItemInMainHand().getType() == Material.CREEPER_SPAWN_EGG) {
                    e.setCancelled(true);
                    Block block = p.getTargetBlock(null, 5);
                    Location eyeLoc = block.getLocation();
                    Entity creeper = p.getWorld().spawnEntity(eyeLoc.add(0.5, 1, 0.5), EntityType.CREEPER);
                    PersistentDataContainer creeperdata = creeper.getPersistentDataContainer();
                    NamespacedKey nk = new NamespacedKey(BattleBoxBMCC.getPlugin(), "killer");
                    creeperdata.set(nk, PersistentDataType.STRING, p.getName());
                }
            }
        }
    }

    @EventHandler
    public void tntspawn(BlockPlaceEvent e){
        Block block = e.getBlock();
        Player p = e.getPlayer();
        if(Bukkit.getWorld("world").getBlockAt(0,140,0).getType() == Material.CYAN_TERRACOTTA) {
            if (block.getType() == Material.TNT) {
                e.setCancelled(true);
                TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation().add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
                tnt.setSource(p);
            }
        }
    }

    @EventHandler
    public void CreeperKillCredit(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getEntity();
        Entity killer = e.getDamager();
        if (Bukkit.getWorld("world").getBlockAt(0, 140, 0).getType() == Material.CYAN_TERRACOTTA) {
            if (killer.getType() == EntityType.CREEPER) {
                PersistentDataContainer killerdata = killer.getPersistentDataContainer();
                NamespacedKey nk = new NamespacedKey(BattleBoxBMCC.getPlugin(), "killer");
                if (killerdata.has(nk, PersistentDataType.STRING)) {
                    Player killerplayer = Bukkit.getPlayer(killerdata.get(nk, PersistentDataType.STRING));
                    ((HumanEntity) player).damage(e.getFinalDamage(), killerplayer);
                }
            }
            if (killer.getType() == EntityType.PRIMED_TNT) {
                TNTPrimed tnt = (TNTPrimed) killer;
                ((HumanEntity) player).damage(e.getFinalDamage(), tnt.getSource());
            }
        }
    }

    @EventHandler
    public void tntnobreak(EntityExplodeEvent e){
        Entity entity = e.getEntity();
        if (Bukkit.getWorld("world").getBlockAt(0, 140, 0).getType() == Material.CYAN_TERRACOTTA) {
            if (entity.getType() == EntityType.PRIMED_TNT) {
                e.blockList().clear();
            }
        }
    }
}
