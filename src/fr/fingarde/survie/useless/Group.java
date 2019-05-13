package fr.fingarde.survie.useless;

import fr.fingarde.survie.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Group
{
    private String group;

    public Group(String group)
    {
        this.group = group;
    }

    public String getPrefix()
    {
        return null;
    }

    public String getSuffix()
    {
        return null;
    }

    public ArrayList<String> getPermissions()
    {
        return null;
    }

    public String getInherit()
    {
        return null;
    }

    public File getConfig() {
        File file = new File(Main.getInstance().getDataFolder(), group +  ".yml");

        return file;
    }

    private Object getObject(String path)
    {
        File file = getConfig();

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);

            Object obj = config.get(path);

            if(obj == null) return null;

            return obj;

        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Une erreur est survenue dans la config.");
            e.printStackTrace();
        }

        return null;
    }

    private void setObject(String path, Object object)
    {
        File file = getConfig();

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);

            config.set(path, object);

            config.options().copyDefaults(true);

            config.save(file);

        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Une erreur est survenue dans la config.");
            e.printStackTrace();
        }
    }
}
