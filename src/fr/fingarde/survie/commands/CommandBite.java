package fr.fingarde.survie.commands;

import fr.fingarde.survie.objects.newItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBite implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //((Player)commandSender).getInventory().addItem(newItems.getBackpack());
        //((Player)commandSender).getInventory().addItem(newItems.getMithrilFAS());
        //((Player)commandSender).getInventory().addItem(newItems.getGoldBackpack());
        //((Player)commandSender).getInventory().addItem(newItems.getEnderBackpack());


        //((Player)commandSender).getInventory().addItem(newItems.getWaystone());

        ((Player)commandSender).getInventory().addItem(newItems.getItem(newItems.items.valueOf(strings[0])));


        /*Block b = ((Player) commandSender).getLocation().getWorld().getBlockAt( ((Player) commandSender).getLocation().getBlockX(),  ((Player) commandSender).getLocation().getBlockY(),  ((Player) commandSender).getLocation().getBlockZ());


        b.setType(Material.BARRIER);
        b.setMetadata("survie:type", new FixedMetadataValue(Main.getInstance(), "waystone"));
        b.setMetadata("survie:waystoneID", new FixedMetadataValue(Main.getInstance(), "1"));*/


        return false;
    }
}
