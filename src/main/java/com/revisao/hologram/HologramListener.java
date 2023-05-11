package com.revisao.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class HologramListener implements Listener {

    private final HologramManager manager;

    public HologramListener(HologramManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        updateHolograms(event.getPlayer());
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        updateHolograms(event.getPlayer());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        updateHolograms(event.getPlayer());
    }

    @EventHandler
    public void kick(PlayerKickEvent event) {
        updateHolograms(event.getPlayer());
    }

    private void updateHolograms(Player player) {
        Location loc = player.getLocation();

        for (Hologram globalHologram : manager.getGlobalHolograms()) {
            if (manager.canSpawn(globalHologram, loc))
                globalHologram.spawnTo(player);
            else
                globalHologram.despawnTo(player);
        }

    }
}
