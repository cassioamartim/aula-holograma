package com.revisao;

import com.revisao.hologram.Hologram;
import com.revisao.hologram.HologramListener;
import com.revisao.hologram.HologramManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;

public class Main extends JavaPlugin implements Listener {

    private final HologramManager hologramManager = new HologramManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HologramListener(hologramManager), this);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Hologram hologram = new Hologram(player.getLocation(), Arrays.asList(
                "§fOlá, seja bem-vindo!",
                "§aSeu nome é: §e" + player.getName()
        ));

        hologram.spawnTo(player);

        hologram.setText(Collections.singletonList("isso foi um teste"));
    }

}
