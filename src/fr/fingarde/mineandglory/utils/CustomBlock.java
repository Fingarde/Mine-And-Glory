package fr.fingarde.mineandglory.utils;


import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CustomBlock
{

    public enum Blocks
    {
       GRAVESTONE(Material.RED_MUSHROOM_BLOCK,false, false, false, false, false, false);

        private final Material type;
        private final boolean down;
        private final boolean east;
        private final boolean north;
        private final boolean south;
        private final boolean up;
        private final boolean west;;

        Blocks(Material type, boolean downn, boolean east, boolean north, boolean south, boolean up, boolean west)
        {
            this.type = type;
            this.down = downn;
            this.east = east;
            this.north = north;
            this.south = south;
            this.up = up;
            this.west = west;

        }
    }

    public static void set(Block block, Blocks type, String data)
    {
            block.setType(type.type);

            MultipleFacing multiFacing = (MultipleFacing) block.getBlockData();

            multiFacing.setFace(BlockFace.DOWN, type.down);
            multiFacing.setFace(BlockFace.EAST, type.east);
            multiFacing.setFace(BlockFace.NORTH, type.north);
            multiFacing.setFace(BlockFace.SOUTH, type.south);
            multiFacing.setFace(BlockFace.UP, type.up);
            multiFacing.setFace(BlockFace.WEST,type.west);

            block.setBlockData(multiFacing);

        try {
            Connection connection = MineAndGlory.getHikari().getConnection();
            Statement statement = connection.createStatement();

            data += " " + "block_id:" + type.name().toLowerCase();

            statement.executeUpdate("INSERT INTO Blocks (LOCATION, DATA) VALUES ('" + LocationSerializer.serialize(block.getLocation()) + "', '" + data + "')");

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
