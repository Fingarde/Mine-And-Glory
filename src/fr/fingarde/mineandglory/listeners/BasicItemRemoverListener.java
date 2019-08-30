package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import net.minecraft.server.v1_14_R1.DataWatcher;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class BasicItemRemoverListener implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if (event.getCursor() == null)
            return;

        ItemStack item = event.getCursor();
        if (item.getType().getMaxDurability() == 0)
            return;

        ItemMeta meta = item.getItemMeta();

        if (meta.getLocalizedName() != "")
            return;
        if (meta.getLore() != null)
        {
            for (String ligne : meta.getLore())
            {
                if (ligne.startsWith("§rDurability: "))
                    return;
            }
        }

        ArrayList<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§rDurability: §e" + item.getType().getMaxDurability() + "§r/§e" + item.getType().getMaxDurability());

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.getItem() == null)
            return;

        ItemStack item = event.getItem();
        if (item.getType().getMaxDurability() == 0)
            return;

        ItemMeta meta = item.getItemMeta();

        if (meta.getLocalizedName() != "")
            return;
        if (meta.getLore() != null)
        {
            for (String ligne : meta.getLore())
            {
                if (ligne.startsWith("§rDurability: "))
                    return;
            }
        }

        ArrayList<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§rDurability: §e" + item.getType().getMaxDurability() + "§r/§e" + item.getType().getMaxDurability());

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @EventHandler
    public void onDamage(PlayerItemDamageEvent event)
    {
        ItemStack item = event.getItem();

        if (item.getItemMeta() == null)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta.getLore() == null)
            return;

        String durabilityLigne = null;
        for (String ligne : meta.getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                durabilityLigne = ligne;
                break;
            }
        }

        if (durabilityLigne == null)
            return;

        for (int i = 0; i < durabilityLigne.length(); i++)
        {
            if (durabilityLigne.charAt(i) == '§')
            {
                durabilityLigne = durabilityLigne.replaceAll(durabilityLigne.substring(i, i + 2), "");
            }
        }

        String[] split = durabilityLigne.split(" ")[1].split("/");

        int durability = Integer.valueOf(split[0]);
        int maxDurability = Integer.valueOf(split[1]);

        int newDurabily = durability - event.getDamage();
        if (newDurabily <= 0)
        {
            item.setType(Material.AIR);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
            return;
        }

        int durabilityConverted = item.getType().getMaxDurability() * newDurabily / maxDurability;
        ((Damageable) meta).setDamage(item.getType().getMaxDurability() - durabilityConverted);

        ArrayList<String> lore = new ArrayList<>();

        for (String ligne : item.getItemMeta().getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                lore.add("§rDurability: §e" + newDurabily + "§r/§e" + maxDurability);
            } else
            {
                lore.add(ligne);
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        event.setDamage(0);
    }

    @EventHandler
    public void onRepair(PlayerItemMendEvent event)
    {
        ItemStack item = event.getItem();

        if (item.getItemMeta() == null)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta.getLore() == null)
            return;

        String durabilityLigne = null;
        for (String ligne : meta.getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                durabilityLigne = ligne;
                break;
            }
        }

        if (durabilityLigne == null)
            return;

        for (int i = 0; i < durabilityLigne.length(); i++)
        {
            if (durabilityLigne.charAt(i) == '§')
            {
                durabilityLigne = durabilityLigne.replaceAll(durabilityLigne.substring(i, i + 2), "");
            }
        }

        String[] split = durabilityLigne.split(" ")[1].split("/");

        int durability = Integer.valueOf(split[0]);
        int maxDurability = Integer.valueOf(split[1]);

        int newDurabily = durability + event.getRepairAmount();

        if (newDurabily > maxDurability) newDurabily = maxDurability;

        int durabilityConverted = item.getType().getMaxDurability() * newDurabily / maxDurability;
        ((Damageable) meta).setDamage(item.getType().getMaxDurability() - durabilityConverted);

        ArrayList<String> lore = new ArrayList<>();

        for (String ligne : item.getItemMeta().getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                lore.add("§rDurability: §e" + newDurabily + "§r/§e" + maxDurability);
            } else
            {
                lore.add(ligne);
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        event.setRepairAmount(0);
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event)
    {
        if(willConflict(event))
        {
            event.getResult().setType(Material.AIR);
            event.getInventory().setRepairCost(1000000000);

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    event.setResult(new ItemStack(Material.AIR));
                }
            }.runTaskLater(MineAndGlory.getInstance(), 5);

            return;
        }

        ItemStack item = event.getResult();

        if (item.getItemMeta() == null) return;

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(event.getInventory().getRenameText().replaceAll("&", "§"));

        if (meta.getLore() == null) return;

        String durabilityLigne = null;
        for (String ligne : meta.getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                durabilityLigne = ligne;
                break;
            }
        }

        if (durabilityLigne == null) return;

        for (int i = 0; i < durabilityLigne.length(); i++)
        {
            if (durabilityLigne.charAt(i) == '§')
            {
                durabilityLigne = durabilityLigne.replaceAll(durabilityLigne.substring(i, i + 2), "");
            }
        }

        String[] split = durabilityLigne.split(" ")[1].split("/");

        int maxDurability = Integer.valueOf(split[1]);

        int newDurabily = ((Damageable) meta).getDamage() * maxDurability / item.getType().getMaxDurability();

        if (newDurabily > maxDurability) newDurabily = maxDurability;

        ArrayList<String> lore = new ArrayList<>();

        for (String ligne : item.getItemMeta().getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                lore.add("§rDurability: §e" + (maxDurability - newDurabily) + "§r/§e" + maxDurability);
            } else
            {
                lore.add(ligne);
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }


    public boolean willConflict(PrepareAnvilEvent event)
    {

        if(event.getInventory().getContents()[0] == null || event.getInventory().getContents()[1] == null) return false;

        if(event.getInventory().getContents()[0].getType() != event.getInventory().getContents()[1].getType()) return false;

        ItemStack item1 = event.getInventory().getContents()[0];
        ItemStack item2 = event.getInventory().getContents()[1];

        if(item1.getItemMeta() == null || item2.getItemMeta() == null) return false;

        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        if(meta1.getLocalizedName() == meta2.getLocalizedName()) return false;

        return true;
    }
}
