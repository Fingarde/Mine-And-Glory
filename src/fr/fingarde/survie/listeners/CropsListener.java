package fr.fingarde.survie.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CropsListener implements Listener
{
    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        if(event.getClickedBlock() == null) return;
        if(!isCrop(event.getClickedBlock().getType())) return;

        Ageable ageable = (Ageable) event.getClickedBlock().getBlockData();

        if(ageable.getAge() != ageable.getMaximumAge()) return;

        int fortuneLevel = 0;

        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
        if(mainHand.getItemMeta() != null)
        if(mainHand.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))
        {
            fortuneLevel = mainHand.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
        }

        dropCrop(event.getClickedBlock().getType(), event.getClickedBlock().getLocation(), fortuneLevel);

        ageable.setAge(0);

        event.getClickedBlock().setBlockData(ageable);
    }

    private void dropCrop(Material material, Location location, int fortuneLevel)
    {
        switch (material)
        {
            case WHEAT:
                int nbWheat = 1 + (int) ((Math.random() * 1.1) + Math.random() * (fortuneLevel / 1.5));
                int nbSeed = (int) (Math.random() * (3 + (fortuneLevel / 1.5)));

                location.getWorld().dropItemNaturally(location, new ItemStack(Material.WHEAT, nbWheat));
                location.getWorld().dropItemNaturally(location, new ItemStack(Material.WHEAT_SEEDS, nbSeed));

                break;
            case POTATOES:
                int nbPotatoes = new Random().nextInt(4 + fortuneLevel) + 1;

                location.getWorld().dropItemNaturally(location, new ItemStack(Material.POTATO, nbPotatoes));

                break;
            case CARROTS:
                int nbCarrots = new Random().nextInt(4 + fortuneLevel) + 1;

                location.getWorld().dropItemNaturally(location, new ItemStack(Material.CARROT, nbCarrots));


                break;
            case BEETROOTS:
                int nbBeetroots = 1 + (int) ((Math.random() * 1.1) + Math.random() * (fortuneLevel / 1.5));
                int nbBSeed = (int) (Math.random() * (3 + (fortuneLevel / 1.5)));

                location.getWorld().dropItemNaturally(location, new ItemStack(Material.BEETROOT, nbBeetroots));
                location.getWorld().dropItemNaturally(location, new ItemStack(Material.BEETROOT_SEEDS, nbBSeed));

                break;
        }
    }


    private boolean isCrop(Material material)
    {
        switch (material)
        {
            case WHEAT:
            case POTATOES:
            case CARROTS:
            case BEETROOTS:
                return true;
        }

        return false;
    }
}
