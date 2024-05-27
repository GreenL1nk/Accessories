package com.greenlink.accessories;

import com.greenlink.accessories.accessories.Accessory;
import com.greenlink.accessories.accessories.LevelBuff;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

public enum AccessoryType {

    FIRE_ORB("fireorb", new Accessory(null,
            new LevelBuff(2, 0, 0, 1),
            new LevelBuff(5, 0, 0, 2),
            new LevelBuff(8, 0, 0, 3),
            new LevelBuff(10, 0, 0, 4)
    )),
    CACTUS("cactus", new Accessory(PotionEffectType.REGENERATION,
            new LevelBuff(2, 60, 5, 0),
            new LevelBuff(5, 60, 10, 0),
            new LevelBuff(10, 60, 10, 1)
    )),
    TEARS("tears", new Accessory(null,
            new LevelBuff(2, 0, 0, 1),
            new LevelBuff(6, 0, 0, 2),
            new LevelBuff(10, 0, 0, 3)
    )),
    RAINBOW("rainbow", new Accessory(PotionEffectType.SPEED,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    )),
    COAL("coal", new Accessory(PotionEffectType.FAST_DIGGING,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    )),
    SWORD("sword", new Accessory(PotionEffectType.INCREASE_DAMAGE,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    )),
    BUBBLES("bubbles", new Accessory(PotionEffectType.WATER_BREATHING,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    )),
    SMOKE("smoke", new Accessory(PotionEffectType.FIRE_RESISTANCE,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    )),
    GRANULE("granule", new Accessory(PotionEffectType.SATURATION,
            new LevelBuff(2, 60, 1),
            new LevelBuff(5, 60, 2),
            new LevelBuff(7, 60, 3),
            new LevelBuff(10, 60, 4)
    )),
    CLOUDS("clouds", new Accessory(PotionEffectType.SLOW_FALLING,
            new LevelBuff(2, 60, 10, 0),
            new LevelBuff(5, 60, 15, 0),
            new LevelBuff(7, 60, 15, 1),
            new LevelBuff(10, 60, 20, 1)
    ));

    private final NamespacedKey key;
    private final Accessory accessory;

    AccessoryType(String key, Accessory accessory) {
        this.key = new NamespacedKey(AccessoriesMain.getInstance(), key);
        this.accessory = accessory;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public int getLevel(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 1);
    }

    public void addLevel(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, getLevel(itemStack) + 1);
        itemStack.setItemMeta(itemMeta);
    }
}
