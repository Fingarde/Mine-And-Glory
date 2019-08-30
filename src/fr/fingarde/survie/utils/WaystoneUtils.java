package fr.fingarde.survie.utils;

import fr.fingarde.survie.Main;
import fr.fingarde.survie.objects.newItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class WaystoneUtils
{
    private static String prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "Waystone" + ChatColor.GRAY + "] " + ChatColor.WHITE;

    public static void openWaystone(String waystoneID, Player player, int page)
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Players WHERE Player_Name = '" + player.getName() + "'");
            ResultSet result = statement.executeQuery();

            if (result.next())
            {
                Inventory waystoneInv = Bukkit.createInventory(player, 54, ChatColor.GOLD + "Waystone");

                String waystoneStringList = result.getString("Waystone");
                String[] waystonesList = waystoneStringList.split(" ");


                String newWaystoneList =  waystoneStringList + " " + waystoneID;
                if(waystoneStringList.length() == 0) newWaystoneList = newWaystoneList.substring(1);

                if(!Arrays.asList(waystonesList).contains(waystoneID))
                {
                    PreparedStatement statement3 = connection.prepareStatement("UPDATE players SET Waystone = '" + newWaystoneList + "' WHERE Player_Name = '" + player.getName() + "'");
                    statement3.executeUpdate();

                    waystonesList = newWaystoneList.split(" ");
                }

                PreparedStatement statement4 = connection.prepareStatement("SELECT * FROM Waystones WHERE ID = '" + waystoneID + "'");
                ResultSet result4 = statement4.executeQuery();

                if(result4.next())
                {
                    if(player.getName().equalsIgnoreCase(result4.getString("Owner_Name")))
                    {

                    }
                }
                else
                {
                    player.sendMessage(prefix + ChatColor.RED + "Il y a une erreur sur la waystone veuillez contacter une admin: " + ChatColor.AQUA + "WaystoneUtils:4");
                    return;
                }

                if(waystonesList.length < 36 * page)
                {
                    player.sendMessage(prefix + ChatColor.RED + "Il y a une erreur sur la waystone veuillez contacter une admin: " + ChatColor.AQUA + "WaystoneUtils:2");
                    return;
                }

                int numberToDraw = waystonesList.length - 36;

                if(numberToDraw > 36)
                {
                    numberToDraw = 36;

                    waystoneInv.setItem(53, newItems.getNext());
                    waystoneInv.setItem(50, newItems.getPrevious());

                }

                for(int i = 0 + (36 * page) ; i < numberToDraw; i++)
                {
                    PreparedStatement statement5 = connection.prepareStatement("SELECT * FROM Waystones WHERE ID = '" + waystonesList[i] + "'");
                    ResultSet result5 = statement5.executeQuery();

                    if (result5.next())
                    {
                        String ownerName = result5.getString("Owner_Name");
                        String waystoneName = result5.getString("Name");

                        String location = result5.getString("Location");

                        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

                        SkullMeta meta = (SkullMeta) item.getItemMeta();

                        meta.setDisplayName(ChatColor.GOLD + waystoneName);

                        List<String> lore = new ArrayList<>();

                        lore.add("Owner: " + ownerName);
                        lore.add("Location: " + location);

                        meta.setLore(lore);
                        meta.setOwningPlayer(Bukkit.getPlayer(ownerName));

                        item.setItemMeta(meta);
                        waystoneInv.addItem(item);

                    }
                    else
                    {
                        player.sendMessage(prefix + ChatColor.RED + "Il y a une erreur sur la waystone veuillez contacter une admin: " + ChatColor.AQUA + "WaystoneUtils:3" );
                        return;
                    }
                }

                player.openInventory(waystoneInv);
            }
            else
            {
                player.sendMessage(prefix + ChatColor.RED + "Il y a une erreur sur la waystone veuillez contacter une admin: " + ChatColor.AQUA + "WaystoneUtils:4");
                return;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void createWaystone(PlayerInteractEvent event)
    {
        try
        {
            Block bottom = null;
            Location loc = event.getClickedBlock().getLocation();

            switch (event.getBlockFace())
            {
                case UP:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
                    break;
                case DOWN:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 2, loc.getBlockZ());
                    break;

                case EAST:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ());
                    break;
                case WEST:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ());
                    break;

                case NORTH:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() - 1);
                    break;
                case SOUTH:
                    bottom = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + 1);
                    break;
            }

            if(bottom == null) return;
            if(bottom.getType() != Material.AIR && bottom.getType() != Material.CAVE_AIR) return;

            loc = bottom.getLocation();
            Block top = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());

            if(top.getType() != Material.AIR && top.getType() != Material.CAVE_AIR) return;
            if(loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() == Material.AIR || loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() == Material.CAVE_AIR) return;

            String id = UUID.randomUUID().toString();

            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO waystones (Owner_Name, Name, ID, Location) VALUES ('" + event.getPlayer().getName() +"','Waystone','" + id + "', '" + Utils.serializeLocation(new Location(loc.getWorld(), loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ())) + "');");
            statement.executeUpdate();

            top.setType(Material.BARRIER);
            top.setMetadata("survie:type", new FixedMetadataValue(Main.getInstance(), "waystone"));
            top.setMetadata("survie:waystoneID", new FixedMetadataValue(Main.getInstance(), id));

            bottom.setType(Material.MUSHROOM_STEM);
            bottom.setMetadata("survie:type", new FixedMetadataValue(Main.getInstance(), "waystone"));
            bottom.setMetadata("survie:waystoneID", new FixedMetadataValue(Main.getInstance(), id));

            MultipleFacing multiFacing = (MultipleFacing) bottom.getBlockData();

            multiFacing.setFace(BlockFace.UP, false);
            multiFacing.setFace(BlockFace.DOWN, false);
            multiFacing.setFace(BlockFace.EAST, false);
            multiFacing.setFace(BlockFace.WEST, false);
            multiFacing.setFace(BlockFace.NORTH, false);
            multiFacing.setFace(BlockFace.SOUTH, false);

            bottom.setBlockData(multiFacing);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
