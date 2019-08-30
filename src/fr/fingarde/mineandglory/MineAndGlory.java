package fr.fingarde.mineandglory;

import com.zaxxer.hikari.HikariDataSource;
import fr.fingarde.mineandglory.listeners.*;
import fr.fingarde.mineandglory.utils.Backpack;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MineAndGlory extends JavaPlugin
{
    public static ArrayList<Backpack> backpacks;
    public static ArrayList<Backpack>  backpacksToCreate;


    private int TIMEMULTIPLIER = 3;

    private static MineAndGlory instance;
    private static HikariDataSource hikari;
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static HashMap<Player, HashMap<String, String>> players = new HashMap<>();
    public static ArrayList<Player> afk = new ArrayList<>();
    public static HashMap<Player, BlockFace> lastClickedFace = new HashMap<>();

    private double TPS;
    public static String clock;

    private long passedTime;
    private HashMap<Player, Location> lastLocation = new HashMap<>();
    public static HashMap<Player, Integer> afkCount = new HashMap<>();

    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();

        connectDB();
        createTables();

        checkAFK();

        updateGameTime();
        refreshTPS();
        sendPos();

        sheduleTablist();

        registerEvents();
        registerCrafts();

    }


    public void onDisable()
    {

    }


    // MySQL
    private void connectDB()
    {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", getConfig().getString("host"));
        hikari.addDataSourceProperty("port", getConfig().getInt("port"));
        hikari.addDataSourceProperty("databaseName", getConfig().getString("database"));
        hikari.addDataSourceProperty("user", getConfig().getString("user"));
        hikari.addDataSourceProperty("password", getConfig().getString("password"));

        hikari.addDataSourceProperty("allowPublicKeyRetrieval",true);
        hikari.addDataSourceProperty("verifyServerCertificate", false);
        hikari.addDataSourceProperty("useSSL", false);

        hikari.addDataSourceProperty("tcpKeepAlive", true);
        hikari.addDataSourceProperty("autoReconnect", true);
        hikari.addDataSourceProperty("connectTimeout", 300);

        hikari.setMaximumPoolSize(2147483647);
        hikari.setMinimumIdle(0);
        hikari.setIdleTimeout(300);
    }

    private void createTables()
    {
        try
        {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Players(\n" +
                "  UUID VARCHAR(36) NOT NULL,\n" +
                "  RANK VARCHAR(30) NOT NULL,\n" +
                "  MONEY BIGINT NOT NULL,\n" +
                "  GLORY BIGINT NOT NULL,\n" +
                "  WAYSTONES TEXT NOT NULL\n" +
                ")");

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Gravestones(\n" +
                        "  UUID VARCHAR(36) NOT NULL,\n" +
                        "  PLAYER_UUID VARCHAR(36) NOT NULL,\n" +
                        "  TIMESTAMP BIGINT NOT NULL,\n" +
                        "  LOCATION TEXT NOT NULL,\n" +
                        "  CONTENTS TEXT NOT NULL\n" +
                        ")");

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Blocks(\n" +
                        "  LOCATION TEXT NOT NULL,\n" +
                        "  DATA TEXT NOT NULL\n" +
                        ")");

            statement.close();
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[Mine And Glory] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[Mine And Glory] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    // Register Events
    private void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new ConnectionListener(),this);
        getServer().getPluginManager().registerEvents(new ChatListener(),this);
        getServer().getPluginManager().registerEvents(new AFKRemoverListener(),this);
        getServer().getPluginManager().registerEvents(new SleepListener(),this);
        getServer().getPluginManager().registerEvents(new BasicItemRemoverListener(),this);
        getServer().getPluginManager().registerEvents(new CustomItemListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getServer().getPluginManager().registerEvents(new CropListener(),this);
        getServer().getPluginManager().registerEvents(new VehicleListener(),this);
        getServer().getPluginManager().registerEvents(new BucketListener(),this);
        getServer().getPluginManager().registerEvents(new BackpackListener(),this);
        getServer().getPluginManager().registerEvents(new TimberListener(), this);
        getServer().getPluginManager().registerEvents(new RideListener(), this);
        getServer().getPluginManager().registerEvents(new GraveStoneListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
    }

    private void registerCrafts()
    {
        ItemStack diamondHammer = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta diamondHammerMeta = diamondHammer.getItemMeta();
        diamondHammerMeta.setDisplayName("§rDiamond Hammer");
        diamondHammerMeta.setLocalizedName("diamond_hammer");
        ArrayList<String> diamondHammerLore = new ArrayList<>();
        diamondHammerLore.add("");
        diamondHammerLore.add("§rDurability: §e" + "1651" + "§r/§e" + "1651");
        diamondHammerMeta.setCustomModelData(10001001);
        diamondHammerMeta.setLore(diamondHammerLore);
        diamondHammer.setItemMeta(diamondHammerMeta);
        ShapedRecipe diamondHammerRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "diamond_hammer"), diamondHammer);
        diamondHammerRecipe.shape("***","?#?"," % ");
        diamondHammerRecipe.setIngredient('%', Material.BLAZE_ROD);
        diamondHammerRecipe.setIngredient('?', Material.DIAMOND);
        diamondHammerRecipe.setIngredient('*', Material.DIAMOND_PICKAXE);
        diamondHammerRecipe.setIngredient('#', Material.DIAMOND_BLOCK);
        getServer().addRecipe(diamondHammerRecipe);

        ItemStack diamondExcavator = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta diamondExcavatorMeta = diamondExcavator.getItemMeta();
        diamondExcavatorMeta.setDisplayName("§rDiamond Excavator");
        diamondExcavatorMeta.setLocalizedName("diamond_excavator");
        ArrayList<String> diamondExcavatorLore = new ArrayList<>();
        diamondExcavatorLore.add("");
        diamondExcavatorLore.add("§rDurability: §e" + "1651" + "§r/§e" + "1651");
        diamondExcavatorMeta.setCustomModelData(10001011);
        diamondExcavatorMeta.setLore(diamondExcavatorLore);
        diamondExcavator.setItemMeta(diamondExcavatorMeta);
        ShapedRecipe diamondExcavatorRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "diamond_excavator"), diamondExcavator);
        diamondExcavatorRecipe.shape("***","?#?"," % ");
        diamondExcavatorRecipe.setIngredient('%', Material.BLAZE_ROD);
        diamondExcavatorRecipe.setIngredient('?', Material.DIAMOND);
        diamondExcavatorRecipe.setIngredient('*', Material.DIAMOND_SHOVEL);
        diamondExcavatorRecipe.setIngredient('#', Material.DIAMOND_BLOCK);
        getServer().addRecipe(diamondExcavatorRecipe);

        ItemStack backpack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta backpackMeta = backpack.getItemMeta();
        backpackMeta.setDisplayName("§rBackpack");
        backpackMeta.setLocalizedName("backpack");
        backpackMeta.setCustomModelData(10030001);
        backpack.setItemMeta(backpackMeta);
        ShapedRecipe backpackRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "backpack"), backpack);
        backpackRecipe.shape("*#*","%!%","*%*");
        backpackRecipe.setIngredient('#', Material.IRON_INGOT);
        backpackRecipe.setIngredient('%', Material.LEATHER);
        backpackRecipe.setIngredient('*', Material.STRING);
        backpackRecipe.setIngredient('!', Material.CHEST);
        getServer().addRecipe(backpackRecipe);

        ItemStack largebackpack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta largebackpackMeta = largebackpack.getItemMeta();
        largebackpackMeta.setDisplayName("§rLarge Backpack");
        largebackpackMeta.setLocalizedName("large_backpack");
        largebackpackMeta.setCustomModelData(10030002);
        largebackpack.setItemMeta(largebackpackMeta);
        ShapedRecipe largebackpackRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "large_backpack"), largebackpack);
        largebackpackRecipe.shape("*#*","%!%","*%*");
        largebackpackRecipe.setIngredient('#', Material.GOLD_INGOT);
        largebackpackRecipe.setIngredient('%', Material.LEATHER);
        largebackpackRecipe.setIngredient('*', Material.STRING);
        largebackpackRecipe.setIngredient('!', Material.SHULKER_BOX);
        getServer().addRecipe(largebackpackRecipe);

        ItemStack enderbackpack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta enderbackpackMeta = enderbackpack.getItemMeta();
        enderbackpackMeta.setDisplayName("§rEnder Backpack");
        enderbackpackMeta.setLocalizedName("ender_backpack");
        enderbackpackMeta.setCustomModelData(10030003);
        enderbackpack.setItemMeta(enderbackpackMeta);
        ShapedRecipe enderbackpackRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "ender_backpack"), enderbackpack);
        enderbackpackRecipe.shape("*#*","%!%","*%*");
        enderbackpackRecipe.setIngredient('#', Material.ENDER_EYE);
        enderbackpackRecipe.setIngredient('%', Material.LEATHER);
        enderbackpackRecipe.setIngredient('*', Material.STRING);
        enderbackpackRecipe.setIngredient('!', Material.ENDER_CHEST);
        getServer().addRecipe(enderbackpackRecipe);

        ItemStack extraBucked = new ItemStack(Material.BUCKET);
        ItemMeta extraBuckedMeta = extraBucked.getItemMeta();
        extraBuckedMeta.setDisplayName("§rExtra Bucked");
        extraBuckedMeta.setLocalizedName("extra_bucket");
        ArrayList<String> extraBuckedLore = new ArrayList<>();
        extraBuckedLore.add("");
        extraBuckedLore.add("§rMode: §e" + "3x3x3");
        extraBuckedMeta.setLore(extraBuckedLore);
        extraBuckedMeta.setCustomModelData(10030001);

        extraBucked.setItemMeta(extraBuckedMeta);

        ShapedRecipe extraBuckedRecipe = new ShapedRecipe( new NamespacedKey(MineAndGlory.getInstance(), "extra_bukket"), extraBucked);
        extraBuckedRecipe.shape("* *", " * ");
        extraBuckedRecipe.setIngredient('*', Material.DIAMOND_BLOCK);


        getServer().addRecipe(extraBuckedRecipe);
    }

    private void sendPos()
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    Location loc = player.getLocation();

                    String direction = "";
                    float yaw = loc.getYaw();

                    if (yaw < 0) {
                        yaw += 360;
                    }

                    if (yaw >= 315 || yaw < 45) {
                       direction = "S";
                    } else if (yaw < 135) {
                        direction = "W";
                    } else if (yaw < 225) {
                        direction = "N";
                    } else if (yaw < 315) {
                        direction = "E";
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§6X:§b" + loc.getBlockX() + " §6Y:§b" + loc.getBlockY() + " §6Z:§b" + loc.getBlockZ() + " §6" + direction).create());
                }
            }
        }.runTaskTimer(this, 0, 2);

    }
        private void checkAFK()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    if(afk.contains(player)) continue;

                    if(!lastLocation.containsKey(player))
                    {
                        lastLocation.put(player, player.getLocation());
                        continue;
                    }

                    if(lastLocation.get(player).equals(player.getLocation()))
                    {
                        if(!afkCount.containsKey(player))
                        {
                            afkCount.put(player, 0);
                        }

                        int count = afkCount.get(player) + 1;
                        afkCount.put(player, count);

                        if(count >= 300)
                        {
                            afk.add(player);
                            Bukkit.broadcastMessage(player.getName() + " est désormais afk");

                            afkCount.remove(player);
                            lastLocation.remove(player);
                        }
                    }
                    else
                    {
                        lastLocation.put(player, player.getLocation());
                    }
                }
            }
        }.runTaskTimer(this, 0 , 20);

    }




    private void refreshTPS()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Long millis = (System.currentTimeMillis() - passedTime);
                double seconds = millis.floatValue() / 1000.00;

                TPS = 100 / seconds;

                BigDecimal bd = new BigDecimal(TPS);
                bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
                TPS = bd.doubleValue();

                passedTime = System.currentTimeMillis();

            }
        }.runTaskTimer(this,  0L, 100L);


    }

    private void sheduleTablist()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    player.setPlayerListHeader(
                            "§r \n" +
                            "§r§eMine And Glory\n" +
                            "§r      §m                   §r      \n");

                    player.setPlayerListFooter(
                            "§r      §m                   §r      \n" +
                            "§rOnline: §b" + Bukkit.getOnlinePlayers().size() + "§r/§b" + Bukkit.getMaxPlayers() + "\n" +
                            "§rTPS: §a" + TPS + "\n" +
                            "§r      §m                   §r      \n" +
                            "§r§e" + clock + "\n" +
                            "§r ");
                }
            }
        }.runTaskTimer(this,  0, 10);
    }

    private void updateGameTime()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (World world : Bukkit.getWorlds())
                {
                    world.setFullTime(world.getFullTime() + 1);
                }

                long gameTime = Bukkit.getWorld("world").getTime() + 6000;
                int hours = (int) Math.floor(gameTime / 1000);
                int minutes = (int) ((gameTime % 1000) / 1000.0 * 60);
                if (hours >= 24) hours -= 24;

                clock = String.format("%02d", hours) + ":" + String.format("%02d", minutes);
            }
        }.runTaskTimer(this, 0 , TIMEMULTIPLIER);

    }


    // Getters

    public static MineAndGlory getInstance()
    {
        return instance;
    }

    public static HikariDataSource getHikari()
    {
        return hikari;
    }

    public static ConsoleCommandSender getConsole()
    {
        return console;
    }
}
