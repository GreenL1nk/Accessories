package com.greenlink.accessories.listeners;

import com.greenlink.accessories.AccessoriesMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        AccessoriesMain.getInstance().getPlayerAccessories(event.getPlayer().getUniqueId()).startScheduler(event.getPlayer());
    }

}
