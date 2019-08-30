package fr.fingarde.survie.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

public class newBlocks
{
    public enum blocks
    {
        // ORES
        emerald_ore(new boolean[] {false, false, true, true, false, false}),
        amethyst_ore(new boolean[] {false, false, true, true, false, true}),
        ruby_ore (new boolean[] {false, false, true, true, true, false}),
        saphir_ore (new boolean[] {false, false, true, true, true, true}),
        onyx_ore (new boolean[] {false, true, false, false, false, true}),
        meteorite_ore (new boolean[] {false, true, false, false , true, true}),

        // BLOCKS
        emeral_block(new boolean[] {false, true, false, true , false, true}),
        amethyst_block(new boolean[] {false, true, false, true , true, true}),
        ruby_block (new boolean[] {false, true, true, false , false, true}),
        saphir_block (new boolean[] {false, true, true, false, true, true}),
        onyx_block (new boolean[] {false, true, true, true, false, false}),
        meteorite_block (new boolean[] {false, true, true, true, false, true}),
        breakable_bedrock (new boolean[] {true, false, false, false, false, false}),
        
        
        // OTHERS
        lucky_block (new boolean[] {false, true, true, true, true, false}),
        meteorite_stone (new boolean[] {false, true, true, true, true, true});


        private boolean[] states;

        blocks(boolean[] states){
            this.states = states;
        }
    }

    public static void setNewBlock(Block block, blocks customBlock)
    {
        block.setType(Material.RED_MUSHROOM_BLOCK);

        boolean[] values = customBlock.states;

        MultipleFacing multiFacing = (MultipleFacing) block.getBlockData();

        multiFacing.setFace(BlockFace.DOWN, values[0]);
        multiFacing.setFace(BlockFace.EAST, values[1]);
        multiFacing.setFace(BlockFace.NORTH, values[2]);
        multiFacing.setFace(BlockFace.SOUTH, values[3]);
        multiFacing.setFace(BlockFace.UP, values[4]);
        multiFacing.setFace(BlockFace.WEST, values[5]);

        block.setBlockData(multiFacing);
    }
}
