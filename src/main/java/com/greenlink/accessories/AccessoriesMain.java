package com.greenlink.accessories;

import com.greenlink.accessories.accessories.LevelBuff;
import com.greenlink.accessories.commands.GiveAccessoryCommand;
import com.greenlink.accessories.commands.SharpMenuCommand;
import com.greenlink.accessories.listeners.ActionListener;
import com.greenlink.accessories.listeners.InventoryListener;
import com.greenlink.accessories.listeners.JoinListener;
import com.greenlink.accessories.listeners.QuitListener;
import com.greenlink.accessories.utils.EffectKeys;
import com.greenlink.accessories.utils.Utils;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static com.greenlink.accessories.utils.Utils.initStyles;

public final class AccessoriesMain extends JavaPlugin {

    private static AccessoriesMain instance;
    private final HashMap<UUID, PlayerAccessories> playerAccessoriesMap = new HashMap<>();
    private @Nullable PlayerParticlesAPI ppAPI;
    public final NamespacedKey sharpStoneKey = new NamespacedKey(this, "sharpstone");
    public final NamespacedKey resistance = new NamespacedKey(this, "resistance");
    private static Economy econ = null;
    private PlayerPointsAPI pointsAPI;
    private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    @Override
    public void onEnable() {
        instance = this;
        if (!setupEconomy() ) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        initStyles();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            this.ppAPI = PlayerParticlesAPI.getInstance();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            this.pointsAPI = PlayerPoints.getInstance().getAPI();
        }

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ActionListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        new GiveAccessoryCommand().register(this, "giveaccessories");
        new SharpMenuCommand().register(this, "sharpmenu");

        for (AccessoryObject accessoryObject : AccessoryObject.values()) {
            String particleType = this.getConfig().getString("accessories." + accessoryObject + ".particleEffect");
            String particleStyle = this.getConfig().getString("accessories." + accessoryObject.name() + ".particleStyle");
            String name = this.getConfig().getString("accessories." + accessoryObject.name() + ".name");
            if (particleType == null || particleType.equalsIgnoreCase("")) {
                ParticleEffect particleEffect = accessoryObject.getParticleEffect();
                if (particleEffect != null) {
                    this.getConfig().set("accessories." + accessoryObject.name() + ".particleEffect", particleEffect.getName());
                }
                else {
                    this.getConfig().set("accessories." + accessoryObject.name() + ".particleEffect", "totem_of_undying");
                }
            }
            else accessoryObject.setParticleEffect(particleType);

            if (particleStyle == null || particleStyle.equalsIgnoreCase("")) {
                ParticleStyle ps = accessoryObject.getParticleStyle();
                if (ps != null) {
                    this.getConfig().set("accessories." + accessoryObject.name() + ".particleStyle", ps.getName());
                }
                else {
                    this.getConfig().set("accessories." + accessoryObject.name() + ".particleStyle", accessoryObject.getStringParticleStyle());
                }
            }
            else accessoryObject.setParticleStyle(particleStyle);

            if (name == null || name.equalsIgnoreCase("")) {
                this.getConfig().set("accessories." + accessoryObject.name() + ".name", accessoryObject.getName());
            }
            else {
                accessoryObject.setName(name);
            }
            saveConfig();
        }
    }

    @Override
    public void onDisable() {

    }

    public void updateAcsItem(ItemStack itemStack) {
        AccessoryObject accessoryObjectByItem = getAccessoryObjectByItem(itemStack);
        if (accessoryObjectByItem == null) return;
        Utils.addLore(itemStack, true, Component.text("Уровень: ", NamedTextColor.GRAY)
                .append(Component.text(accessoryObjectByItem.getAccessoryType().getLevel(itemStack), NamedTextColor.GOLD)));
        if (itemStack.getItemMeta().getPersistentDataContainer().has(resistance)) {
            Utils.addLore(itemStack, false, Component.text("Прочность: ", NamedTextColor.GRAY)
                    .append(Component.text(accessoryObjectByItem.getDurability(itemStack), NamedTextColor.YELLOW))
                    .append(Component.text("/", NamedTextColor.GRAY))
                    .append(Component.text("100", NamedTextColor.YELLOW)));
        }
        PotionEffectType potionEffectType = accessoryObjectByItem.getAccessoryType().getAccessory().potionEffectType();
        int level = accessoryObjectByItem.getAccessoryType().getLevel(itemStack);
        LevelBuff buffByLevel = accessoryObjectByItem.getAccessoryType().getAccessory().getBuffByLevel(level);
        if (buffByLevel == null) return;
        if (potionEffectType != null) {
            TranslatableComponent translatableComponent = Component.translatable("effect.minecraft." + EffectKeys.valueOf(potionEffectType.getName()).getKey())
                    .append(Component.text( " " + NUMERALS[buffByLevel.amplifier()]))
                    .color(NamedTextColor.YELLOW)
                    .decoration(TextDecoration.ITALIC, false);
            Utils.addLore(itemStack, false, Component.text(" "),
                    translatableComponent,
                    Component.text("Действует: ", NamedTextColor.GRAY).append(Component.text(buffByLevel.duration() + "с", NamedTextColor.YELLOW)),
                    Component.text("Перезарядка: ", NamedTextColor.GRAY).append(Component.text(buffByLevel.cooldown()  + "с", NamedTextColor.YELLOW))
            );
        }
        else {
            if (accessoryObjectByItem.getAccessoryType() == AccessoryType.FIRE_ORB) {
                Utils.addLore(itemStack, false, Component.text(" "),
                        Component.text("Пасивно увеличивает урон: ", NamedTextColor.GRAY).append(Component.text(buffByLevel.amplifier(), NamedTextColor.YELLOW))
                );
            }
            if (accessoryObjectByItem.getAccessoryType() == AccessoryType.TEARS) {
                Utils.addLore(itemStack, false, Component.text(" "),
                        Component.text("Пасивно поглощает урон: ", NamedTextColor.GRAY).append(Component.text(buffByLevel.amplifier(), NamedTextColor.YELLOW))
                );
            }
        }
    }

    public static AccessoriesMain getInstance() {
        return instance;
    }

    public PlayerAccessories getPlayerAccessories(UUID uuid) {
        if (playerAccessoriesMap.containsKey(uuid)) return playerAccessoriesMap.get(uuid);
        return addPlayerAccessories(uuid);
    }

    private PlayerAccessories addPlayerAccessories(UUID uuid) {
        PlayerAccessories playerAccessories = new PlayerAccessories();
        playerAccessoriesMap.put(uuid, playerAccessories);
        return playerAccessories;
    }

    @Nullable
    public PlayerParticlesAPI getPpAPI() {
        return ppAPI;
    }

    @Nullable
    public AccessoryObject getAccessoryObjectByItem(ItemStack itemStack) {
        if (itemStack == null) return null;
        if (!itemStack.hasItemMeta()) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        if (itemMeta.getPersistentDataContainer().isEmpty()) return null;
        return Arrays.stream(AccessoryObject.values())
                .filter(accessoryObject -> itemMeta.getPersistentDataContainer().has(accessoryObject.getKey()))
                .findFirst()
                .orElse(null);
    }
    @Nullable
    public AccessoryType getAccessoryTypeByItem(ItemStack itemStack) {
        if (itemStack == null) return null;
        if (!itemStack.hasItemMeta()) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        if (itemMeta.getPersistentDataContainer().isEmpty()) return null;
        return Arrays.stream(AccessoryType.values())
                .filter(accessoryType -> itemMeta.getPersistentDataContainer().has(accessoryType.getKey()))
                .findFirst()
                .orElse(null);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Nullable
    public PlayerPointsAPI getPointsAPI() {
        return pointsAPI;
    }
}
