package fr.fingarde.mineandglory.listeners;

import fr.fingarde.mineandglory.MineAndGlory;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class HorseListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event)
    {
        if(event.getRightClicked().getPassengers().size() < 1) return;

        Location loc = event.getPlayer().getLocation();
        Vector vector = event.getRightClicked().getBoundingBox().rayTrace(event.getRightClicked().getBoundingBox().getCenter(), event.getRightClicked().getLocation().getDirection().multiply(-1), 0.5).getHitPosition();

        Location ass = new Location(loc.getWorld(), vector.getX(), vector.getY(), vector.getZ());

       /*9 Entity entity = loc.getWorld().spawnEntity(ass, EntityType.ARMOR_STAND);
            entity.setGravity(false);
        //((ArmorStand) entity).setVisible(false);


        entity.addPassenger(event.getPlayer());*/


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Location loc = event.getPlayer().getLocation();
                Vector vector = event.getRightClicked().getBoundingBox().rayTrace(event.getRightClicked().getBoundingBox().getCenter(), event.getRightClicked().getLocation().getDirection().multiply(-1), 2).getHitPosition().multiply(0.8);

                Location ass = new Location(loc.getWorld(), vector.getX(), vector.getY(), vector.getZ());

                event.getPlayer().teleport(ass);

            }
        }.runTaskTimer(MineAndGlory.getInstance(), 0 , 1);
    }
}
