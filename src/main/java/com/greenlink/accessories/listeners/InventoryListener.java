package com.greenlink.accessories.listeners;

import com.greenlink.accessories.utils.AbstractInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getView().getTopInventory().getHolder() instanceof AbstractInventoryHolder) {
                if (event.getRawSlot() > event.getView().getTopInventory().getSize() - 1) {
                    ((AbstractInventoryHolder) event.getView().getTopInventory().getHolder()).clickFromPlayerInventory(event);
                }
            }
            if (event.getClickedInventory().getHolder() instanceof AbstractInventoryHolder) {
                ((AbstractInventoryHolder) event.getClickedInventory().getHolder()).click(event);
            }
            if (event.getInventory().getHolder() instanceof AbstractInventoryHolder && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                ((AbstractInventoryHolder) event.getInventory().getHolder()).shiftClickFromPlayerInventory(event);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getRawSlots().stream().anyMatch(slot -> slot <= event.getView().getTopInventory().getSize() - 1)) {
            if (event.getInventory().getHolder() instanceof AbstractInventoryHolder)
                ((AbstractInventoryHolder) event.getInventory().getHolder()).onDrag(event);
        }
    }


    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof AbstractInventoryHolder) {
            ((AbstractInventoryHolder) event.getInventory().getHolder()).close(event);
        }
    }
}
