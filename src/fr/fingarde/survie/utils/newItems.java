package fr.fingarde.survie.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class newItems
{
    public static ItemStack getMithrilFAS()
    {
        ItemStack itemStack = new ItemStack(Material.FLINT_AND_STEEL);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(1);

        meta.setUnbreakable(true);
        meta.setLocalizedName("mithril_flint_and_steel");
        meta.setDisplayName(ChatColor.RESET + "Mithril Flint and Steel");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getBackpack() {
        ItemStack itemStack = new ItemStack(Material.SHEARS);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(1);

        meta.setUnbreakable(true);
        meta.setLocalizedName("backpack");
        meta.setDisplayName(ChatColor.RESET + "Backpack");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getGoldBackpack() {
        ItemStack itemStack = new ItemStack(Material.SHEARS);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(2);

        meta.setUnbreakable(true);
        meta.setLocalizedName("backpack_gold");
        meta.setDisplayName(ChatColor.RESET + "Gold Backpack");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getEnderBackpack() {
        ItemStack itemStack = new ItemStack(Material.SHEARS);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(3);

        meta.setUnbreakable(true);
        meta.setLocalizedName("ender_backpack");
        meta.setDisplayName(ChatColor.RESET + "End Backpack");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getWaystone()
    {
        ItemStack itemStack = new ItemStack(Material.SHEARS);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(4);

        meta.setUnbreakable(true);
        meta.setLocalizedName("survie:waystone");
        meta.setDisplayName(ChatColor.RESET + "Waystone");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getWaystoneGui()
    {
        ItemStack itemStack = new ItemStack(Material.GOLDEN_HOE);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(1);

        meta.setUnbreakable(true);
        meta.setLocalizedName("survie:waystoneGUI");
        meta.setDisplayName(ChatColor.RESET + "");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getPrevious()
    {
        ItemStack itemStack = new ItemStack(Material.WOODEN_HOE);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(1);

        meta.setUnbreakable(true);
        meta.setLocalizedName("survie:arrow_previous");
        meta.setDisplayName(ChatColor.RESET + "");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getNext()
    {
        ItemStack itemStack = new ItemStack(Material.WOODEN_HOE);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(2);

        meta.setUnbreakable(true);
        meta.setLocalizedName("survie:arrow_next");
        meta.setDisplayName(ChatColor.RESET + "");

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
