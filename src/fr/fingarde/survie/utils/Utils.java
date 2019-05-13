package fr.fingarde.survie.utils;

import fr.fingarde.survie.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Utils
{
    public static String serializeLocation(Location location)
    {
        String value = location.getWorld().getName() + " " + (location.getBlockX() + 0.5) + " " + location.getBlockY() + " " + (location.getBlockZ() + 0.5);
        return value;
    }

    public static Location deserializeLocation(String string)
    {
        String[] values = string.split(" ");

        if(values.length != 4) return null;

        Location loc = new Location(Bukkit.getWorld(values[0]), Double.valueOf(values[1]), Double.valueOf(values[2]), Double.valueOf(values[3]));
        return loc;
    }

    public static UUID saveDeathInventory(PlayerInventory inventory)
    {
        try {
            UUID id = UUID.randomUUID();

            YamlConfiguration config = new YamlConfiguration();
            config.set("content", inventory.getContents());

            config.save(new File(Main.getInstance().getDataFolder(), "deaths\\" + id));

            return id;
        }
        catch (IOException e)
        {
            Main.getConsole().sendMessage(ChatColor.RED + "[ERROR] Une erreur est survenue lors de la creation d'un death inventory");
            e.printStackTrace();
        }

        return null;
    }

    public static void dropDeathInventory(Location loc, UUID id)
    {
        try
        {
            YamlConfiguration config = new YamlConfiguration();
            config.load(new File(Main.getInstance().getDataFolder(), "deaths\\" + id));

            ItemStack[] content = ((List<ItemStack>) config.get("content")).toArray(new ItemStack[0]);

            for(int i = 0 ; i < content.length ; i++)
            {
                if(content[i] != null) loc.getWorld().dropItemNaturally(loc, content[i]).setPickupDelay(0);
            }
        }
        catch (IOException | InvalidConfigurationException e)
        {
            Main.getConsole().sendMessage(ChatColor.RED + "[ERROR] Une erreur est survenue lors de la restoration d'un death inventory");
            e.printStackTrace();
        }
    }

    public static void sendErrorPermission(CommandSender sender)
    {
        sender.sendMessage(ChatColor.RED + "[ERROR] Vous n\'avez pas la permission d\'effectuer ceci");
    }

    public static void checkChunk(Chunk c)
    {
        c.getBlock(0, 0, 0).setType(Material.BARRIER);

        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 1; y < 132; y++)
                {
                    Block b = c.getBlock(x, y, z);

                    if((isBlockToCut(b.getType())) || (b.getType() == Material.BEDROCK && y > 1) )
                    {
                        Main.blocks.add(b);
                    }
                }
            }
        }
    }

    public static boolean isBlockToCut(Material material)
    {
        switch (material)
        {
            case DIAMOND_ORE:
            case COAL_ORE:
            case GOLD_ORE:
            case EMERALD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
                return true;
        }

        return false;
    }
}
