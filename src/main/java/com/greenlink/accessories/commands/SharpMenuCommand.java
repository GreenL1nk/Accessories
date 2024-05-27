package com.greenlink.accessories.commands;

import com.greenlink.accessories.SharpGUI;
import com.greenlink.accessories.utils.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SharpMenuCommand extends AbstractCommand {
    @Override
    protected void onPlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if (!player.isOp()) return;
        if (args.length == 1) {
            Player toOpen = Bukkit.getPlayer(args[0]);
            if (toOpen == null) return;
            SharpGUI.display(toOpen);
        }
    }

    @Override
    protected void onConsoleCommand(@NotNull CommandSender commandSender, @NotNull String[] args) {
        if (args.length == 1) {
            Player toOpen = Bukkit.getPlayer(args[0]);
            if (toOpen == null) return;
            SharpGUI.display(toOpen);
        }
    }

    @Override
    protected List<String> onPlayerTab(@NotNull Player player, @NotNull String[] args) {
        return null;
    }

    @Override
    protected List<String> onConsoleTab(@NotNull CommandSender commandSender, @NotNull String[] args) {
        return null;
    }
}
