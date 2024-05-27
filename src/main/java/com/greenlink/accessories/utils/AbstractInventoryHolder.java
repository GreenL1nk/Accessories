package com.greenlink.accessories.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInventoryHolder implements InventoryHolder {
    protected Inventory inventory;
    protected Player requester;

    public AbstractInventoryHolder(Component title, int lines, Player requester) {
        this.inventory = Bukkit.createInventory(this, lines * 9, title);
        this.requester = requester;
    }

    protected abstract void init();

    public abstract void click(InventoryClickEvent event);
    public abstract void close(InventoryCloseEvent event);
    public abstract void onDrag(InventoryDragEvent event);

    @Override
    public final @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public final void open(){
        requester.openInventory(this.inventory);
    }

    public Player getRequester() {
        return requester;
    }

    public void shiftClickFromPlayerInventory(InventoryClickEvent event) {
        event.setCancelled(true);
    }
    public void clickFromPlayerInventory(InventoryClickEvent event) {}
}
