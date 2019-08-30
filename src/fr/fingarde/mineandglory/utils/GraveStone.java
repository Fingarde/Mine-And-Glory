package fr.fingarde.mineandglory.utils;

import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

public class GraveStone {

    private UUID id;
    private ItemStack[] items;
    private long timestamp;
    private UUID playerUUID;
    private Location location;

    public GraveStone(UUID id)
    {
        this.id = id;
    }

    public ItemStack[] getItems()
    {
        return items;
    }

    public void setItems(ItemStack[] items)
    {
        this.items = items;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public UUID getId()
    {
        return id;
    }

    public UUID getPlayerUUID()
    {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID)
    {
        this.playerUUID = playerUUID;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void create()
    {
        try {
            Connection connection = MineAndGlory.getHikari().getConnection();
            Statement statement = connection.createStatement();

            String contents = ItemSerializer.serialize(items);

            statement.executeUpdate("INSERT INTO Gravestones (UUID, PLAYER_UUID, TIMESTAMP, LOCATION, CONTENTS) VALUES ('" + id.toString() + "', '" + playerUUID.toString() + "', '" + timestamp + "', '" + LocationSerializer.serialize(location)+ "', '" + contents + "')");

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void load()
    {
        try {
            Connection connection = MineAndGlory.getHikari().getConnection();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM Gravestones WHERE UUID = '" + id.toString() + "'");

            if(result.next())
            {
                this.playerUUID = UUID.fromString(result.getString("PLAYER_UUID"));
                this.timestamp = result.getLong("TIMESTAMP");
                this.location = LocationSerializer.deserialize(result.getString("LOCATION"));
                this.items = ItemSerializer.deserialize(result.getString("CONTENTS"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean test(Boolean element)
    {
        return(!!element);
    }
}
