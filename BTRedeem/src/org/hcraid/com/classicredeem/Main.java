package org.hcraid.com.classicredeem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public static Redeem redeem;
	private String directory;
	
	public void onEnable(){
		directory = getDataFolder().getAbsolutePath() + File.separator;
		
		File f = new File(directory);
		
		if(!f.exists()){
			f.mkdir();
		}
		
		loadRedeem();
		
		getServer().getPluginCommand("redeem").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("kit").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("setredeem").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("setkit").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("delkit").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("delredeem").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("rlist").setExecutor(new RedeemCommand());
		getServer().getPluginCommand("resetredeem").setExecutor(new RedeemCommand());
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void firstTime(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		if(!p.hasPlayedBefore()){
			
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			
			//sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			//sword.addEnchantment(Enchantment.DURABILITY, 1);
			
			p.setItemInHand(sword);
			
			p.getInventory().addItem(new ItemStack(Material.IRON_SPADE));
			p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
			p.getInventory().addItem(new ItemStack(Material.IRON_AXE));
			p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
			p.getInventory().addItem(new ItemStack(Material.LOG, 32));
			p.getInventory().addItem(new ItemStack(Material.BOW, 1));
			p.getInventory().addItem(new ItemStack(Material.ARROW, 32));
			
			for(int i = 0; i < 3; i++)
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
			
		}
		
	}
	
	private void loadRedeem() {
		
	      try
	      {
	    	  if(!new File(directory + "Redeem.ser").exists()){
	    		  redeem = new Redeem();
	    	  }
	    	  
	         FileInputStream fileIn = new FileInputStream(directory + "Redeem.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         log("Loaded!");
	         redeem = (Redeem) in.readObject();
	         in.close();
	         fileIn.close();
	         //Main.log("Succesfully loaded 'Scheduler' file.");
	         
	         if(redeem == null){
	        	 log("Redeem is null! Making new.");
	    		  redeem = new Redeem();
	         }
	         
	         //return data;
	      }catch(Exception i){
	    	  i.printStackTrace();
	         //return null;
	      }
	}

	public void onDisable(){
		saveRedeem();
	}

	private void saveRedeem() {
		      try
		      	{
		         FileOutputStream fileOut =
		         new FileOutputStream(directory + "Redeem.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(redeem);
		         out.close();
		         fileOut.close();
		         log("Saved redeem file.");
		      }catch(IOException i){
		    	  i.printStackTrace();
		      }

	}

	private void log(String string) {
		System.out.println("[Log] " + string);
	}

}
