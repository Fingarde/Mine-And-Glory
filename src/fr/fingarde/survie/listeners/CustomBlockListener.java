package fr.fingarde.survie.listeners;

import fr.fingarde.survie.objects.newBlocks;
import fr.fingarde.survie.objects.newItems;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockListener extends Listeners
{
    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.getItem() == null) return;
        if(event.getItem().getType() != Material.IRON_HOE) return;
        if(event.getItem().getItemMeta() == null) return;
        if(event.getItem().getItemMeta().getLocalizedName() == null) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        event.setCancelled(true);

        String localName = event.getItem().getItemMeta().getLocalizedName().toLowerCase();

        if(localName.startsWith("survie:"))
        {
            newItems.items items = newItems.items.valueOf(localName.substring(7));

            if(items.type == newItems.type.BLOCK)
            {
                newBlocks.blocks newBlock = newBlocks.blocks.valueOf(items.data);

                if(newBlock == null) return;
                if(event.getClickedBlock() == null) return;

                Location loc = event.getClickedBlock().getLocation();
                Block block = null;

                switch (event.getBlockFace())
                {
                    case UP:
                        block = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
                        break;
                    case DOWN:
                        block = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ());
                        break;

                    case EAST:
                        block = loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ());
                        break;
                    case WEST:
                        block = loc.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ());
                        break;

                    case NORTH:
                        block = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() - 1);
                        break;
                    case SOUTH:
                        block = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + 1);
                        break;
                }

                loc = block.getLocation();

                loc.getWorld().playSound(loc, Sound.BLOCK_STONE_PLACE, 1 , 1);
                newBlocks.setNewBlock(block, newBlock);

                loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ()).getState().update();
                if(event.getPlayer().getGameMode() == GameMode.SURVIVAL) event.getItem().setAmount(event.getItem().getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void onUpdate(BlockPhysicsEvent event)
    {
        if(event.getSourceBlock().getType() == Material.RED_MUSHROOM_BLOCK || event.getSourceBlock().getType() == Material.BROWN_MUSHROOM_BLOCK ||event.getSourceBlock().getType() == Material.MUSHROOM_STEM) event.setCancelled(true);
    }
}
