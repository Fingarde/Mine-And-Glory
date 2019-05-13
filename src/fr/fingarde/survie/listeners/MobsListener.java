package fr.fingarde.survie.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;

public class MobsListener extends Listeners
{
    @EventHandler
    public void onFocus(EntityTargetEvent event)
    {
        if(!(event.getTarget() instanceof Player)) return;

        if(((Player)event.getTarget()).hasPotionEffect(PotionEffectType.INVISIBILITY))
        {
            event.setCancelled(true);
        }
    }
}
