package fr.fingarde.mineandglory.utils;

import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Rank
{
    private String rank;
    private MemorySection data;

    public Rank(String rank)
    {
        this.rank = rank;
        File file = getConfig();

        YamlConfiguration config = new YamlConfiguration();

        try
        {
            config.load(file);

           data = (MemorySection) config.get(rank);
        } catch (InvalidConfigurationException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getPrefix()
    {
        if(data.get("prefix") == null) return null;
        return (String) data.get("prefix");
    }

    public String getSuffix()
    {
        if(data.get("suffix") == null) return null;
        return (String) data.get("suffix");
    }

    public CharSequence getColor()
    {
        if(data.get("color") == null) return null;
        return (String) data.get("color");
    }

    public String getTeamName()
    {
        if(data.get("placeintab") == null) return "999_" + rank;
        return data.getInt("placeintab") + "_" + rank;
    }


    public ArrayList<String> getPermissions()
    {
        return null;
    }

    private String getInherit()
    {
        return null;
    }

    public File getConfig() {
        File file = new File(MineAndGlory.getInstance().getDataFolder(), "group.yml");

        return file;
    }
}
