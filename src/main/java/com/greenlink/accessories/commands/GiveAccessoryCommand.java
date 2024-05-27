package com.greenlink.accessories.commands;

import com.greenlink.accessories.AccessoriesMain;
import com.greenlink.accessories.AccessoryObject;
import com.greenlink.accessories.utils.AbstractCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GiveAccessoryCommand extends AbstractCommand {
    @Override
    protected void onPlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if (!player.isOp()) return;
        if (args.length == 4) {
            Player toAdd = Bukkit.getPlayer(args[0]);
            if (toAdd == null) return;
            if (args[1].equalsIgnoreCase("ТочильныйКамень")) {
                try {
                    int count = Integer.parseInt(args[3]);
                    ItemStack itemStack = new ItemStack(Material.FIRE_CHARGE, count);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Точильный камень", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                    itemMeta.getPersistentDataContainer().set(AccessoriesMain.getInstance().sharpStoneKey, PersistentDataType.BOOLEAN, true);
                    itemStack.setItemMeta(itemMeta);
                    toAdd.getInventory().addItem(itemStack);
                } catch (NumberFormatException ignored) {}
            }
            else {
                AccessoryObject accessory = Arrays.stream(AccessoryObject.values())
                        .filter(accessoryObject -> accessoryObject.getName().replace(" ", "").equalsIgnoreCase(args[1]))
                        .findFirst()
                        .orElse(null);
                if (accessory != null) {
                    try {
                        int level = Integer.parseInt(args[2]);
                        int count = Integer.parseInt(args[3]);
                        ItemStack itemStack = new ItemStack(accessory.getMaterial(), count);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
                        itemMeta.displayName(Component.text(accessory.getName(), NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                        itemMeta.getPersistentDataContainer().set(accessory.getKey(), PersistentDataType.INTEGER, 100);
                        itemMeta.getPersistentDataContainer().set(accessory.getAccessoryType().getKey(), PersistentDataType.INTEGER, level);
                        itemStack.setItemMeta(itemMeta);
                        AccessoriesMain.getInstance().updateAcsItem(itemStack);
                        toAdd.getInventory().addItem(itemStack);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }

    @Override
    protected void onConsoleCommand(@NotNull CommandSender commandSender, @NotNull String[] args) {
        if (args.length == 4) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) return;
            if (args[1].equalsIgnoreCase("ТочильныйКамень")) {
                try {
                    int count = Integer.parseInt(args[3]);
                    ItemStack itemStack = new ItemStack(Material.FIRE_CHARGE, count);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Точильный камень", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                    itemMeta.getPersistentDataContainer().set(AccessoriesMain.getInstance().sharpStoneKey, PersistentDataType.BOOLEAN, true);
                    itemStack.setItemMeta(itemMeta);
                    player.getInventory().addItem(itemStack);
                } catch (NumberFormatException ignored) {}
            }
            else {
                AccessoryObject accessory = Arrays.stream(AccessoryObject.values())
                        .filter(accessoryObject -> accessoryObject.getName().replace(" ", "").equalsIgnoreCase(args[1]))
                        .findFirst()
                        .orElse(null);
                if (accessory != null) {
                    try {
                        int level = Integer.parseInt(args[2]);
                        int count = Integer.parseInt(args[3]);
                        ItemStack itemStack = new ItemStack(accessory.getMaterial(), count);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
                        itemMeta.displayName(Component.text(accessory.getKey().getKey()));
                        itemMeta.getPersistentDataContainer().set(accessory.getKey(), PersistentDataType.INTEGER, 100);
                        itemMeta.getPersistentDataContainer().set(accessory.getAccessoryType().getKey(), PersistentDataType.INTEGER, level);
                        itemStack.setItemMeta(itemMeta);
                        AccessoriesMain.getInstance().updateAcsItem(itemStack);
                        player.getInventory().addItem(itemStack);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }

    @Override
    protected List<String> onPlayerTab(@NotNull Player player, @NotNull String[] args) {
        if (!player.isOp()) return null;
        if (args.length == 2) {
            List<String> collect = Arrays.stream(AccessoryObject.values()).map(accessoryObject -> accessoryObject.getName().replace(" ", "")).collect(Collectors.toList());
            collect.add("ТочильныйКамень");
            return collect;
        }
        if (args.length == 3) {
            return Collections.singletonList("<уровень>");
        }
        if (args.length == 4) {
            return Collections.singletonList("<кол-во>");
        }
        return null;
    }

    @Override
    protected List<String> onConsoleTab(@NotNull CommandSender commandSender, @NotNull String[] args) {
        if (args.length == 1) {
            return AccessoriesMain.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if (args.length == 2) {
            List<String> collect = Arrays.stream(AccessoryObject.values()).map(AccessoryObject::getName).collect(Collectors.toList());
            collect.add("Точильный камень");
            return collect;
        }
        if (args.length == 3) {
            return Collections.singletonList("<уровень>");
        }
        if (args.length == 4) {
            return Collections.singletonList("<кол-во>");
        }
        return null;
    }
}
