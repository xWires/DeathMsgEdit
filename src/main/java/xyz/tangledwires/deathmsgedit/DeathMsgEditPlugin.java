package xyz.tangledwires.deathmsgedit;

import org.bstats.bukkit.Metrics;

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
        int pluginId = 21523;
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);

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