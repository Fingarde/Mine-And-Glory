package fr.fingarde.mineandglory.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class VehicleListener implements Listener
{
    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event)
    {
        Vehicle vehicle = event.getVehicle();

        if(vehicle.getType() != EntityType.BOAT) return;
        Location loc = vehicle.getLocation();

        if(new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.5 , loc.getZ()).getBlock().getType() == Material.WATER || new Location(loc.getWorld(), loc.getX(), loc.getY() + 1.5 , loc.getZ()).getBlock().getType() == Material.WATER)
        {
            vehicle.setVelocity(event.getVehicle().getVelocity().add(new Vector(0, 0.35 , 0)));
        }
    }

    @EventHandler
    public void onVehicleLeave(VehicleExitEvent event)
    {
        Vehicle vehicle = event.getVehicle();

        if(vehicle.getType() != EntityType.BOAT) return;
        if(!(event.getExited() instanceof Player)) return;
        if(((Player) event.getExited()).isSneaking()) return;
        if(vehicle.isDead()) return;

        event.setCancelled(true);
    }
}