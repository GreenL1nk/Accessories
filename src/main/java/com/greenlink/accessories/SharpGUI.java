package com.greenlink.accessories;

import com.greenlink.accessories.utils.AbstractInventoryHolder;
import com.greenlink.accessories.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;

public class SharpGUI extends AbstractInventoryHolder {

    LastAction lastAction = LastAction.NONE;
    boolean canSharp = false;
    int level;
    public SharpGUI(Player requester) {
        super(Component.text(" "), 3, requester);
        this.init();
    }

    @Override
    protected void init() {
        ItemStack nullItem = Utils.getItem(Component.text(" "), Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 27; i++) {
            if (i == 11) continue;
            if (i == 15) continue;
            this.inventory.setItem(i, nullItem);
        }

    }

    @Override
    public void clickFromPlayerInventory(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (isSharpStone(currentItem)) {
            event.setCancelled(true);
            swapItems(event.getCurrentItem(), event.getView().getTopInventory().getItem(11), event.getSlot(), 11, event.getView().getBottomInventory(), event.getView().getTopInventory());
            requester.playSound(requester.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        }
        if (isAccessoryItem(currentItem)) {
            event.setCancelled(true);
            if (currentItem != null) {
                if (currentItem.getAmount() > 1) {
                    ItemStack item = event.getView().getTopInventory().getItem(15);
                    if (item != null) {
                        requester.getInventory().addItem(item);
                    }
                    event.getView().getTopInventory().setItem(15, currentItem.asQuantity(1));
                    currentItem.subtract();
                } else {
                    swapItems(event.getCurrentItem(), event.getView().getTopInventory().getItem(15), event.getSlot(), 15, event.getView().getBottomInventory(), event.getView().getTopInventory());
                }
            }
            requester.playSound(requester.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        }
        update();
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int rawSlot = event.getRawSlot();
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem != null && !currentItem.getType().isAir()) {
            if (rawSlot == 11) {
                requester.getInventory().addItem(currentItem);
                event.getInventory().setItem(11, null);
                requester.playSound(requester.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, 1);
            }
            if (rawSlot == 15) {
                requester.getInventory().addItem(currentItem);
                event.getInventory().setItem(15, null);
                requester.playSound(requester.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, 1);
            }
            Economy economy = AccessoriesMain.getEconomy();
            if (rawSlot == 22 && canSharp) {
                ItemStack sharpItem = this.inventory.getItem(11);
                ItemStack acsItem = this.inventory.getItem(15);
                if (sharpItem != null && acsItem != null) {
                    sharpItem.subtract(1);
                    LevelUpChance nextLevel = Arrays.stream(LevelUpChance.values()).filter(levelUpChance -> levelUpChance.getLevel() == level).findFirst().orElse(null);
                    if (nextLevel != null) {
                        double balance = economy.getBalance(requester);
                        if (balance < 3) {
                            requester.sendMessage(Component.text("У вас нету ", NamedTextColor.GRAY)
                                    .append(Component.text("3 Coins", NamedTextColor.GOLD)));
                            return;
                        }
                        economy.withdrawPlayer(requester, 3);
                        if (nextLevel.rollChance()) {
                            addAcsLevel(acsItem);
                            lastAction = LastAction.SUCCESS;
                            requester.playSound(requester.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                        } else {
                            lastAction = LastAction.FAIL;
                            requester.playSound(requester.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 2);
                        }
                    }
                }
            }
            if (rawSlot == 18) {
                ItemStack acsItem = this.inventory.getItem(15);
                if (acsItem != null && isAccessoryItem(acsItem)) {
                    ItemMeta itemMeta = acsItem.getItemMeta();
                    if (itemMeta.getPersistentDataContainer().has(AccessoriesMain.getInstance().resistance)) {
                        AccessoryObject accessoryObjectByItem = AccessoriesMain.getInstance().getAccessoryObjectByItem(acsItem);
                        if (accessoryObjectByItem != null) {
                            int durability = accessoryObjectByItem.getDurability(acsItem);
                            if (durability == 100) return;
                            if (event.isShiftClick()) {
                                int toRestore = 100 - durability;
                                double cost = toRestore * 1.2;
                                if (economy.has(requester, cost)) {
                                    accessoryObjectByItem.addDurability(acsItem, toRestore);
                                    economy.withdrawPlayer(requester, cost);
                                    requester.playSound(requester.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
                                }
                                else {
                                    requester.sendMessage(Component.text("У вас нету ", NamedTextColor.GRAY)
                                            .append(Component.text(cost + " Coins", NamedTextColor.GOLD)));
                                    requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                }
                            }
                            else {
                                if (economy.has(requester, 1.2)) {
                                    accessoryObjectByItem.addDurability(acsItem, 1);
                                    economy.withdrawPlayer(requester, 1.2);
                                    requester.playSound(requester.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
                                }
                                else {
                                    requester.sendMessage(Component.text("У вас нету ", NamedTextColor.GRAY)
                                            .append(Component.text("1.2 Coins", NamedTextColor.GOLD)));
                                    requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                }
                            }
                        }
                    }
                    else {
                        PlayerPointsAPI pointsAPI = AccessoriesMain.getInstance().getPointsAPI();
                        if (pointsAPI != null) {
                            if (pointsAPI.take(requester.getUniqueId(), 19)) {
                                itemMeta.getPersistentDataContainer().set(AccessoriesMain.getInstance().resistance, PersistentDataType.BOOLEAN, true);
                                acsItem.setItemMeta(itemMeta);
                                requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                            }
                            else {
                                requester.sendMessage(Component.text("Недостаточно ", NamedTextColor.GRAY).append(Component.text("Ⓟ", NamedTextColor.GOLD)));
                                requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            }
                        }
                    }
                }
            }
        }
        update();
    }

    @Override
    public void close(InventoryCloseEvent event) {
        ItemStack sharpItem = this.inventory.getItem(11);
        ItemStack acsItem = this.inventory.getItem(15);
        if (sharpItem != null) requester.getInventory().addItem(sharpItem);
        if (acsItem != null) requester.getInventory().addItem(acsItem);
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player){
        SharpGUI sharpGUI = new SharpGUI(player);
        Bukkit.getServer().getScheduler().runTaskLater(AccessoriesMain.getInstance(), sharpGUI::open, 1);
    }

    public void update() {
        ItemStack sharpItem = this.inventory.getItem(11);
        ItemStack acsItem = this.inventory.getItem(15);
        AccessoriesMain.getInstance().updateAcsItem(acsItem);
        ItemStack nullItem = Utils.getItem(Component.text(" "), Material.GRAY_STAINED_GLASS_PANE);

        AccessoryType accessoryType = AccessoriesMain.getInstance().getAccessoryTypeByItem(acsItem);

        if (accessoryType != null) {
            ItemStack resistanceItem = Utils.getItem(Component.text("Устойчивость аксессуара", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE), Material.SHULKER_SHELL);
            if (hasResistance(acsItem)) {
                Utils.addLore(resistanceItem, true, Component.text("На выбранном аксессуаре есть устойчивость", NamedTextColor.GRAY));
                AccessoryObject accessoryObjectByItem = AccessoriesMain.getInstance().getAccessoryObjectByItem(acsItem);
                if (accessoryObjectByItem != null) {
                    int durability = accessoryObjectByItem.getDurability(acsItem);
                    Utils.addLore(resistanceItem, false,
                            Component.text("Прочность: ", NamedTextColor.GRAY)
                                    .append(Component.text(durability, NamedTextColor.YELLOW))
                                    .append(Component.text("/", NamedTextColor.GRAY))
                                    .append(Component.text("100", NamedTextColor.GREEN)),
                            Component.text("Стоимость починки за одну единицу: ", NamedTextColor.GRAY)
                                    .append(Component.text("1.2 Coins", NamedTextColor.GOLD)));
                    if (durability != 100) {
                        Utils.addLore(resistanceItem, false, Component.text("Нажатие - ", NamedTextColor.GRAY)
                                        .append(Component.text("+1", NamedTextColor.GREEN))
                                        .append(Component.text(" за ", NamedTextColor.GRAY))
                                        .append(Component.text("1.2 Coins", NamedTextColor.GOLD)),
                                Component.text("Нажатие+shift - ", NamedTextColor.GRAY)
                                        .append(Component.text("+" + (100 - durability), NamedTextColor.GREEN))
                                        .append(Component.text(" за ", NamedTextColor.GRAY))
                                        .append(Component.text((1.2 * (100 - durability) + "Coins"), NamedTextColor.GOLD)));
                    }
                }
            }
            else {
                Utils.addLore(resistanceItem, true, Component.text("Стоимость: ", NamedTextColor.GRAY)
                        .append(Component.text("19Ⓟ", NamedTextColor.GOLD)));
            }
            this.inventory.setItem(18, resistanceItem);
        }
        else {
            this.inventory.setItem(18, nullItem);
        }
        if (isSharpStone(sharpItem) && accessoryType != null && haveNextLevel(accessoryType, acsItem)) {
            this.inventory.setItem(12, lastAction.itemStack);
            this.inventory.setItem(13, lastAction.itemStack);
            this.inventory.setItem(14, lastAction.itemStack);
            level = accessoryType.getLevel(acsItem);
            this.inventory.setItem(22, getReadyItem(level));
            canSharp = true;
        }
        else {
            canSharp = false;
            this.inventory.setItem(22, nullItem);
            this.inventory.setItem(12, nullItem);
            this.inventory.setItem(13, nullItem);
            this.inventory.setItem(14, nullItem);
        }
    }

    public ItemStack getReadyItem(int currentLevel) {
        ItemStack item = Utils.getItem(Component.text("Попытка заточки", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE), Material.LIME_STAINED_GLASS);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        LevelUpChance nextLevel = Arrays.stream(LevelUpChance.values()).filter(levelUpChance -> levelUpChance.getLevel() == currentLevel).findFirst().orElse(null);

        lore.add(Component.text("Текущий уровень: ", NamedTextColor.GRAY)
                .append(Component.text(currentLevel, NamedTextColor.YELLOW))
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        if (nextLevel != null) {
            lore.add(Component.text("Шанс удачной заточки: ", NamedTextColor.GRAY)
                    .append(Component.text(nextLevel.getChance() + "%", NamedTextColor.YELLOW))
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        }
        lore.add(Component.text("Стоимость: ", NamedTextColor.GRAY)
                .append(Component.text("3 Coins", NamedTextColor.GOLD)
                        .append(Component.text(" + ", NamedTextColor.GRAY)))
                        .append(Component.text(" точильный камень", NamedTextColor.YELLOW))
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public void addAcsLevel(ItemStack itemStack) {
        AccessoryType accessoryType = AccessoriesMain.getInstance().getAccessoryTypeByItem(itemStack);
        if (accessoryType == null) return;
        accessoryType.addLevel(itemStack);
    }

    public boolean isSharpStone(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() == Material.AIR) return false;
        if (!itemStack.hasItemMeta()) return false;
        if (itemStack.getItemMeta() == null) return false;
        return itemStack.getItemMeta().getPersistentDataContainer().has(AccessoriesMain.getInstance().sharpStoneKey);
    }

    public boolean isAccessoryItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta ==  null) return false;
        if (itemMeta.getPersistentDataContainer().isEmpty()) return false;
        return Arrays.stream(AccessoryType.values()).anyMatch(accessoryType -> itemMeta.getPersistentDataContainer().has(accessoryType.getKey()));
    }

    public void swapItems(ItemStack itemStack1, ItemStack itemStack2, int slot1, int slot2, Inventory inventory1, Inventory inventory2) {
        inventory1.setItem(slot1, itemStack2);
        inventory2.setItem(slot2, itemStack1);
    }

    public boolean haveNextLevel(AccessoryType accessoryType, ItemStack itemStack) {
        return accessoryType.getLevel(itemStack) < 10;
    }

    public boolean hasResistance(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        if (pdc.isEmpty()) return false;
        if (!pdc.has(AccessoriesMain.getInstance().resistance)) return false;
        return pdc.getOrDefault(AccessoriesMain.getInstance().resistance, PersistentDataType.BOOLEAN, false);
    }
}
