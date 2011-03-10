package net.gamerservices.npcx;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.event.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.MobType;
import java.util.logging.Level;
import org.bukkit.event.Event.Type;
import java.util.logging.Logger;
import redecouverte.npcspawner.BasicHumanNpc;
import redecouverte.npcspawner.BasicHumanNpcList;
import redecouverte.npcspawner.NpcEntityTargetEvent;
import redecouverte.npcspawner.NpcEntityTargetEvent.NpcTargetReason;
import redecouverte.npcspawner.NpcSpawner;
import org.bukkit.event.Event.Priority;

public class npcx extends JavaPlugin {

	private static final Logger logger = Logger.getLogger("Minecraft");
	private npcxEListener mEntityListener;
	public BasicHumanNpcList npclist = new BasicHumanNpcList();
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		 try {
	            PluginDescriptionFile pdfFile = this.getDescription();
	            logger.log(Level.INFO, pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled.");
	        } catch (Exception e) {
	            logger.log(Level.WARNING, "npcx : error: " + e.getMessage() + e.getStackTrace().toString());
	            e.printStackTrace();
	            return;
	        }
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		 try {
	            PluginManager pm = getServer().getPluginManager();

	            mEntityListener = new npcxEListener(this);
	            pm.registerEvent(Type.ENTITY_TARGET, mEntityListener, Priority.Normal, this);
	            pm.registerEvent(Type.ENTITY_DAMAGED, mEntityListener, Priority.Normal, this);

	            //this.HumanNPCList = new BasicHumanNpcList();

	            PluginDescriptionFile pdfFile = this.getDescription();
	            logger.log(Level.INFO, pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled.");
	        } catch (Exception e) {
	            logger.log(Level.WARNING, "npcx : error: " + e.getMessage() + e.getStackTrace().toString());
	            e.printStackTrace();
	            return;
	        }
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        try {
        	logger.log(Level.WARNING, "npcx : " + command.getName().toLowerCase());
	           
            if (!command.getName().toLowerCase().equals("npcx")) {
            	
                return false;
            }
            if (!(sender instanceof Player)) {

                return false;
            }

            if (args.length < 1) {
                return false;
            }

            String subCommand = args[0].toLowerCase();
        	//debug: logger.log(Level.WARNING, "npcx : " + command.getName().toLowerCase() + "(" + subCommand + ")");

            Player player = (Player) sender;
            Location l = player.getLocation();
            
            if (subCommand.equals("spawngroup"))
            {
            	
            	// Overview:
            	// A spawngroup is like a container. It contains many npcs and any one of them could spawn randomly.
            	// If you placed just one npc in the group only one npc would spawn. This allows you to create 'rare' npcs
            	
            	// Spawngroups need to be assigned to a location with 'spawngroup place' Once assigned that group
            	// will spawn in that location and remain stationary
            	
            	// If a path is assigned to the spawn group, the npc will follow the path continuously after spawning 
            	// at the location of 'spawngroup place'
            	
            	// todo: functionality
            	// creates a new spawngroup with name
            	// adds an npc to a spawngroup with a chance
            	// makes the spawngroup spawn at your location
            	// assigns a path to the spawngroup
            	
            	if (args.length < 3) {
            		player.sendMessage("Insufficient arguments /npcx spawngroup create|delete spawngroupname");
                	player.sendMessage("Insufficient arguments /npcx spawngroup add npcname");
                	player.sendMessage("Insufficient arguments /npcx spawngroup place spawngroupname");
                	player.sendMessage("Insufficient arguments /npcx spawngroup pathgroup pathgroupname");
            		return false;
                }
            }
            
            
            
            if (subCommand.equals("npc"))
            {
            	// Overview:
            	// NPCs are just that, definitions of the mob you want to appear in game. There can be multiple of the same
            	// npc in many spawngroups, for example if you wanted a custom npc called 'Thief' to spawn in several locations
            	// you would put the npc into many spawn groups
            	
            	// In the future these npcs will support npctypes which determines how the npc will respond to right click, attack, etc events
            	// ie for: bankers, normal npcs, merchants etc
            	
            	// Also loottables will be assignable
            	
            	// todo: functionality
            	// creates a new npc with name       
            	
            	
            	if (args.length < 3) {
            		// todo: need to implement npc types here ie: 0 = default 1 = banker 2 = merchant
            		// todo: need to implement '/npcx npc edit' here
                	player.sendMessage("Insufficient arguments /npcx npc create|delete name");

                	// todo needs to force the player to provide a search term to not spam them with lots of results in the event of a huge npc list
                	player.sendMessage("Insufficient arguments /npcx npc list all");
                	
                	// spawns the npc temporarily at your current spot for testing
                	player.sendMessage("Insufficient arguments /npcx npc test name");
                	
                    return false;
                }
            	
            	if (args[1].equals("create")) {
	            		player.sendMessage("Creating new NPC: " + args[2]);
	                    // temporary
	                    BasicHumanNpc hnpc = NpcSpawner.SpawnBasicHumanNpc(args[2], args[2], player.getWorld(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
	                    this.npclist.put(args[2], hnpc);
	                    return true;
                }

            }
            
            if (subCommand.equals("pathgroup"))
            {
            	// Overview:
            	// Path groups are containers of path locations assigned to SpawnGroups. They are used to determine the route of an npc after it spawns
            	// When a path group is created, it is given several orderIDs beginning with 1, the npc will move from 
            	// point to point in the order of the orderID
            	
            	// In the future this will allow different types of pathgroups which will effect the way the npc traverses the order
            	// ie random, byID and circular
            	
            	// todo: functionality
            	// creates a new path group
            	// assigns a location to a path group
            	if (args.length < 3) {
                	player.sendMessage("Insufficient arguments /npcx pathgroup create|delete name");
                	player.sendMessage("Insufficient arguments /npcx pathgroup add pathgroupname orderinteger");
                    return false;
                }
            }

            
            /*
             * 
             * For reference: NPC Spawner Code (thanks to the npc lib)

            // create npc-id npc-name
            if (subCommand.equals("create")) {
                if (args.length < 3) {
                	player.sendMessage("Insufficient arguments /npcx create id name ");
                    return false;
                }

                if (this.npclist.get(args[1]) != null) {
                    player.sendMessage("This npc-id is already in use.");
                    return true;
                }

            	player.sendMessage("Creating npc....");

                BasicHumanNpc hnpc = NpcSpawner.SpawnBasicHumanNpc(args[1], args[2], player.getWorld(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                this.npclist.put(args[1], hnpc);
                
                ItemStack is = new ItemStack(Material.BOOKSHELF);
                is.setAmount(1);
                hnpc.getBukkitEntity().setItemInHand(is);


            // attackme npc-id
            } else if (subCommand.equals("attackme")) {

                if (args.length < 2) {
                    return false;
                }

                BasicHumanNpc npc = this.npclist.get(args[1]);
                if (npc != null) {
                    npc.attackLivingEntity(player);
                    return true;
                }

            // move npc-id
            } else if (subCommand.equals("move")) {
                if (args.length < 2) {
                    return false;
                }

                BasicHumanNpc npc = this.npclist.get(args[1]);
                if (npc != null) {
                    npc.moveTo(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                    return true;
                }

            // spawnpig
            } else if (subCommand.equals("spawnpig")) {
                NpcSpawner.SpawnMob(MobType.PIG, player.getWorld(), l.getX(), l.getY(), l.getZ());
            }
            
            */


        } catch (Exception e) {
            sender.sendMessage("An error occured.");
            logger.log(Level.WARNING, "npcx: error: " + e.getMessage() + e.getStackTrace().toString());
            e.printStackTrace();
            return true;
        }

        return true;
    }

}