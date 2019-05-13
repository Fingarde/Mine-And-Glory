package fr.fingarde.survie.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class newItems
{
    public enum type
    {
        BLOCK,
        ITEM,
        TOOLS
    }
    public enum items
    {
        // ORES
        emerald_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:emerald_ore", ChatColor.GREEN + "Emerald Ore", 1, newBlocks.blocks.emerald_ore.name()),
        amethyst_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:amethyst_ore", ChatColor.LIGHT_PURPLE + "Amethyst Ore", 2, newBlocks.blocks.amethyst_ore.name()),
        ruby_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:ruby_ore", ChatColor.RED + "Ruby Ore", 3, newBlocks.blocks.ruby_ore.name()),
        saphir_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:saphir_ore", ChatColor.BLUE + "Saphir Ore", 4, newBlocks.blocks.saphir_ore.name()),
        onyx_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:onyx_ore", ChatColor.DARK_GRAY + "Onyx Ore", 5, newBlocks.blocks.onyx_ore.name()),
        meteorite_ore (newItems.type.BLOCK, Material.IRON_HOE, "survie:meteorite_ore", ChatColor.GOLD + "Meteorite Ore", 6, newBlocks.blocks.meteorite_ore.name()),

        // BLOCKS
        emerald_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:emerald_block", ChatColor.GREEN + "Emerald Block", 7, newBlocks.blocks.emeral_block.name()),
        amethyst_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:amethyst_block", ChatColor.LIGHT_PURPLE + "Amethyst Block", 8, newBlocks.blocks.amethyst_block.name()),
        ruby_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:ruby_block", ChatColor.RED + "Ruby Block", 9, newBlocks.blocks.ruby_block.name()),
        saphir_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:saphir_block", ChatColor.BLUE + "Saphir Block", 10, newBlocks.blocks.saphir_block.name()),
        onyx_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:onyx_block", ChatColor.DARK_GRAY + "Onyx Block", 11, newBlocks.blocks.onyx_block.name()),
        meteorite_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:meteorite_block", ChatColor.GOLD + "Meteorite Block", 12, newBlocks.blocks.meteorite_block.name()),

        // OTHERS
        lucky_block (newItems.type.BLOCK, Material.IRON_HOE, "survie:lucky_block", ChatColor.GOLD + "Lucky Block", 13, newBlocks.blocks.lucky_block.name()),
        meteorite_stone (newItems.type.BLOCK, Material.IRON_HOE, "survie:meteorite_stone", ChatColor.DARK_GRAY  + "Meteorite Stone", 14, newBlocks.blocks.meteorite_stone.name());


        public final newItems.type type;
        private final Material baseItem;
        private final String localizedName;
        private final String displayName;
        private final int durability;
        public final String data;

        items(newItems.type type, Material baseItem, String localizedName, String displayName, int durability, String data){
            this.type = type;
            this.baseItem = baseItem;
            this.localizedName = localizedName;
            this.displayName = displayName;
            this.durability = durability;
            this.data = data;
        }
    }

    public static ItemStack getItem(items item)
    {
        ItemStack itemStack = new ItemStack(item.baseItem);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(item.durability);

        meta.setUnbreakable(true);
        meta.setLocalizedName(item.localizedName);
        meta.setDisplayName(ChatColor.RESET + item.displayName);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

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
        ItemStack itemStack = new ItemStack(Material.IRON_HOE);

        ItemMeta meta =  itemStack.getItemMeta();

        ((Damageable) meta).setDamage(4);

        meta.setUnbreakable(true);
        //meta.setLocalizedName("survie:waystone");
        meta.setLocalizedName("block_meteorite_ore");
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
