package com.revisao;

import com.revisao.event.FallEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();

            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {

                Bukkit.getPluginManager().callEvent(new FallEvent(player));
            }
        }
    }

    @EventHandler
    public void fall(FallEvent event) {
        Player player = event.getPlayer();

        player.sendMessage("§cVocê caiu!");
    }
}
