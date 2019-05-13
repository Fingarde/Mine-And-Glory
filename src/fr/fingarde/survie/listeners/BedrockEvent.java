package fr.fingarde.survie.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fingarde.survie.objects.newBlocks;

public class BedrockEvent extends Listeners {
	
	private static final int miningCoefficient = 3;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		//Check if the player left click on a block
		if(event.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}
		
		Block block = event.getClickedBlock();
		
		//Check if the clicked block is a bedrock block
		if(block.getBlockData().getMaterial() != Material.BEDROCK) {
			return;
		}
		
		//Check if the player holds a bedrock pickaxe
		ItemStack currentItem = event.getItem();
		if(currentItem.getItemMeta().getLocalizedName() != "survie:bedrock_pickaxe") {
			return;
		}
		
		newBlocks.setNewBlock(block, newBlocks.blocks.breakable_bedrock);
		Player player = event.getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1, BedrockEvent.miningCoefficient));		
	}
}
