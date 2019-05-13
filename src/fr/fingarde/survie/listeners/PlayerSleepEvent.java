package fr.fingarde.survie.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerSleepEvent implements Listener
{
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event)
    {
        Player player = event.getPlayer();
        int nbPlayer = player.getWorld().getPlayers().size();
        int nbSleeping = 1;

        for(Player arrayPlayer : player.getWorld().getPlayers())
        {
            if(arrayPlayer.isSleeping()) nbSleeping++;
        }

        int percent = ((nbSleeping / nbPlayer) * 100);

        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " est en train de dormir (" + nbSleeping + "/" + nbPlayer + " - " + ChatColor.YELLOW + percent + "%" + ChatColor.RED + ")");

        if(percent >= 50)
        {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Il y a assez de joueurs qui dorment pour passer la nuit " + ChatColor.RED + "[Annuler]");
        }
    }
}
