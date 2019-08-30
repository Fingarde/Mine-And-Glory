package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();

        event.setCancelled(true);
        String message = event.getMessage().replaceAll("&", "§");
        for(Player players : Bukkit.getOnlinePlayers())
        {
            if(message.toLowerCase().contains(players.getName().toLowerCase()))
            {
                message = "§r" + message;

                String color = ChatColor.getLastColors(message.substring(0, message.toLowerCase().lastIndexOf(players.getName().toLowerCase())));
                int start = message.toLowerCase().indexOf(players.getName().toLowerCase());

                message = message.replaceAll(message.substring(start, start + players.getName().length()), ChatColor.GREEN  + players.getName() + color);

                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f , 2);
            }
        }



        for(Player players : Bukkit.getOnlinePlayers()) players.sendMessage(ChatColor.WHITE + player.getDisplayName() + ChatColor.GRAY + " » " + ChatColor.WHITE + message);

        String messageForConsole = player.getName() + ": " + message;

        for(int i = 0; i < messageForConsole.length(); i++)
        {
            if (messageForConsole.charAt(i) == '§')
            {
                messageForConsole = messageForConsole.replaceAll(messageForConsole.substring(i, i + 2), "");
            }
        }

        MineAndGlory.getConsole().sendMessage(messageForConsole);


    }
}
