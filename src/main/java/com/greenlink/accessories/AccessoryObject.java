package com.greenlink.accessories;

import com.greenlink.accessories.utils.Utils;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public enum AccessoryObject {

    FIRE_ORB1("1", Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.FIRE_ORB, Utils.getStringEffect(), Utils.getStringStyle(), "Пламя души"),
    FIRE_ORB2("2", Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, AccessoryType.FIRE_ORB, Utils.getStringEffect(), Utils.getStringStyle(), "Камень Империи"),
    FIRE_ORB3("3", Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.FIRE_ORB, Utils.getStringEffect(), Utils.getStringStyle(), "Спиральный нимб"),
    CACTUS1("1", Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.CACTUS, Utils.getStringEffect(), Utils.getStringStyle(), "Парящие сферы"),
    CACTUS2("2", Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.CACTUS, Utils.getStringEffect(), Utils.getStringStyle(), "Шипованный обруч"),
    CACTUS3("3", Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.CACTUS, Utils.getStringEffect(), Utils.getStringStyle(), "Вера Самурая"),
    TEARS1("1", Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.TEARS, Utils.getStringEffect(), Utils.getStringStyle(), "Агрессивные полусферы"),
    TEARS2("2", Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.TEARS, Utils.getStringEffect(), Utils.getStringStyle(), "Диадема мечты"),
    TEARS3("3", Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.TEARS, Utils.getStringEffect(), Utils.getStringStyle(), "Великая аура"),
    RAINBOW1("1", Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.RAINBOW, Utils.getStringEffect(), Utils.getStringStyle(), "Земля Центуриона"),
    RAINBOW2("2", Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.RAINBOW, Utils.getStringEffect(), Utils.getStringStyle(), "Пышная Магма"),
    RAINBOW3("3", Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.RAINBOW, Utils.getStringEffect(), Utils.getStringStyle(), "Летний ветер"),
    COAL1("1", Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.COAL, Utils.getStringEffect(), Utils.getStringStyle(), "Зимний ураган"),
    COAL2("2", Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.COAL, Utils.getStringEffect(), Utils.getStringStyle(), "Светлое будущее"),
    COAL3("3", Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.COAL, Utils.getStringEffect(), Utils.getStringStyle(), "Признак силы"),
    SWORD1("1", Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.SWORD, Utils.getStringEffect(), Utils.getStringStyle(), "Обломки вечности"),
    SWORD2("2", Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, AccessoryType.SWORD, Utils.getStringEffect(), Utils.getStringStyle(), "Крылья свободы"),
    SWORD3("3", Material.ANGLER_POTTERY_SHERD, AccessoryType.SWORD, Utils.getStringEffect(), Utils.getStringStyle(), "Признак зла"),
    BUBBLES1("1", Material.ARCHER_POTTERY_SHERD, AccessoryType.BUBBLES, Utils.getStringEffect(), Utils.getStringStyle(), "Кислотная слизь"),
    BUBBLES2("2", Material.ARMS_UP_POTTERY_SHERD, AccessoryType.BUBBLES, Utils.getStringEffect(), Utils.getStringStyle(), "Слава победителя"),
    BUBBLES3("3", Material.BLADE_POTTERY_SHERD, AccessoryType.BUBBLES, Utils.getStringEffect(), Utils.getStringStyle(), "Цель победы"),
    SMOKE1("1", Material.BREWER_POTTERY_SHERD, AccessoryType.SMOKE, Utils.getStringEffect(), Utils.getStringStyle(), "Неисповедимые пути"),
    SMOKE2("2", Material.BURN_POTTERY_SHERD, AccessoryType.SMOKE, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    SMOKE3("3", Material.DANGER_POTTERY_SHERD, AccessoryType.SMOKE, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    GRANULE1("1", Material.EXPLORER_POTTERY_SHERD, AccessoryType.GRANULE, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    GRANULE2("2", Material.FRIEND_POTTERY_SHERD, AccessoryType.GRANULE, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    GRANULE3("3", Material.HEART_POTTERY_SHERD, AccessoryType.GRANULE, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    CLOUDS1("1", Material.HEARTBREAK_POTTERY_SHERD, AccessoryType.CLOUDS, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    CLOUDS2("2", Material.HOWL_POTTERY_SHERD, AccessoryType.CLOUDS, Utils.getStringEffect(), Utils.getStringStyle(), " "),
    CLOUDS3("3", Material.MINER_POTTERY_SHERD, AccessoryType.CLOUDS, Utils.getStringEffect(), Utils.getStringStyle(), " ");

    private final NamespacedKey key;
    private final Material material;
    private final AccessoryType accessoryType;
    private String particleEffect;
    private String particleStyle;
    private String name;

    AccessoryObject(String number, Material material, AccessoryType accessoryType, String particleEffect, String particleStyle, String name) {
        this.material = material;
        this.accessoryType = accessoryType;
        this.key = new NamespacedKey(AccessoriesMain.getInstance(), accessoryType.getKey().getKey() + number);
        this.particleEffect = particleEffect;
        this.particleStyle = particleStyle;
        this.name = name;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public Material getMaterial() {
        return material;
    }

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    @Nullable
    public ParticleEffect getParticleEffect() {
        return ParticleEffect.fromName(particleEffect);
    }

    @Nullable
    public ParticleStyle getParticleStyle() {
        return ParticleStyle.fromName(particleStyle);
    }

    public void setParticleEffect(String particleEffect) {
        this.particleEffect = particleEffect;
    }

    public void setParticleStyle(String particleStyle) {
        this.particleStyle = particleStyle;
    }

    public String getStringParticleStyle() {
        return this.particleStyle;
    }

    public int getDurability(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public void minusDurability(ItemStack itemStack, int minus) {
        int durability = getDurability(itemStack);
        if (durability <= 0) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, durability - minus);
        itemStack.setItemMeta(itemMeta);
    }

    public void addDurability(ItemStack itemStack, int count) {
        int durability = getDurability(itemStack);
        if (durability >= 100) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, durability + count);
        itemStack.setItemMeta(itemMeta);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
