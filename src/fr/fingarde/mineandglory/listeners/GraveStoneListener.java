package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.utils.CustomBlock;
import fr.fingarde.mineandglory.utils.GraveStone;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;
import java.util.UUID;

public class GraveStoneListener implements Listener
{

   // @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {

        Player player = event.getEntity();
        GraveStone graveStone = new GraveStone(UUID.randomUUID());

        Date date = new Date();
        graveStone.setPlayerUUID(player.getUniqueId());
        graveStone.setTimestamp(date.getTime());
        graveStone.setItems(player.getInventory().getContents());
        graveStone.setLocation(player.getLocation());

        graveStone.create();

        event.getDrops().clear();

        Block graveStoneBlock = player.getLocation().getBlock();

        CustomBlock.set(graveStoneBlock, CustomBlock.Blocks.GRAVESTONE, "gravestone_id:" + graveStone.getId().toString());

        player.sendMessage("§bVous etes mort aux coordonnées §eX=" + player.getLocation().getBlockX() + " Y=" + player.getLocation().getBlockY() + " Z=" + player.getLocation().getBlockZ());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getBlock().hasMetadata("gravestone_id")) return;

        Bukkit.broadcastMessage(UUID.fromString(event.getBlock().getMetadata("gravestone_id").get(0).asString()).toString());

        GraveStone graveStone = new GraveStone(UUID.fromString(event.getBlock().getMetadata("gravestone_id").get(0).asString()));

        graveStone.load();

        for(int i = 0; i < graveStone.getItems().length; i++)
        {
            if(graveStone.getItems()[i] == null) continue;
            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), graveStone.getItems()[i]);
        }
    }
}
