package com.greenlink.accessories.listeners;

import com.greenlink.accessories.AccessoriesMain;
import com.greenlink.accessories.AccessoryObject;
import com.greenlink.accessories.AccessoryType;
import com.greenlink.accessories.PlayerAccessories;
import com.greenlink.accessories.accessories.LevelBuff;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ActionListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (ItemStack itemStack : event.getPlayer().getInventory()) {
            if (itemStack == null) continue;
            if (!itemStack.hasItemMeta()) continue;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) continue;
            if (itemMeta.getPersistentDataContainer().isEmpty()) continue;
            if (!itemMeta.getPersistentDataContainer().has(AccessoriesMain.getInstance().resistance)) continue;
            AccessoryObject accessoryObjectByItem = AccessoriesMain.getInstance().getAccessoryObjectByItem(itemStack);
            if (accessoryObjectByItem == null) continue;
            if (accessoryObjectByItem.getDurability(itemStack) <= 0) continue;
            accessoryObjectByItem.minusDurability(itemStack, 1);
            AccessoriesMain.getInstance().updateAcsItem(itemStack);
            event.getItemsToKeep().add(itemStack);
            event.getDrops().remove(itemStack);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager) {
            PlayerAccessories playerAccessories = AccessoriesMain.getInstance().getPlayerAccessories(damager.getUniqueId());
            if (playerAccessories.getCurrentAccessoryType() == AccessoryType.FIRE_ORB) {
                Integer level = playerAccessories.getAccessoryLevelByType(damager, AccessoryType.FIRE_ORB);
                if (level == null) return;
                LevelBuff buffByLevel = AccessoryType.FIRE_ORB.getAccessory().getBuffByLevel(level);
                if (buffByLevel == null) return;
                event.setDamage(event.getDamage() + ((double) buffByLevel.amplifier() / 2));
            }
        }
        if (event.getEntity() instanceof Player player) {
            PlayerAccessories playerAccessories = AccessoriesMain.getInstance().getPlayerAccessories(player.getUniqueId());
            if (playerAccessories.getCurrentAccessoryType() == AccessoryType.TEARS) {
                Integer level = playerAccessories.getAccessoryLevelByType(player, AccessoryType.TEARS);
                if (level == null) return;
                LevelBuff buffByLevel = AccessoryType.TEARS.getAccessory().getBuffByLevel(level);
                if (buffByLevel == null) return;
                event.setDamage(event.getDamage() - ((double) buffByLevel.amplifier() / 2));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        if (item.getType() != Material.FIRE_CHARGE) return;
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;
        if (!itemMeta.getPersistentDataContainer().has(AccessoriesMain.getInstance().sharpStoneKey)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDispenser(BlockDispenseEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;
        if (item.getType() != Material.FIRE_CHARGE) return;
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;
        if (!itemMeta.getPersistentDataContainer().has(AccessoriesMain.getInstance().sharpStoneKey)) return;
        event.setCancelled(true);
    }

}
