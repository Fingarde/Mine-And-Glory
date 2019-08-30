package fr.fingarde.mineandglory.utils;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Backpack
{
    private int size;
    private ItemStack[] items;
    UUID id;

    public int getSize()
    {
        return size;
    }

    public ItemStack[] getContents()
    {
        return items;
    }

    public void setContents(ItemStack[] items)
    {
        this.items = items;
    }

    public UUID getId()
    {
        return id;
    }

   public Backpack(UUID id, int size)
   {
       this.id = id;
       this.size = size;
   }
}
