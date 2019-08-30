package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import fr.fingarde.mineandglory.utils.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class BackpackListener implements Listener
{
    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        if (event.getItem() == null)
            return;

        ItemStack item = event.getItem();

        if (item.getItemMeta() == null)
            return;

        ItemMeta meta = item.getItemMeta();

        if (!meta.getLocalizedName().contains("backpack"))
            return;

        String localizedName = meta.getLocalizedName();

        if (localizedName.equalsIgnoreCase("ender_backpack")) {
            openEnderChest(event.getPlayer());
            return;
        }

        int size = 0;

        if (localizedName.equalsIgnoreCase("backpack")) size = 27;
        if (localizedName.equalsIgnoreCase("large_backpack")) size = 54;

        UUID id = null;

        if (meta.getLore() == null)
            return;

        String idLigne = null;
        for (String ligne : meta.getLore()) {
            if (ligne.startsWith("§eID: ")) {
                idLigne = ligne;
                break;
            }
        }

        if (idLigne == null)
            return;

        for (int i = 0; i < idLigne.length(); i++) {
            if (idLigne.charAt(i) == '§') {
                idLigne = idLigne.replaceAll(idLigne.substring(i, i + 2), "");
            }
        }

        idLigne = idLigne.split(" ")[1];

        id = UUID.fromString(idLigne);

        Backpack backpack = null;

        for (Backpack backpackList : MineAndGlory.backpacks) {
            if (backpackList.getId() == id) {
                backpack = backpackList;
                break;
            }
        }

        if (backpack == null) {
            backpack = new Backpack(id, size);
            MineAndGlory.backpacks.add(backpack);
            MineAndGlory.backpacksToCreate.add(backpack);
        }

        Inventory inventory = Bukkit.createInventory(null, size, "Backpack");

        if (backpack.getContents() != null) inventory.setContents(backpack.getContents());

        event.getPlayer().openInventory(inventory);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Ender Backpack")) {
            event.getPlayer().getEnderChest().setContents(event.getInventory().getContents());
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase("Backpack")) {

            if(event.getPlayer().getInventory().getItemInMainHand() == null)
            return;
        }


    }

    private void openEnderChest(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, "Ender Backpack");
        inventory.setContents(player.getEnderChest().getContents());
        player.openInventory(inventory);
    }
}
