package org.hcraid.com.classicredeem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit implements Serializable{
	
	private static final long serialVersionUID = 3718249783630137552L;
	private int secondsDelay;
	private int levels;
	private int money;
	private HashMap<String, Long> usedPlayers = new HashMap<String, Long>();
	private ArrayList<SavedItem> items = new ArrayList<SavedItem>();

	public Kit(ItemStack[] items, int delay){
		
		secondsDelay = delay;
		
		for(ItemStack i : items){
			
			if(i != null){
				this.items.add(new SavedItem(i));
			}
			
		}
	}
	
	public String toString(){
		return "Stores $" + money
				+ " and " + levels + " levels of experience. Here are the following items.";
	}
	
	public String handPlayerKit(Player p){
		
		String id = p.getUniqueId().toString();
		
		if(usedPlayers.containsKey(id)){
			if(usedPlayers.get(id) > System.currentTimeMillis() && !p.hasPermission("Kit.Cooldown.Bypass")){
				return ChatColor.RED + "You've already used this kit, you can next use this kit in " + getNextUseDate(id);
			}
		}
		
		usedPlayers.put(id, System.currentTimeMillis() + (secondsDelay * 1000));
		
		// add money
		p.setLevel(p.getLevel()+levels);
		
		for(SavedItem si : items){
			if(inventoryIsEmpty(p) && si != null){
				Material m = si.getItemType();
				
				if(isHelmet(m)){
					if(p.getInventory().getHelmet() == null){
						p.getInventory().setHelmet(si.toBukkitItemStack());
					}else{
						p.getInventory().addItem(si.toBukkitItemStack());
					}
				}else if(isChestplate(m)){
					if(p.getInventory().getChestplate() == null){
						p.getInventory().setChestplate(si.toBukkitItemStack());
					}else{
						p.getInventory().addItem(si.toBukkitItemStack());
					}
				}else if(isLeggings(m)){
					if(p.getInventory().getLeggings() == null){
						p.getInventory().setLeggings(si.toBukkitItemStack());
					}else{
						p.getInventory().addItem(si.toBukkitItemStack());
					}
				}else if(isBoots(m)){
					if(p.getInventory().getBoots() == null){
						p.getInventory().setBoots(si.toBukkitItemStack());
					}else{
						p.getInventory().addItem(si.toBukkitItemStack());
					}
				}else{				
					p.getInventory().addItem(si.toBukkitItemStack());
				}
			}else{
				
				Material m = si.getItemType();
				
				if(isHelmet(m)){
					if(p.getInventory().getHelmet() == null){
						p.getInventory().setHelmet(si.toBukkitItemStack());
					}else{
						p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
					}
				}else if(isChestplate(m)){
					if(p.getInventory().getChestplate() == null){
						p.getInventory().setChestplate(si.toBukkitItemStack());
					}else{
						p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
					}
				}else if(isLeggings(m)){
					if(p.getInventory().getLeggings() == null){
						p.getInventory().setLeggings(si.toBukkitItemStack());
					}else{
						p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
					}
				}else if(isBoots(m)){
					if(p.getInventory().getBoots() == null){
						p.getInventory().setBoots(si.toBukkitItemStack());
					}else{
						p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
					}
				}else{	
				
					p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
				
				}
			}
		}
		
		
		
		return ChatColor.GREEN + "You've successfully redeemed this kit. ";
		
	}
	
	private static Material[] h = {Material.DIAMOND_HELMET, Material.IRON_HELMET
			, Material.GOLD_HELMET, Material.LEATHER_HELMET
	};
	
	private boolean isHelmet(Material m){
	 
		if(m == null){
			Bukkit.broadcastMessage("Material is null");
		}
		if(h == null){
			Bukkit.broadcastMessage("Array is null");
		}
		
	 	for(Material he : h){
	 		if(m == he)
	 			return true;
	 	}
	 	return false;
		
	}
	
	private static Material[] c = {Material.DIAMOND_CHESTPLATE, Material.IRON_CHESTPLATE
			, Material.GOLD_CHESTPLATE, Material.LEATHER_CHESTPLATE};
	
	private boolean isChestplate(Material m){
		 
		 	for(Material he : c){
		 		if(m == he)
		 			return true;
		 	}
		 	return false;
		
	}
	
	private static Material[] l = {Material.DIAMOND_LEGGINGS, Material.IRON_LEGGINGS
			, Material.GOLD_LEGGINGS, Material.LEATHER_LEGGINGS};
	
	
	
	private boolean isLeggings(Material m){
		 
		 	for(Material he : l){
		 		if(m == he)
		 			return true;
		 	}
		 	return false;
		
	}
	
	private static Material[] b = {Material.DIAMOND_BOOTS, Material.IRON_BOOTS
			, Material.GOLD_BOOTS, Material.LEATHER_BOOTS};
	
	
	
	private boolean isBoots(Material m){
		 
		 	for(Material he : b){
		 		if(m == he)
		 			return true;
		 	}
		 	return false;
		
	}

	private boolean inventoryIsEmpty(Player p) {
		
		for(ItemStack i : p.getInventory()){
			
			if(i == null){
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public String getNextUseDate(String id){
		
		if(!usedPlayers.containsKey(id)){
			return ChatColor.GREEN + "Now!";
		}
		
		long dateStart = System.currentTimeMillis();
		long dateStop =  usedPlayers.get(id);
		
		if(dateStop < System.currentTimeMillis()){
			return ChatColor.GREEN + "Now!";
		}
		
		int different = (int) ((dateStop - dateStart) / 1000);
		
		String rem = "";
		
		if(Slim.slimDays(different) > 0){
			rem += ChatColor.GOLD + "" + Slim.slimDays(different) + " Days, ";
		}
		if(Slim.slimHours(different) > 0){
			rem += ChatColor.YELLOW + "" + Slim.slimHours(different) + " Hours, ";
		}
		if(Slim.slimMinutes(different) > 0){
			rem += ChatColor.RED + "" + Slim.slimMinutes(different) + " Minutes, ";
		}
		rem += ChatColor.DARK_RED + "" + Slim.slimSeconds(different) + " Seconds.";
		
		
		// Get msec from each, and subtract.
		return rem;
		
	}

	public String getReuseTime() {
		long dateStart = System.currentTimeMillis();
		long dateStop =  System.currentTimeMillis() + (secondsDelay * 1000);

		int different = (int) ((dateStop - dateStart) / 1000);
		
		String rem = "";
		
		if(Slim.slimDays(different) > 0){
			rem += ChatColor.GOLD + "" + Slim.slimDays(different) + " Days, ";
		}else if(Slim.slimHours(different) > 0){
			rem += ChatColor.YELLOW + "" + Slim.slimHours(different) + " Hours, ";
		}else if(Slim.slimMinutes(different) > 0){
			rem += ChatColor.RED + "" + Slim.slimMinutes(different) + " Minutes, ";
		}
		rem += ChatColor.DARK_RED + "" + Slim.slimSeconds(different) + " Seconds.";
		
		
		// Get msec from each, and subtract.
		return rem;
	}
	
}
