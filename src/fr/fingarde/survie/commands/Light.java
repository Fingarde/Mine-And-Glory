package fr.fingarde.survie.commands;

import com.github.hexocraftapi.lights.Lights;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Light implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        Lights.createLight(player.getLocation(), 15);
        return false;
    }
}
