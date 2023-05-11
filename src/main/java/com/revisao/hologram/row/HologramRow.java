package com.revisao.hologram.row;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HologramRow {

    private Location location;

    private final EntityArmorStand armorStand;

    public HologramRow(Location location, String text) {
        this.location = location;

        this.armorStand = handleArmorStand(location, text);
    }

    protected EntityArmorStand handleArmorStand(Location location, String text) {
        EntityArmorStand armorStand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle());

        armorStand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);

        armorStand.setGravity(false);
        armorStand.setInvisible(true);

        armorStand.n(false);

        return armorStand;
    }

    public void spawn(Player player) {
        PacketPlayOutSpawnEntityLiving living = new PacketPlayOutSpawnEntityLiving(armorStand);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(living);
    }

    public void despawn(Player player) {
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(getId());

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
    }

    public void update(Player player) {
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(getId(), armorStand.getDataWatcher(), true);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(metadata);
    }

    public void respawn(Player player) {
        despawn(player);

        spawn(player);
    }

    public void teleport(Location location) {
        this.location = location;

        armorStand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public int getId() {
        return armorStand.getId();
    }

    public String getText() {
        return armorStand.getCustomName();
    }

    public void setText(String text) {
        armorStand.setCustomName(text);
    }

    public Location getLocation() {
        return location;
    }

    public EntityArmorStand getArmorStand() {
        return armorStand;
    }

}
