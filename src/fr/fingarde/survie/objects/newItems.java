package fr.fingarde.survie.objects;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.UUID;

public class newItems
{
    public enum type
    {
        BLOCK,
        ITEM,
        ARMOR,
        TOOL
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
        meteorite_stone (newItems.type.BLOCK, Material.IRON_HOE, "survie:meteorite_stone", ChatColor.DARK_GRAY  + "Meteorite Stone", 14, newBlocks.blocks.meteorite_stone.name()),
        breakable_bedrock (newItems.type.BLOCK, Material.IRON_HOE, "survie:breakable_bedrock", ChatColor.DARK_GRAY  + "Bedrock", 14, newBlocks.blocks.breakable_bedrock.name()),
        

        // ITEMS
        emerald (newItems.type.ITEM, Material.SHEARS, "survie:emerald", ChatColor.GREEN + "Emerald", 1, null),
        amethyst (newItems.type.ITEM, Material.SHEARS, "survie:amethyst", ChatColor.LIGHT_PURPLE + "Amethyst", 2, null),
        ruby (newItems.type.ITEM, Material.SHEARS, "survie:ruby", ChatColor.RED + "Ruby", 3, null),
        saphir (newItems.type.ITEM, Material.SHEARS, "survie:saphir", ChatColor.BLUE + "Saphir", 4, null),
        onyx (newItems.type.ITEM, Material.SHEARS, "survie:onyx", ChatColor.DARK_GRAY + "Onyx", 5, null),
        meteorite (newItems.type.ITEM, Material.SHEARS, "survie:meteorite", ChatColor.GOLD + "Meteorite", 6, null),

        // ARMORS
        emerald_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:emerald_helmet", ChatColor.GREEN + "Emerald Helmet", 1, "561 74,255,38 4 2"),
        emerald_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:emerald_chestplate", ChatColor.GREEN + "Emerald Chestplate", 1, "768 74,255,38 9 2"),
        emerald_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:emerald_leggings", ChatColor.GREEN + "Emerald Leggings", 1, "710 74,255,38 7 2"),
        emerald_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:emerald_boots", ChatColor.GREEN + "Emerald Boots", 1, "629 74,255,38 4 2"),

        amethyst_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:amethyst_helmet", ChatColor.LIGHT_PURPLE + "Amethyst Helmet", 2, "759 245,150,255 5 2"),
        amethyst_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:amethyst_chestplate", ChatColor.LIGHT_PURPLE + "Amethyst Chestplate", 2, "1008 245,150,255 10 2"),
        amethyst_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:amethyst_leggings", ChatColor.LIGHT_PURPLE + "Amethyst Leggings", 2, "925 245,150,255 9 2"),
        amethyst_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:amethyst_boots", ChatColor.LIGHT_PURPLE + "Amethyst Boots", 2, "829 245,150,255 5 2"),

        ruby_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:ruby_helmet", ChatColor.RED + "Ruby Helmet", 3, "957 255,31,68 6 2"),
        ruby_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:ruby_chestplate", ChatColor.RED + "Ruby Chestplate", 3, "1248 255,31,68 11 2"),
        ruby_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:ruby_leggings", ChatColor.RED + "Ruby Leggings", 3, "1140 255,31,68 10 2"),
        ruby_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:ruby_boots", ChatColor.RED + "Ruby Boots", 3, "1029 255,31,68 6 2"),

        saphir_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:saphir_helmet", ChatColor.BLUE + "Saphir Helmet", 4, "1155 54,75,255 7 2"),
        saphir_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:saphir_chestplate", ChatColor.BLUE + "Saphir Chestplate", 4, "1488 54,75,255 12 2"),
        saphir_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:saphir_leggings", ChatColor.BLUE + "Saphir Leggings", 4, "1355 54,75,255 11 2"),
        saphir_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:saphir_boots", ChatColor.BLUE + "Saphir Boots", 4, "1229 54,75,255 7 2"),

        onyx_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:onyx_helmet", ChatColor.RED + "Ruby Helmet", 5, "1493 33,34,38 8 2"),
        onyx_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:onyx_chestplate", ChatColor.RED + "Ruby Chestplate", 5, "1728 33,34,38 13 2"),
        onyx_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:onyx_leggings", ChatColor.RED + "Ruby Leggings", 5, "1570 33,34,38 12 2"),
        onyx_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:onyx_boots", ChatColor.RED + "Ruby Boots", 5, "1429 33,34,38 8 2"),

        meteorite_helmet (newItems.type.ARMOR, Material.LEATHER_HELMET, "survie:meteorite_helmet", ChatColor.GOLD + "Meteorite Helmet", 6, "1561 252,153,53 9 2"),
        meteorite_chestplate (newItems.type.ARMOR, Material.LEATHER_CHESTPLATE, "survie:meteorite_chestplate", ChatColor.GOLD + "Meteorite Chestplate", 6, "1968 252,153,53 14 2"),
        meteorite_leggings (newItems.type.ARMOR, Material.LEATHER_LEGGINGS, "survie:meteorite_leggings", ChatColor.GOLD + "Meteorite Leggings", 6, "1785 252,153,53 13 2"),
        meteorite_boots (newItems.type.ARMOR, Material.LEATHER_BOOTS, "survie:meteorite_boots", ChatColor.GOLD + "Meteorite Boots", 6, "1629 252,153,53 9 2"),

        // TOOLS
        emerald_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:emerald_pickaxe", ChatColor.GREEN + "Emerald Pickaxe", 1, "1961"),
        emerald_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:emerald_axe", ChatColor.GREEN + "Emerald Axe", 1, "1961"),
        emerald_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:emerald_shovel", ChatColor.GREEN + "Emerald Shovel", 1, "1961"),
        emerald_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:emerald_hoe", ChatColor.GREEN + "Emerald Hoe", 1, "1961"),

        amethyst_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:amethyst_pickaxe", ChatColor.LIGHT_PURPLE + "Amethyst Pickaxe", 2, "2561"),
        amethyst_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:amethyst_axe", ChatColor.LIGHT_PURPLE + "Amethyst Axe", 2, "2561"),
        amethyst_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:amethyst_shovel", ChatColor.LIGHT_PURPLE + "Amethyst Shovel", 2, "2561"),
        amethyst_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:amethyst_hoe", ChatColor.LIGHT_PURPLE + "Amethyst Hoe", 2, "2561"),

        ruby_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:ruby_pickaxe", ChatColor.RED+ "Ruby Pickaxe", 3, "3061"),
        ruby_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:ruby_axe", ChatColor.RED + "Ruby Axe", 3, "3061"),
        ruby_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:ruby_shovel", ChatColor.RED + "Ruby Shovel", 3, "3061"),
        ruby_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:ruby_hoe", ChatColor.RED + "Ruby Hoe", 3, "3061"),

        saphir_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:saphir_pickaxe", ChatColor.BLUE+ "Saphir Pickaxe", 4, "4061"),
        saphir_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:saphir_axe", ChatColor.BLUE + "Saphir Axe", 4, "4061"),
        saphir_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:saphir_shovel", ChatColor.BLUE + "Saphir Shovel", 4, "4061"),
        saphir_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:saphir_hoe", ChatColor.BLUE + "Saphir Hoe", 4, "4061"),

        onyx_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:onyx_pickaxe", ChatColor.DARK_GRAY + "Onyx Pickaxe", 5, "6061"),
        onyx_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:onyx_axe", ChatColor.DARK_GRAY + "Onyx Axe", 5, "6061"),
        onyx_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:onyx_shovel", ChatColor.DARK_GRAY + "Onyx Shovel", 5, "6061"),
        onyx_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:onyx_hoe", ChatColor.DARK_GRAY + "Onyx Hoe", 5, "6061"),

        meteorite_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:meteorite_pickaxe", ChatColor.GOLD + "Meteorite Pickaxe", 6, "8061"),
        meteorite_axe (newItems.type.TOOL, Material.DIAMOND_AXE, "survie:meteorite_axe", ChatColor.GOLD + "Meteorite Axe", 6, "8061"),
        meteorite_shovel (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:meteorite_shovel", ChatColor.GOLD + "Meteorite Shovel", 6, "8061"),
        meteorite_hoe (newItems.type.TOOL, Material.DIAMOND_HOE, "survie:meteorite_hoe", ChatColor.GOLD + "Meteorite Hoe", 6, "8061"),

        emerald_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:emerald_hammer", ChatColor.GREEN + "Emerald Hammer", 7, "1961"),
        emerald_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:emerald_excavator", ChatColor.GREEN + "Emerald Excavator", 7, "1961"),

        amethyst_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:amethyst_hammer", ChatColor.LIGHT_PURPLE + "Amethyst Hammer", 8, "2561"),
        amethyst_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:amethyst_excavator", ChatColor.LIGHT_PURPLE + "Amethyst Excavator", 8, "2561"),

        ruby_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:ruby_hammer", ChatColor.RED + "Ruby Hammer", 9, "3061"),
        ruby_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:ruby_excavator", ChatColor.RED + "Ruby Excavator", 9, "3061"),

        saphir_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:saphir_hammer", ChatColor.BLUE + "Saphir Hammer", 10, "4061"),
        saphir_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:saphir_excavator", ChatColor.BLUE + "Saphir Excavator", 10, "4061"),

        onyx_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:onyx_hammer", ChatColor.DARK_GRAY + "Onyx Hammer", 11, "6061"),
        onyx_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:onyx_excavator", ChatColor.DARK_GRAY + "Onyx Excavator", 11, "6061"),

        meteorite_hammer (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:meteorite_hammer", ChatColor.GOLD + "Meteorite Hammer", 12, "8061"),
        meteorite_excavator (newItems.type.TOOL, Material.DIAMOND_SHOVEL, "survie:meteorite_excavator", ChatColor.GOLD + "Meteorite Excavator", 12, "8061"),

        // OTHER
        spawner_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:spawner_pickaxe", ChatColor.GOLD + "Spawner Pickaxe", 13, "2"),
        bedrock_pickaxe (newItems.type.TOOL, Material.DIAMOND_PICKAXE, "survie:bedrock_pickaxe", ChatColor.DARK_GRAY + "Bedrock Pickaxe", 14, "16"),

        backpack (newItems.type.ITEM, Material.WOODEN_HOE, "survie:backpack", ChatColor.YELLOW + "Backpack", 1, null),
        big_backpack (newItems.type.ITEM, Material.WOODEN_HOE, "survie:big_backpack", ChatColor.YELLOW + "Big Backpack", 2, null),
        end_backpack (newItems.type.ITEM, Material.WOODEN_HOE, "survie:end_backpack", ChatColor.AQUA + "End Backpack", 3, null);

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

        if(item.type == type.ARMOR)
        {
            String[] datas = item.data.split(" ");
            String[] rgb = datas[1].split(",");

            ((LeatherArmorMeta)meta).setColor(Color.fromRGB(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2])));

            EquipmentSlot slot = null;

            switch (item.baseItem)
            {
                case LEATHER_HELMET:
                    slot = EquipmentSlot.HEAD;
                    break;
                case LEATHER_CHESTPLATE:
                    slot = EquipmentSlot.CHEST;
                    break;
                case LEATHER_LEGGINGS:
                    slot = EquipmentSlot.LEGS;
                    break;
                case LEATHER_BOOTS:
                    slot = EquipmentSlot.FEET;
                    break;
            }

            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", Double.valueOf(datas[2]), AttributeModifier.Operation.ADD_NUMBER, slot));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", Double.valueOf(datas[3]), AttributeModifier.Operation.ADD_NUMBER, slot));

            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.WHITE + "Durability: " + datas[0] + "/" + datas[0]);
            meta.setLore(lore);
        }

        if(item.type == type.TOOL)
        {
            String[] datas = item.data.split(" ");
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.WHITE + "Durability: " + datas[0] + "/" + datas[0]);
            meta.setLore(lore);
        }

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
