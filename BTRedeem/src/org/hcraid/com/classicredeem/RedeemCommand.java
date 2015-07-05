package org.hcraid.com.classicredeem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class RedeemCommand implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command c, String l,
			String[] args) {
		
		if(c.getName().equalsIgnoreCase("redeem")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(!p.hasPermission("Redeem.use")){
				p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
				return true;
			}
			
			if(args.length == 0){
				p.sendMessage(ChatColor.GRAY + "/redeem <redeemname> - Redeem this specific kit.");
				p.sendMessage(ChatColor.GRAY + "/redeem all - Redeem ALL kits " + ChatColor.RED + " DO NOT DO THIS IN A POPULATED AREA.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("all")){
			
			for(String u : Main.redeem.getRedeems().keySet()){
				
				if(p.hasPermission("Redeem." + u)){
					
					RedeemKit rk = Main.redeem.getRedeems().get(u);
					
					p.sendMessage(rk.handPlayerRedeem(p));
					
				}
				
			}
			
			}else{
				
				String redeemname = args[0];
				
				RedeemKit rk = Main.redeem.getRedeems().get(redeemname);
				
				if(rk == null){
					p.sendMessage(ChatColor.RED + "This redeem does not exist!");
					
					for(String u : Main.redeem.getRedeems().keySet()){
						
						if(p.hasPermission("Redeem." + u)){
							
							
							p.sendMessage("Redeem - " + u);
							
						}
						
					}
					return true;
				}
				
				if(p.hasPermission("redeem." + redeemname)){
					p.sendMessage(rk.handPlayerRedeem(p));
				}else{
					p.sendMessage(ChatColor.RED + "You can't use this redeem, no permission!");
					return true;
				}
				
			}
			
		}else if(c.getName().equalsIgnoreCase("kit")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(args.length == 0){
				
				p.sendMessage(ChatColor.GREEN + " --- Kits you can use --- ");
				
				for(String u : Main.redeem.getKits().keySet()){
					
					if(p.hasPermission("Kit." + u)){
						
						p.sendMessage(ChatColor.GRAY + " - " + u + ", Next Use: " + Main.redeem.getKits().get(u).getNextUseDate(
							id));

						
					}
					
			}
				
			
				
				return true;
			}else{
				
				String kitName = args[0];
				
				if(kitName.equalsIgnoreCase("perms")){
					
					for(PermissionAttachmentInfo perm : p.getEffectivePermissions()){
						p.sendMessage(perm.getPermission() + " - " + perm.getValue());
					}
					
					return true;
				}
				
				Kit k = null;
				
				for(String u : Main.redeem.getKits().keySet()){
					
					if(p.hasPermission("Kit." + u) && kitName.equalsIgnoreCase(u)){
						k = Main.redeem.getKits().get(u);
						break;
					}
					
				}
				
				if(k == null){
					p.sendMessage(ChatColor.RED + " --- Kits not found, listing kits --- ");
					
					for(String u : Main.redeem.getKits().keySet()){
							
							if(p.hasPermission("Kit." + u)){
								
								p.sendMessage(ChatColor.GRAY + " - " + u + ", Next Use: " + Main.redeem.getKits().get(u).getNextUseDate(
										id));
								
							}
							
					}
				}else{
					p.sendMessage(k.handPlayerKit(p));
				}
			}
			
		}else if(c.getName().equalsIgnoreCase("setredeem")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("Redeem.set")){
				
				if(args.length == 0){
					p.sendMessage(ChatColor.RED + "/setredeem <name> - Creates a new redeem with the items in your inventory.");
					return true;
				}
				
				String name = args[0];
				
				Main.redeem.getRedeems().put(name, new RedeemKit(id, p.getInventory().getContents(), 0, 0));
				
				p.sendMessage(ChatColor.GREEN + "Created new redeem for items in your inventory, permission node: redeem." + name);
				
			}
			
		}else if(c.getName().equalsIgnoreCase("listRedeem")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("Redeem.list")){
			
				for(String u : Main.redeem.getRedeems().keySet()){
					
					p.sendMessage(ChatColor.GRAY + " " + u + " - Redeem." + u);
					
				}
				
			}
				
		}else if(c.getName().equalsIgnoreCase("deleteredeem")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("Redeem.delete")){
				
				if(args.length == 0){
					p.sendMessage(ChatColor.RED + "/delredeem <name> - Deletes redeem under this name.");
					return true;
				}
				
				String name = args[0];
				
				Main.redeem.getRedeems().remove(name);
				
				p.sendMessage(ChatColor.GREEN + "Deleted redeem called " + name + ".");
				
			}
			
		}else if(c.getName().equalsIgnoreCase("setkit")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("kit.set")){
				
				if(args.length <= 1){
					p.sendMessage(ChatColor.RED + "/setkit <name> <secondDelay> - Creates a new kit with the items in your inventory" +
							" with a delay in seconds between use.");
					return true;
				}
				
				String name = args[0];
				int secondDelay = Integer.parseInt(args[1]);
				
				Main.redeem.getKits().put(name, new Kit(p.getInventory().getContents(), secondDelay));
				
				p.sendMessage(ChatColor.GREEN + "Created new kit called " + name + " with a second delay of " + secondDelay
						 + " seconds.");
				
			}
			
		}else if(c.getName().equalsIgnoreCase("delkit")){
			
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("kit.del")){
				
				if(args.length <= 1){
					p.sendMessage(ChatColor.RED + "/delkit <name> - Deletes kit.");
					return true;
				}
				
				String name = args[0];
				int secondDelay = Integer.parseInt(args[1]);
				
				Main.redeem.getKits().put(name, new Kit(p.getInventory().getContents(), secondDelay));
				
				p.sendMessage(ChatColor.GREEN + "");
				
			}
			
		}else if(c.getName().equalsIgnoreCase("rlist")){
		
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			
			if(p.hasPermission("redeem.list")){
				
				for(String u : Main.redeem.getKits().keySet()){
					
					p.sendMessage(ChatColor.GRAY + " Kits: " + u);
					
				}
				
				for(String u : Main.redeem.getRedeems().keySet()){
					
					p.sendMessage(ChatColor.GRAY + " Redeem: " + u);
					
				}
				
			}
			
		}else if(c.getName().equalsIgnoreCase("resetredeem")){
			Player p = (Player)s;
			
			String id = p.getUniqueId().toString();
			if(args.length == 0){
				p.sendMessage(ChatColor.RED + "/resetredeem <player> - Reset player redeems.");
				return true;
			}
			
			if(p.hasPermission("redeem.list")){
				
				String name = args[0];
				
				if(Bukkit.getOfflinePlayer(name) != null){
					
					id = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
					
				}
				
				for(String u : Main.redeem.getRedeems().keySet()){
					
					if(p.hasPermission("Redeem." + u)){
						
						Main.redeem.getRedeems().get(u).reset(id);
						
						p.sendMessage("Reset " + u + " for " + name);
						
					}
					
				}
			}
			
			
		}
		return true;
	}
	
	

}
