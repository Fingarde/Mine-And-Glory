package fr.fingarde.mineandglory.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();

        ((SkullMeta) meta).setOwningPlayer(event.getEntity());

        item.setItemMeta(meta);

        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
    }
}
