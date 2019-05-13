package fr.fingarde.survie.listeners;

import fr.fingarde.survie.Main;
import fr.fingarde.survie.utils.Utils;
import fr.fingarde.survie.objects.JPlayer;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Listeners implements Listener
{
    private static String msgAlfheim = ChatColor.AQUA + "" + ChatColor.BOLD + "〜 " + ChatColor.RESET + "" + ChatColor.AQUA + "Un portail vers alfheim a été ouvert " + ChatColor.BOLD + "〜";

    @EventHandler
    public void onPopulate(PlayerMoveEvent event)
    {
        if(event.getFrom().getChunk() == event.getTo().getChunk()) return;

        Chunk c = event.getTo().getChunk();

        int radius = Main.getInstance().getConfig().getInt("chunkCheckingRadius");

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if(c.getWorld().getChunkAt(c.getX() + x, c.getZ() + z).getBlock(0, 0, 0).getType() == Material.BARRIER) continue;

                Main.chunks.add(c.getWorld().getChunkAt(c.getX() + x, c.getZ() + z));
            }
        }
    }


    @EventHandler
    public void breakBlock(BlockBreakEvent event)
    {
         String id = event.getBlock().getMetadata("death_id").get(0).asString();

         Utils.dropDeathInventory(event.getBlock().getLocation(), UUID.fromString(id));
    }

    @EventHandler
    public void onEnterPortal(PlayerPortalEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        if(event.getClickedBlock() != null)
        {
            Block block = event.getClickedBlock();
            if(block.getType().equals(Material.BARRIER))
            {
                List<MetadataValue> type = block.getMetadata("survie:type");
                if(type.size() == 0)
                {
                    if(type.get(0).asString().equalsIgnoreCase("waystone"))
                    {

                    }
                }
            }
        }

        if(event.getItem() == null) return;

        ItemStack item = event.getItem();

        if(!item.hasItemMeta()) return;
        if(item.getItemMeta().getLocalizedName() == null) return;


        if(item.getItemMeta().getLocalizedName().equalsIgnoreCase("mithril_flint_and_steel"))
        {
            if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
            if(event.getClickedBlock() == null) return;

            if(event.getClickedBlock().getType() != Main.getBlockPortalAlfheim()) return;

            World w = event.getClickedBlock().getWorld();
            Location l = event.getClickedBlock().getLocation();

            int x = (int) l.getX();     int y = (int) l.getY();     int z = (int) l.getZ();

            switch (event.getBlockFace())
            {
                case UP: y += 1; break;
                case DOWN: y -= 1; break;
                case EAST: x += 1; break;
                case WEST: x -= 1; break;
                case SOUTH: z += 1; break;
                case NORTH: z -= 1; break;
            }

            Block center;

            if(w.getBlockAt(x, y,z + 1).getType() == Main.getBlockPortalAlfheim())
            {
                if(w.getBlockAt(x, y - 1,z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y + 1, z);
                else if(w.getBlockAt(x, y + 1, z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y - 1, z);
                else center = w.getBlockAt(x, y , z);

                if(center.getType() == Material.NETHER_PORTAL) return;

                if(isValidPortal(Axis.Z, center))
                {
                    setPortalAxis(Axis.Z, center);
                    for(Player player : Bukkit.getOnlinePlayers()) player.playSound(center.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1 , 1);

                    Bukkit.broadcastMessage(msgAlfheim);
                }
            }
            else if(w.getBlockAt(x, y,z - 1).getType() == Main.getBlockPortalAlfheim())
            {
                if(w.getBlockAt(x, y - 1,z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y + 1, z + 1);
                else if(w.getBlockAt(x, y + 1, z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y - 1, z + 1);
                else center = w.getBlockAt(x, y , z + 1);

                if(center.getType() == Material.NETHER_PORTAL) return;

                if(isValidPortal(Axis.Z, center))
                {
                    setPortalAxis(Axis.Z, center);
                    for(Player player : Bukkit.getOnlinePlayers()) player.playSound(center.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1 , 1);

                    Bukkit.broadcastMessage(msgAlfheim);
                }
            }
            else if(w.getBlockAt(x - 1, y,z).getType() == Main.getBlockPortalAlfheim())
            {
                if(w.getBlockAt(x, y - 1,z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x + 1, y + 1, z);
                else if(w.getBlockAt(x, y + 1, z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x + 1, y - 1, z);
                else center = w.getBlockAt(x + 1, y , z);

                if(center.getType() == Material.NETHER_PORTAL) return;

                if(isValidPortal(Axis.X, center))
                {
                    setPortalAxis(Axis.X, center);
                    for(Player player : Bukkit.getOnlinePlayers()) player.playSound(center.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1 , 1);
                    
                    Bukkit.broadcastMessage(msgAlfheim);
                }
            }
            else if(w.getBlockAt(x + 1, y,z).getType() == Main.getBlockPortalAlfheim())
            {
                if(w.getBlockAt(x, y - 1,z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y + 1, z);
                else if(w.getBlockAt(x, y + 1, z).getType() == Main.getBlockPortalAlfheim()) center = w.getBlockAt(x, y - 1, z);
                else center = w.getBlockAt(x, y , z);

                if(center.getType() == Material.NETHER_PORTAL) return;

                if(isValidPortal(Axis.X, center))
                {
                    setPortalAxis(Axis.X, center);
                    for(Player player : Bukkit.getOnlinePlayers()) player.playSound(center.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1 , 1);

                    Bukkit.broadcastMessage(msgAlfheim);
                }
            }
        }

        if(item.getItemMeta().getLocalizedName().startsWith("backpack"))
        {
            if(!item.hasItemMeta()) return;
            if(item.getItemMeta().getLore() == null)
            {
                createBackpack(event.getItem());
                return;
            }


            if(item.getItemMeta().getLore().size() == 0)
            {
                createBackpack(item);
                return;
            }
            else
            {
                boolean hasID = false;

                for(String lore : item.getItemMeta().getLore())
                {
                    if(lore.startsWith("ID:")) hasID = true;
                }

                if(!hasID)
                {
                    createBackpack(item);
                    return;
                }else
                {
                    String id = null;

                    for(String lore : item.getItemMeta().getLore())
                    {
                        if(lore.startsWith("ID:")) id = lore.substring(4);
                    }

                    int size = 9;

                    if(event.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("backpack_gold"))
                    {
                        size = 18;
                    }

                    Inventory inv = loadBackpack(UUID.fromString(id),  size);
                    event.getPlayer().openInventory(inv);
                }
            }

        }

        if(item.getItemMeta().getLocalizedName().equalsIgnoreCase("ender_backpack"))
        {

            Inventory inv = Bukkit.createInventory(null, 27, "End Backpack");
            inv.setContents(event.getPlayer().getEnderChest().getContents());

            event.getPlayer().openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        if(event.getView().getTitle().equalsIgnoreCase("Backpack"))
        {
            String id = null;

            for(String lore : event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore())
            {
                if(lore.startsWith("ID:")) id = lore.substring(4);
            }

            saveBack(UUID.fromString(id), event.getInventory());
        }

        if(event.getView().getTitle().equalsIgnoreCase("End Backpack"))
        {
            event.getPlayer().getEnderChest().setContents(event.getInventory().getContents());
        }
    }

    @EventHandler
    public void onItemMove(InventoryClickEvent event)
    {
        if(!event.getView().getTitle().equalsIgnoreCase("Backpack")) return;

        if(event.getClick() == ClickType.NUMBER_KEY)
        {
            event.setCancelled(true);
            return;
        }

        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getLocalizedName() == null) return;
        if(!event.getCurrentItem().getItemMeta().getLocalizedName().startsWith("backpack")) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + ChatColor.YELLOW + player.getName());

        Main.getBar().addPlayer(player);

        try
        {
            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE Player_Name = '" + event.getPlayer().getName() + "'");
            ResultSet result = statement.executeQuery();

            if(!result.next())
            {
                Main.getConsole().sendMessage(ChatColor.YELLOW + "[Survie] Registering new player named " + event.getPlayer().getName());

                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO players (Player_Name, Player_Group, Player_Money, Player_Death, Player_Spawned_Dragon, Waystone) VALUES ('" + event.getPlayer().getName() +"','Aventurier','0', '0', '0', '');");
                statement1.executeUpdate();

                Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + " est nouveau sur le serveur !");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        JPlayer jPlayer = new JPlayer(player);
        jPlayer.get10LastDeaths();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.YELLOW + player.getName());

        Main.getBar().removePlayer(player);
    }

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
                String color = ChatColor.getLastColors(message.substring(0, message.toLowerCase().lastIndexOf(players.getName().toLowerCase())));
                int start = message.toLowerCase().indexOf(players.getName().toLowerCase());

                message = message.replaceAll(message.substring(start, start + players.getName().length()), ChatColor.GREEN + "@" + players.getName() + color);

                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f , 2);
            }
        }

        Bukkit.broadcastMessage(ChatColor.WHITE + player.getName()+ ChatColor.GRAY + " » " + ChatColor.WHITE + message);


    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        EntityType type = event.getEntityType();

        switch (type) {
            case ENDERMAN:
            case SPIDER:
            case CAVE_SPIDER:
            case GUARDIAN:
            case BLAZE:
            case CREEPER:
            case ZOMBIE_VILLAGER:
            case ELDER_GUARDIAN:
            case HUSK:
            case SLIME:
            case ZOMBIE:
            case VEX:
            case STRAY:
            case WITCH:
            case GHAST:
            case DROWNED:
            case EVOKER:
            case WITHER:
            case SKELETON:
            case SHULKER:
            case ENDERMITE:
            case SILVERFISH:
            case ILLUSIONER:
            case VINDICATOR:
            case MAGMA_CUBE:
            case PIG_ZOMBIE:
            case WITHER_SKELETON:
                ((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() * 1.5);
                break;


            case ENDER_DRAGON:
                FileConfiguration config = Main.getInstance().getConfig();
                config.options().copyDefaults(true);

                int nbEnderdragon = config.getInt("enderdragon");
                nbEnderdragon++;

                config.set("enderdragon",nbEnderdragon);

                Main.getInstance().saveConfig();

                double health = ((EnderDragon) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * nbEnderdragon;

                ((EnderDragon) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

                Bukkit.broadcastMessage("L'ender dragon a spawn avec " + ((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "hp");


                break;
            case PHANTOM:
                ((Phantom) event.getEntity()).setSize((int) (Math.random() * 80));
                break;
        }
    }

    public void saveBack(UUID id, Inventory inv)
    {
        try
        {
            File f = new File(Main.getInstance().getDataFolder(), id.toString() + ".yml");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            c.set("content", inv.getContents());
            c.save(f);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public Inventory loadBackpack(UUID id, int size)
    {
        File f = new File(Main.getInstance().getDataFolder().getAbsolutePath(), id.toString() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("content")).toArray(new ItemStack[0]);

        Inventory inv = Bukkit.createInventory(null, size, "Backpack");
        inv.setContents(content);

        return inv;
    }

    private void createBackpack(ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        UUID  id = UUID.randomUUID();
        saveBack(id, Bukkit.createInventory(null, 9, "Backpack"));

        lore.add("ID: " + id);

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    public boolean isValidPortal(Axis axis, Block center)
    {
        World w = center.getWorld();
        Location l = center.getLocation();

        int x = (int) l.getX();
        int y = (int) l.getY();
        int z = (int) l.getZ();

        if(axis == Axis.Z)
        {
            if(w.getBlockAt(x, y, z + 1).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y + 1, z + 1).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y - 1, z + 1).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x, y + 2, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y - 2, z).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x, y + 2, z - 1).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y - 2, z - 1).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x, y, z - 2).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y + 1, z - 2).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y - 1, z - 2).getType() != Main.getBlockPortalAlfheim()) return false;

            return true;
        }

        if(axis == Axis.X)
        {
            if(w.getBlockAt(x + 1, y, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x + 1, y + 1, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x + 1, y - 1, z).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x, y + 2, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x, y - 2, z).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x - 1, y + 2, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x - 1, y - 2, z).getType() != Main.getBlockPortalAlfheim()) return false;

            if(w.getBlockAt(x - 2, y, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x - 2, y + 1, z).getType() != Main.getBlockPortalAlfheim()) return false;
            if(w.getBlockAt(x - 2, y - 1, z).getType() != Main.getBlockPortalAlfheim()) return false;

            return true;
        }

        return false;
    }

    public void setPortalAxis(Axis axis, Block center)
    {
        if(axis == Axis.Z)
        {
            for(int iy = -1; iy < 2 ; iy++) for(int iz = -1; iz < 1 ; iz++)
            {
                Block portalToPlace =  center.getWorld().getBlockAt(center.getX(), center.getY() + iy , center.getZ() + iz);
                portalToPlace.setType(Material.NETHER_PORTAL);

                Orientable data = (Orientable) portalToPlace.getBlockData();

                data.setAxis(Axis.Z);

                portalToPlace.setBlockData(data);
            }
        }

        if(axis == Axis.X)
        {
            for(int iy = -1; iy < 2 ; iy++) for(int ix = -1; ix < 1 ; ix++)
            {
                Block portalToPlace =  center.getWorld().getBlockAt(center.getX() + ix, center.getY() + iy , center.getZ());
                portalToPlace.setType(Material.NETHER_PORTAL);

                Orientable data = (Orientable) portalToPlace.getBlockData();

                data.setAxis(Axis.X);

                portalToPlace.setBlockData(data);
            }
        }
    }
}
