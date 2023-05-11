package com.revisao.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HologramManager {

    protected final double bukkitRange = NumberConversions.square(Bukkit.getViewDistance() << 4);

    private final Set<Hologram> globalHolograms = new HashSet<>();

    public Set<Hologram> getGlobalHolograms() {
        return globalHolograms;
    }

    public boolean canSpawn(Hologram hologram, Location location) {
        return hologram.getLocation().getWorld().equals(location.getWorld())
                && hologram.getLocation().distanceSquared(location) <= bukkitRange;
    }

    public void save(Hologram... holograms) {
        globalHolograms.addAll(Arrays.asList(holograms));
    }

    public void spawn(Hologram... holograms) {

        for (Hologram hologram : holograms) {

            if (globalHolograms.contains(hologram)) {

                for (Player player : hologram.getWorld().getPlayers()) {

                    if (canSpawn(hologram, player.getLocation()))
                        hologram.spawnTo(player);

                }
            }
        }
    }
}
