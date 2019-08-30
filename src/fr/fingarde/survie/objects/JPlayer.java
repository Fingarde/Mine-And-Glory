package fr.fingarde.survie.objects;

import fr.fingarde.survie.Main;
import fr.fingarde.survie.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

public class JPlayer {
    private Player player;

    public JPlayer(Player p)
    {
        this.player = p;
    }

    public int getDeathCount()
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE Player_Name = '" + player.getName() + "'");
            ResultSet result = statement.executeQuery();

            if (result.next()) do {
                return result.getInt("Player_Death");
            }
            while (result.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setDeathCount(int number)
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Death = '" + number + "' WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDeathCount()
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Death = Player_Death + 1 WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDeathCoun()
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Death = Player_Death - 1 WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMoney() {
        try {
            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE Player_Name = '" + player.getName() + "'");
            ResultSet result = statement.executeQuery();

            if (result.next()) do {
                return result.getInt("Player_Money");
            }
            while (result.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setMoney(int number)
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Money = '" + number + "' WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(int number)
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Money = Player_Money + '" + number + "' WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMoney(int number)
    {
        try {
            Connection connection = Main.getHikari().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE players SET Player_Money = Player_Money - '" + number + "' WHERE Player_Name = '" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[][] get10LastDeaths()
    {
        try {
            String[][] deaths = new String[10][];
            int index = 0;

            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DeathLogger WHERE Player_Name = '" + player.getName() + "' ORDER BY Timestamp DESC LIMIT 10;");
            ResultSet result = statement.executeQuery();

            if (result.next()) do
                {
                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Timestamp.valueOf(result.getString("Timestamp")));

                    deaths[index] = new String[]{
                            String.valueOf(result.getInt("Collected")),
                            result.getString("Cause"),
                            timeStamp,
                            result.getString("Location"),
                            result.getString("Inventory")};

                    index++;
                }
            while (result.next());

            return deaths;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addDeath(UUID id, String deathMessage)
    {
        String location = Utils.serializeLocation(player.getLocation());

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        try {
            Connection  connection = Main.getHikari().getConnection();

            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO DeathLogger (Player_Name, Collected, Cause, Timestamp, Location, Inventory) VALUES ('" + player.getName() +"','0','" + deathMessage + "','" + timeStamp + "', '" + location + "', '" + id + "');");
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }












    public ArrayList<Chunk> getOwnedClaims()
    {
        ArrayList<Chunk> claims = new ArrayList<>();

        try
        {
            Connection connection = Main.getHikari().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM claims WHERE Owner_Name = '" + player.getName() + "'");
            ResultSet result = statement.executeQuery();

            if(result.next()) do
            {
                Main.getConsole().sendMessage(ChatColor.AQUA + "" + result.getString("Owner_Name"));
                claims.add(Bukkit.getWorld("world").getChunkAt(result.getInt("X"), result.getInt("Z")));
            }
            while (result.next());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return claims;
    }


}
