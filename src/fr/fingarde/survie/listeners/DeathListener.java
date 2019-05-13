package fr.fingarde.survie.listeners;

import fr.fingarde.survie.Main;
import fr.fingarde.survie.objects.JPlayer;
import fr.fingarde.survie.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class DeathListener extends Listeners
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {

        Player player = event.getEntity();
        JPlayer jPlayer = new JPlayer(player);

        UUID id = Utils.saveDeathInventory(event.getEntity().getInventory());

        jPlayer.addDeath(id, event.getDeathMessage());

        Block b =  event.getEntity().getLocation().getBlock();
        b.setType(Material.DIAMOND_BLOCK);
        b.setMetadata("death_id", new FixedMetadataValue(Main.getInstance(), id));

        event.getDrops().clear();
    }
}
