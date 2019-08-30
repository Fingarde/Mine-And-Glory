package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.utils.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class CustomBlockListener implements Listener
{

    @EventHandler
    public void onPhysic(BlockPhysicsEvent event)
    {
        if(event.getBlock().getType() != Material.RED_MUSHROOM_BLOCK && event.getBlock().getType() != Material.BROWN_MUSHROOM_BLOCK && event.getBlock().getType() != Material.MUSHROOM_STEM) return;

        event.setCancelled(true);
    }

    /*@EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        Block blockPlaced = event.getBlock();

        for(int x = -1; x < 2; x++)
        {
            for(int y = -1; y < 2; y++)
            {
                for(int z = -1; z < 2; z++)
                {
                    if(blockPlaced.getRelative(x, y ,z).getType() != Material.RED_MUSHROOM_BLOCK && event.getBlock().getType() != Material.BROWN_MUSHROOM_BLOCK && event.getBlock().getType() != Material.MUSHROOM_STEM) continue;

                    Block block = blockPlaced.getRelative(x, y ,z);

                    if(!block.hasMetadata("block_id")) return;

                    CustomBlock.Blocks type = CustomBlock.Blocks.valueOf(block.getMetadata("block_id").get(0).asString().toUpperCase());

                    CustomBlock.set(block, type, "");
                }
            }
        }
    }*/

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        if(event.getBlock().getType() != Material.RED_MUSHROOM_BLOCK && event.getBlock().getType() != Material.BROWN_MUSHROOM_BLOCK && event.getBlock().getType() != Material.MUSHROOM_STEM) return;

        ArrayList<Block> blocksToCheck = new ArrayList<>();

        blocksToCheck.add(event.getBlock().getRelative(BlockFace.UP));
        blocksToCheck.add(event.getBlock().getRelative(BlockFace.DOWN));
        blocksToCheck.add(event.getBlock().getRelative(BlockFace.EAST));
        blocksToCheck.add(event.getBlock().getRelative(BlockFace.WEST));
        blocksToCheck.add(event.getBlock().getRelative(BlockFace.NORTH));
        blocksToCheck.add(event.getBlock().getRelative(BlockFace.SOUTH));

        for(Block block : blocksToCheck)
        {
            if (block.getType() != Material.RED_MUSHROOM_BLOCK && block.getType() != Material.BROWN_MUSHROOM_BLOCK && block.getType() != Material.MUSHROOM_STEM)
                continue;
        }



    }



    /*@EventHandler
    public void onPlace(BlockBreakEvent event)
    {
        Block blockPlaced = event.getBlock();

        for(int x = -1; x < 2; x++)
        {
            for(int y = -1; y < 2; y++)
            {
                for(int z = -1; z < 2; z++)
                {
                    if(blockPlaced.getRelative(x, y ,z).getType() != Material.RED_MUSHROOM_BLOCK && event.getBlock().getType() != Material.BROWN_MUSHROOM_BLOCK && event.getBlock().getType() != Material.MUSHROOM_STEM) continue;

                    Block block = blockPlaced.getRelative(x, y ,z);

                    if(!block.hasMetadata("block_id")) return;

                    CustomBlock.Blocks type = CustomBlock.Blocks.valueOf(block.getMetadata("block_id").get(0).asString().toUpperCase());

                    CustomBlock.set(block, type, "");
                }
            }
        }
    }*/
}
