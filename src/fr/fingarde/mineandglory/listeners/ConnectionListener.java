package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import fr.fingarde.mineandglory.utils.Rank;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scoreboard.Team;

import java.sql.*;
import java.util.HashMap;

public class ConnectionListener implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);

        try
        {
            Connection connection = MineAndGlory.getHikari().getConnection();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM Players WHERE UUID = '" + player.getUniqueId().toString() + "'");

            HashMap<String, String> map = new HashMap<>();

            if(result.next())
            {
                map.put("rank", result.getString("rank"));
                map.put("money", result.getString("money"));
                map.put("glory", result.getString("glory"));
                map.put("waystones", result.getString("waystones"));
            }
            else
            {
                statement.executeUpdate("INSERT INTO Players (UUID, RANK, MONEY, GLORY, WAYSTONES) VALUES ('" + player.getUniqueId().toString() + "', 'member', '0', '0', '')");


                map.put("rank", "member");
                map.put("money", "0");
                map.put("glory", "0");
                map.put("waystones", "");
            }

            MineAndGlory.players.put(event.getPlayer(), map);

            statement.close();
            connection.close();

            Rank rank = new Rank(map.get("rank"));

            if(rank.getPrefix() != null)
            {
                player.setDisplayName(rank.getPrefix() + " " + player.getName());
            }

            if(rank.getSuffix() != null)
            {
                player.setDisplayName(player.getName() + " " + rank.getSuffix());
            }

            if(rank.getColor() != null)
            {
                player.setDisplayName(player.getDisplayName().replace(player.getName(),rank.getColor() + player.getName()));
            }

            player.setPlayerListName(player.getDisplayName());
            event.setJoinMessage("§7[§a+§7] §a" + player.getDisplayName());

            try
            {
                Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(rank.getTeamName());
            }
            catch (IllegalArgumentException e) { }

            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(rank.getTeamName());

            team.addEntry(player.getName());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        event.setQuitMessage("§7[§c-§7] §c" + player.getDisplayName());

        if(MineAndGlory.afk.contains(player))
        {
            MineAndGlory.afk.remove(player);
        }

        if(MineAndGlory.afkCount.containsKey(player))
        {
            MineAndGlory.afkCount.remove(player);
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event)
    {
        event.setMaxPlayers(0);

        event.setMotd("                      §aMine And Glory\n             §bIl est §e" + MineAndGlory.clock + " §bsur le serveur");
    }
}
