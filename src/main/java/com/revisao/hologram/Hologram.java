package com.revisao.hologram;

import com.revisao.hologram.row.HologramRow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Hologram {

    private Location location;

    private final List<HologramRow> rows = new ArrayList<>();

    private final Set<Player> viewers = new HashSet<>();

    public Hologram(Location location) {
        this.location = location;
    }

    public Hologram(Location location, List<String> text) {
        this(location);

        setText(text);
    }

    public Location getLocation() {
        return location;
    }

    public World getWorld() {
        return getLocation().getWorld();
    }

    public void teleport(Location location) {
        this.location = location;

        if (rows.isEmpty()) return;

        Location loc = location.clone();

        for (HologramRow row : rows) {
            row.teleport(loc);

            loc = loc.clone().subtract(0, 0.25, 0);
        }

        viewers.forEach(viewer -> rows.forEach(row -> row.respawn(viewer)));
    }

    public synchronized void setText(List<String> lines) {
        if (rows.isEmpty()) {
            Location loc = location.clone();

            for (String line : lines) {
                rows.add(new HologramRow(loc, line));

                loc = loc.clone().subtract(0, 0.25, 0);
            }

            rows.forEach(row -> viewers.forEach(row::spawn));
            return;
        }

        int difference = lines.size() - rows.size();

        if (difference > 0) {
            int index = 0;

            for (HologramRow row : rows) {
                row.setText(lines.get(index++));
            }

            updateRows();

            int lastIndex = rows.size() - 1;

            HologramRow lastRow = rows.get(lastIndex);
            Location loc = lastRow.getLocation().clone().subtract(0, 0.25, 0);

            while (difference > 0) {
                HologramRow row = new HologramRow(loc, lines.get(index++));

                viewers.forEach(row::spawn);

                rows.add(row);

                loc = loc.clone().subtract(0, 0.25, 0);

                difference--;
            }
        } else {
            Iterator<HologramRow> rowIterator = rows.iterator();

            for (int index = 0; rowIterator.hasNext(); index++) {
                HologramRow row = rowIterator.next();

                if (index < lines.size())
                    row.setText(lines.get(index));
                else {
                    viewers.forEach(row::despawn);

                    rowIterator.remove();
                }
            }

            updateRows();
        }
    }

    public void setText(int index, String text) {
        HologramRow row = rows.get(index);

        row.setText(text);

        viewers.forEach(row::update);
    }

    public void spawn() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            rows.forEach(row -> row.spawn(player));

            viewers.add(player);
        }
    }

    public void spawnTo(Player player) {
        if (!viewers.contains(player)) {
            viewers.add(player);

            rows.forEach(row -> row.spawn(player));
        }
    }

    public void despawn() {
        if (!viewers.isEmpty()) {

            rows.forEach(row -> viewers.forEach(row::despawn));

            viewers.clear();
        }
    }

    public void despawnTo(Player player) {
        if (viewers.contains(player)) {
            viewers.remove(player);

            rows.forEach(row -> row.despawn(player));
        }
    }

    public void updateRows() {
        rows.forEach(row -> viewers.forEach(row::update));
    }
}
