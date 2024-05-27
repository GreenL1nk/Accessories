package com.greenlink.accessories.utils;

import dev.esophose.playerparticles.particles.ParticleEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    public static List<String> styles = new ArrayList<>();

    public static void initStyles() {
        styles.add("BLOCKPLACE");
        styles.add("ARROWS");
        styles.add("BATMAN");
        styles.add("BEAM");
        styles.add("BLOCKBREAK");
        styles.add("CELEBRATION");
        styles.add("CHAINS");
        styles.add("COMPANION");
        styles.add("CUBE");
        styles.add("DEATH");
        styles.add("FEET");
        styles.add("FISHING");
        styles.add("HALO");
        styles.add("HURT");
        styles.add("ICOSPHERE");
        styles.add("INVOCATION");
        styles.add("MOVE");
        styles.add("NORMAL");
        styles.add("ORBIT");
        styles.add("OUTLINE");
        styles.add("OVERHEAD");
        styles.add("POINT");
        styles.add("POPPER");
        styles.add("PULSE");
        styles.add("QUADHELIX");
        styles.add("RINGS");
        styles.add("SPHERE");
        styles.add("SPIN");
        styles.add("SPIRAL");
        styles.add("SWORDS");
        styles.add("TELEPORT");
        styles.add("THICK");
        styles.add("TRAIL");
        styles.add("TWINS");
        styles.add("WHIRL");
        styles.add("WHIRLWIND");
        styles.add("WINGS");
    }

    public static String getStringEffect() {
        Random random = new Random();
        ParticleEffect[] values = ParticleEffect.values();
        ParticleEffect particleEffect = Arrays.stream(values).skip(random.nextInt(values.length)).findFirst().orElse(null);
        if (particleEffect == null) return "";
        return particleEffect.getName();
    }

    public static String getStringStyle() {
        Random random = new Random();
        String particleStyle = styles.stream().skip(random.nextInt(styles.size())).findFirst().orElse(null);
        if (particleStyle == null) return "";
        return particleStyle;
    }

    public static ItemStack getItem(Component name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void addLore(ItemStack itemStack, boolean resetLore, Component ... components) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if (!resetLore) {
            List<Component> oldLore = itemMeta.lore();
            if (itemMeta.hasLore() && oldLore != null) {
                lore = oldLore;
            }
        }
        for (Component component : components) {
            lore.add(component.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        }
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
    }

}
