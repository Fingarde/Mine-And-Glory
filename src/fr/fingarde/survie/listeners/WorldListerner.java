package fr.fingarde.survie.listeners;

import fr.fingarde.survie.utils.WaystoneUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class WorldListerner implements Listener
{
    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        EquipmentSlot e = event.getHand();
        if (!e.equals(EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        if(event.getClickedBlock() != null)
        {
            Block block = event.getClickedBlock();
            if(block.getType() == Material.BARRIER || block.getType() == Material.MUSHROOM_STEM)
            {
                List<MetadataValue> metadataValues = block.getMetadata("survie:type");

                if(metadataValues.size() != 0)
                {
                    String type = metadataValues.get(0).asString();

                    if(type.equalsIgnoreCase("waystone"))
                    {
                        if(player.isSneaking()) return;

                        event.setCancelled(true);
                        String waystoneID = block.getMetadata("survie:waystoneID").get(0).asString();

                        WaystoneUtils.openWaystone(waystoneID, player, 0);
                        return;
                    }
                }
            }
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta() != null)
                {
                    if(event.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("survie:waystone"))
                    {
                        WaystoneUtils.createWaystone(event);
                    }
                }

            }
        }
    }
}
