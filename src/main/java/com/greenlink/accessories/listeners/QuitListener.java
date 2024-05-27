package com.greenlink.accessories.listeners;

import com.greenlink.accessories.AccessoriesMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        AccessoriesMain.getInstance().getPlayerAccessories(event.getPlayer().getUniqueId()).cancelScheduler();
    }

}
