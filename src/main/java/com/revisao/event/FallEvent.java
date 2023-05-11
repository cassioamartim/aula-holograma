package com.revisao.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FallEvent extends Event {

    private final Player player;

    public FallEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
