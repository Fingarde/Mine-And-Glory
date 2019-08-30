package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AFKRemoverListener implements Listener
{
    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if(MineAndGlory.afk.contains(player))
        {
            MineAndGlory.afk.remove(player);

            Bukkit.broadcastMessage(player.getName() + " n'est plus AFK");
        }

        if(MineAndGlory.afkCount.containsKey(player))
        {
            MineAndGlory.afkCount.remove(player);
        }
    }
}
