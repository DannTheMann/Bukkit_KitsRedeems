package org.hcraid.com.classicredeem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RedeemKit implements Serializable{
	
	private static final long serialVersionUID = -6245173139259098429L;
	private String creatorId;
	private int levels;
	private int money;
	private ArrayList<SavedItem> items = new ArrayList<SavedItem>();
	private ArrayList<String> markedPlayers = new ArrayList<String>();
	
	public RedeemKit(String id, ItemStack[] items, int money, int level){
		this.creatorId = id;
		this.money = money;
		this.levels = level;
		
		for(ItemStack i : items){
			
			if(i != null){
				this.items.add(new SavedItem(i));
			}
			
		}
	}
	
	public String toString(){
		return "Created by " + Bukkit.getOfflinePlayer(UUID.fromString(creatorId)).getName() + ", stores $" + money
				+ " and " + levels + " levels of experience. Here are the following items.";
	}
	
	public String handPlayerRedeem(Player p){
		
		String id = p.getUniqueId().toString();
		
		if(markedPlayers.contains(id)){
			return ChatColor.RED + "You've already used this redeem!";
		}
		
		for(Entity e : p.getNearbyEntities(30, 30, 30)){
			
			if(e instanceof Player && e != p){
				return ChatColor.RED + "You can't redeem with players nearby!";
			}
			
		}
		
		markedPlayers.add(id);
		
		// add money
		p.setLevel(p.getLevel()+levels);
		
		for(SavedItem si : items){
			if(inventoryIsEmpty(p)){
				p.getInventory().addItem(si.toBukkitItemStack());
			}else{
				p.getWorld().dropItem(p.getLocation(), si.toBukkitItemStack());
			}
		}
		
		return ChatColor.GREEN + "You've successfully redeemed this kit. ";
		
	}

	private boolean inventoryIsEmpty(Player p) {
		
		for(ItemStack i : p.getInventory()){
			
			if(i == null){
				return true;
			}
			
		}
		
		return false;
		
	}

	public void reset(String pid) {
		
		markedPlayers.remove(pid);
		
	}

}
