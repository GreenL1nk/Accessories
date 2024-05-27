package com.greenlink.accessories;

import com.greenlink.accessories.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum LastAction {

    NONE(Utils.getItem(Component.text(" "), Material.YELLOW_STAINED_GLASS_PANE)),
    FAIL(Utils.getItem(Component.text("Последняя попытка была ", NamedTextColor.GRAY)
            .append(Component.text("неудачной", NamedTextColor.RED))
            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE), Material.RED_STAINED_GLASS_PANE)),
    SUCCESS(Utils.getItem(Component.text("Последняя попытка была ", NamedTextColor.GRAY)
            .append(Component.text("удачной", NamedTextColor.GREEN))
            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE), Material.GREEN_STAINED_GLASS_PANE));

    final ItemStack itemStack;

    LastAction(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
