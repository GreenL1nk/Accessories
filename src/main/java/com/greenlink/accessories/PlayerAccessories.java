package com.greenlink.accessories;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Arrays;

public class PlayerAccessories {
    private AccessoryType currentAccessoryType;
    private AccessoryObject currentAccessoryObject;
    private Integer taskID = null;
    private Long nextBuff = null;

    public PlayerAccessories() {
    }

    public boolean isAccessoryItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        if (itemMeta.getPersistentDataContainer().isEmpty()) return false;
        return Arrays.stream(AccessoryType.values()).anyMatch(accessoryType -> itemMeta.getPersistentDataContainer().has(accessoryType.getKey()));
    }

    public @Nullable AccessoryType getAccessoryTypeByItem(ItemStack itemStack) {
        return Arrays.stream(AccessoryType.values())
                .filter(accessoryType -> itemStack.getItemMeta().getPersistentDataContainer().has(accessoryType.getKey()))
                .findFirst()
                .orElse(null);
    }

    public @Nullable AccessoryObject getAccessoryObjectByItem(ItemStack itemStack) {
            return Arrays.stream(AccessoryObject.values())
                .filter(accessoryObject -> itemStack.getItemMeta().getPersistentDataContainer().has(accessoryObject.getKey()))
                .findFirst()
                .orElse(null);
    }

    public @Nullable Integer getAccessoryLevel(ItemStack itemStack, AccessoryType accessoryType) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(accessoryType.getKey(), PersistentDataType.INTEGER);
    }

    public @Nullable Integer getAccessoryLevelByType(Player player, AccessoryType accessoryType) {
        for (ItemStack itemStack : player.getInventory()) {
            if (isAccessoryItem(itemStack)) {
                if (getAccessoryTypeByItem(itemStack) == accessoryType) {
                    return getAccessoryLevel(itemStack, accessoryType);
                }
            }
        }
        return null;
    }

    private void update(Player player) {
        long currentTime = System.currentTimeMillis();
        boolean updated = false;
        for (ItemStack itemStack : player.getInventory()) {
            if (isAccessoryItem(itemStack)) {
                updated = true;
                AccessoryType newAccessory = getAccessoryTypeByItem(itemStack);
                AccessoryObject accessoryObjectByItem = getAccessoryObjectByItem(itemStack);
                if (currentAccessoryObject != accessoryObjectByItem) {
                    currentAccessoryObject = accessoryObjectByItem;
                    if (AccessoriesMain.getInstance().getPpAPI() != null) {
                        AccessoriesMain.getInstance().getPpAPI().resetActivePlayerParticles(player);
                        ParticleEffect particleEffect = currentAccessoryObject.getParticleEffect();
                        ParticleStyle particleStyle = currentAccessoryObject.getParticleStyle();
                        if (particleStyle != null && particleEffect != null) {
                            AccessoriesMain.getInstance().getPpAPI().addActivePlayerParticle(player, particleEffect, particleStyle);
                        }
                    }
                }
                if (currentAccessoryType != newAccessory) currentAccessoryType = newAccessory;
                if (currentAccessoryType == null) break;
                if (nextBuff != null && currentTime < nextBuff) return;
                Integer accessoryLevel = getAccessoryLevel(itemStack, currentAccessoryType);
                if (accessoryLevel == null) return;
                Long cooldown = currentAccessoryType.getAccessory().applyBuff(player, accessoryLevel);
                if (cooldown == null) return;
                nextBuff = currentTime + cooldown;
                break;
            }
        }
        if (currentAccessoryType == null || !updated) {
            currentAccessoryType = null;
            if (AccessoriesMain.getInstance().getPpAPI() != null) {
                AccessoriesMain.getInstance().getPpAPI().resetActivePlayerParticles(player);
            }
        }
    }

    public void startScheduler(Player player) {
        if (taskID != null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(AccessoriesMain.getInstance(), () -> update((player)), 20, 20);
    }

    public void cancelScheduler() {
        if (taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    public AccessoryType getCurrentAccessoryType() {
        return currentAccessoryType;
    }

    public AccessoryObject getCurrentAccessoryObject() {
        return currentAccessoryObject;
    }
}
