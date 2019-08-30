package fr.fingarde.survie;

import com.zaxxer.hikari.HikariDataSource;
import fr.fingarde.survie.commands.CommandBite;
import fr.fingarde.survie.commands.DeathHistory;
import fr.fingarde.survie.commands.Light;
import fr.fingarde.survie.listeners.*;
import fr.fingarde.survie.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JavaPlugin
{
    private static Material blockPortalAlfheim = Material.BLUE_ICE;

    private final int TIMEMULTIPLIER = 6;
    private final Difficulty DIFFICULTY = Difficulty.HARD;

    private static BossBar bar;
    private static Main instance;
    private static HikariDataSource hikari;

    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Chunk> chunks = new ArrayList<>();

    @Override
    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();

        connectDB();
        createTables();


        createBossBar();
        setGamerules();
        createDataFolder();
        updateGameTime();

        registerEvents();
        registerCommands();
        registerCrafts();



        int blockReplacingPerTick = getConfig().getInt("blockReplacingPerTick");
        int chunkChekingPerTick = getConfig().getInt("chunkChekingPerTick");

        BukkitRunnable run = new BukkitRunnable()
        {
            @Override
            public void run() {
                for(int i = 0; i < blockReplacingPerTick; i++)
                {

                    if(blocks.size() != 0)
                    {
                        blocks.get(0).setType(Material.STONE);
                        blocks.remove(blocks.get(0));
                    }
                }
            }
        };

        BukkitRunnable run2 = new BukkitRunnable()
        {
            @Override
            public void run() {
                for(int i = 0; i < chunkChekingPerTick; i++)
                {
                    if(chunks.size() != 0)
                    {
                        Utils.checkChunk(chunks.get(0));

                        chunks.remove(chunks.get(0));
                    }
                }
            }
        };

        run2.runTaskTimer(this, 1 , 0);
        run.runTaskTimer(this, 1 , 0);
    }

    @Override
    public void onDisable()
    {
        bar.removeAll();

        if (hikari != null) hikari.close();
    }



    ///                     DATABASE                     ///
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
        try {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Players " +
                    "(" +
                    "Player_Name VARCHAR(16) NOT NULL, " +
                    "Player_Group VARCHAR(30) NOT NULL, " +
                    "Player_Money BIGINT NOT NULL, " +
                    "Player_Death INT(6) NOT NULL, " +
                    "Player_Spawned_Dragon INT(6) NOT NULL, " +
                    "Waystone TEXT NOT NULL" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[Players] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[Players] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        try {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Claims " +
                    "(" +
                    "X INT(6) NOT NULL, " +
                    "Z INT(6) NOT NULL, " +
                    "Owner_Name VARCHAR(16) NOT NULL, " +
                    "Members TEXT" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[Claims] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[Claims] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        try {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS OreLogger " +
                    "(" +
                    "Player_Name VARCHAR(16) NOT NULL, " +
                    "Stone BIGINT NOT NULL, " +
                    "Coal_Ore BIGINT NOT NULL, " +
                    "Iron_Ore BIGINT NOT NULL, " +
                    "Gold_Ore BIGINT NOT NULL, " +
                    "Diamond_Ore BIGINT NOT NULL, " +
                    "Emerald_Ore BIGINT NOT NULL, " +
                    "Quartz_Ore BIGINT NOT NULL" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[OreLogger] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[OreLogger] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        try {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS DeathLogger " +
                    "(" +
                    "Player_Name VARCHAR(16) NOT NULL, " +
                    "Collected TINYINT(1) NOT NULL, " +
                    "Cause TEXT NOT NULL," +
                    "Timestamp TEXT NOT NULL, " +
                    "Location TEXT NOT NULL," +
                    "Inventory TEXT NOT NULL" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[DeathLogger] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[DeathLogger] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        try {
            Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Waystones " +
                    "(" +
                    "Owner_Name VARCHAR(16) NOT NULL, " +
                    "Name VARCHAR(36) NOT NULL, " +
                    "ID TEXT NOT NULL, " +
                    "Location TEXT NOT NULL " +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            console.sendMessage(ChatColor.RED + "[Waystones] ERROR: Impossible d'atteindre la base de données !");
            console.sendMessage(ChatColor.GOLD + "[Waystones] INFO: Le plugin se désactive automatiquement.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }



    ///                     REGISTER                     ///

    private void registerCommands()
    {
        getCommand("deathhistory").setExecutor(new DeathHistory());
        getCommand("light").setExecutor(new Light());
        getCommand("bite").setExecutor(new CommandBite());
    }

    private void registerEvents()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WorldListerner(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CropsListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSleepEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new VehicleListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BedrockEvent(), this);
    }

    private void registerCrafts()
    {
        Material[] wools = {Material.RED_WOOL, Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL};

        for(Material material : Arrays.asList(wools))
        {
            ShapelessRecipe string = new ShapelessRecipe(new NamespacedKey(this, "recipe_string_" + material.name()), new ItemStack(Material.STRING, 4));
            string.addIngredient(material);
        }
    }



    ///                     OTHER                     ///

    private void updateGameTime()
    {
        Bukkit.getScheduler().runTaskTimer(this, () ->
        {
            for(World world : Bukkit.getWorlds())
            {
                world.setFullTime(world.getFullTime() + 1);
            }

            long gameTime = Bukkit.getWorld("world").getTime() + 6000;
            int hours = (int) Math.floor(gameTime / 1000);
            int minutes = (int) ((gameTime % 1000) / 1000.0 * 60);
            if(hours >= 24) hours -= 24;

            bar.setTitle(ChatColor.YELLOW + String.format("%02d", hours) + ":" + String.format("%02d", minutes));
        },TIMEMULTIPLIER,TIMEMULTIPLIER);
    }

    private void createDataFolder()
    {
        getDataFolder().mkdirs();
    }

    private void setGamerules()
    {
        for(World world : Bukkit.getWorlds())
        {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 400);

            world.setDifficulty(DIFFICULTY);
        }
    }

    private void createBossBar()
    {
        bar = Bukkit.createBossBar(ChatColor.WHITE + "", BarColor.WHITE, BarStyle.SOLID);

        for(Player player : Bukkit.getOnlinePlayers())
        {
            bar.addPlayer(player);
        }
    }



    ///                     GETTER                     ///

    public static Material getBlockPortalAlfheim()
    {
        return blockPortalAlfheim;
    }

    public static BossBar getBar()
    {
        return bar;
    }

    public static ConsoleCommandSender getConsole() {
        return console;
    }

    public static Main getInstance()
    {
        return instance;
    }

    public static HikariDataSource getHikari()
    {
        return hikari;
    }
}
