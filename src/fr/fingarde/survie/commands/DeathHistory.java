package fr.fingarde.survie.commands;

import fr.fingarde.survie.objects.JPlayer;
import fr.fingarde.survie.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathHistory implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length == 0)
        {

        }
        else
        {
            if(!sender.hasPermission("admin.deathhistory"))
            {
                Utils.sendErrorPermission(sender);
                return false;
            }

            if(Bukkit.getPlayer(args[0]) == null)
            {
                sender.sendMessage(ChatColor.RED + "Ce joueur n\'existe pas");
                return false;
            }

            Player player = Bukkit.getPlayer(args[0]);
            JPlayer jPlayer = new JPlayer(player);

            String[][] deaths = jPlayer.get10LastDeaths();

            sender.sendMessage(ChatColor.GOLD + "-----" + ChatColor.WHITE + " Morts de " + player.getName() + ChatColor.GOLD + " -----" );

            for(int i = deaths.length - 1; i > -1; i--)
            {
                TextComponent content = new TextComponent();
                TextComponent start = new TextComponent(ChatColor.GRAY + "» ");
                TextComponent date = new TextComponent(ChatColor.WHITE + deaths[i][2] + ", ");
                TextComponent cause = new TextComponent(ChatColor.YELLOW + deaths[i][1]);
                Location loc = Utils.deserializeLocation(deaths[i][3]);

                TextComponent locationText[] = {new TextComponent("X: " + loc.getBlockX()), new TextComponent("Z: " + loc.getBlockZ())};

                cause.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, locationText));

                TextComponent collected;
                TextComponent end = new TextComponent(" ");

                if(deaths[i][0] == "1")
                {
                    collected = new TextComponent(ChatColor.GREEN + "Récupéré");
                }else
                {
                    collected = new TextComponent(ChatColor.RED + "Non récupéré");
                    end = new TextComponent("[Définir en récupéré]");
                }

                TextComponent stuff = new TextComponent(ChatColor.GREEN + ", " + "[Voir le stuff]");
                stuff.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "openDeathInv " + deaths[i][4]));

                content.addExtra(start);
                content.addExtra(date);
                content.addExtra(cause);
                content.addExtra(collected);
                content.addExtra(end);
                content.addExtra(stuff);

                sender.spigot().sendMessage(content);
            }
            sender.sendMessage(ChatColor.GOLD + "-----" + ChatColor.WHITE + " Morts de " + player.getName() + ChatColor.GOLD + " -----" );
        }
        return false;
    }
}
