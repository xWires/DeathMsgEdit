package xyz.tangledwires.deathmsgedit;

// import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
// import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import xyz.tangledwires.deathmsgedit.events.ChangeDeathMessage;

public class DeathMsgEditPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        /* 
        FileConfiguration config = this.getConfig();
        ArrayList<String> blockExplosionDefaults = new ArrayList<String>();
        blockExplosionDefaults.add("{victim} blew up");
        config.set("messages.BLOCK_EXPLOSION", blockExplosionDefaults);

        ArrayList<String> contactDefaults = new ArrayList<String>();
        contactDefaults.add("{victim} was pricked to death");
        config.set("messages.CONTACT", contactDefaults);

        ArrayList<String> crammingDefaults = new ArrayList<String>();
        crammingDefaults.add("{victim} was squished too much");
        config.set("messages.CRAMMING", crammingDefaults);

        ArrayList<String> dragonBreathDefaults = new ArrayList<String>();
        dragonBreathDefaults.add("{victim} was roasted in dragon's breath");
        config.set("messages.DRAGON_BREATH", dragonBreathDefaults);

        config.options().copyDefaults(true);
        */
        
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ChangeDeathMessage(), this);
        getLogger().info("Hello, SpigotMC!");
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deathmsgedit")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    this.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}