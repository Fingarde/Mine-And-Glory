package fr.fingarde.mineandglory.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TimberListener implements Listener
{
    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if(!event.getBlock().getType().name().contains("_LOG")) return;

        if(event.getPlayer() == null) return;
        if(!event.getPlayer().isSneaking()) return;
        if(event.getPlayer().getInventory().getItemInMainHand() == null) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(!item.getType().name().contains("_AXE")) return;
        if(item.getItemMeta() == null) return;

        ItemMeta meta = item.getItemMeta();

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

        int durability = Integer.valueOf(split[0]);
        int maxDurability = Integer.valueOf(split[1]);


        ArrayList<Block> blocksToCheck = new ArrayList<>();
        ArrayList<Block> blocksToBreak = new ArrayList<>();

        blocksToCheck.add(event.getBlock());

        while (blocksToCheck.size() > 0)
        {
            for(int y = 0 ; y < 2 ; y++)
            {
                for(int x = -1 ; x < 2 ;x++)
                {
                    for(int z = -1 ; z < 2 ; z++)
                    {

                        if(blocksToBreak.size() >= durability - 1) break;

                        Block blockToCheck = blocksToCheck.get(0).getRelative(x, y, z);
                        if(blockToCheck.getType() == event.getBlock().getType())
                        {
                            if(event.getBlock().getLocation().distance(blockToCheck.getLocation()) > 30) continue;
                            if(blocksToBreak.contains(blockToCheck)) continue;
                            if(blocksToCheck.contains(blockToCheck)) continue;

                            blocksToCheck.add(blockToCheck);
                        }
                    }
                }
            }

            blocksToBreak.add(blocksToCheck.get(0));
            blocksToCheck.remove(blocksToCheck.get(0));

        }

        int newDurability = durability - blocksToBreak.size();


        ArrayList<String> lore = new ArrayList<>();

        for (String ligne : item.getItemMeta().getLore())
        {
            if (ligne.startsWith("§rDurability: "))
            {
                lore.add("§rDurability: §e" + newDurability + "§r/§e" + maxDurability);
            } else
            {
                lore.add(ligne);
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        for(Block block : blocksToBreak)
        {
            block.breakNaturally(item);
        }


    }
}
