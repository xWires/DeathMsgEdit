package xyz.tangledwires.deathmsgedit;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import xyz.tangledwires.deathmsgedit.events.ChangeDeathMessage;

public class DeathMsgEditPlugin extends JavaPlugin {
    public boolean placeholderApiEnabled = false;
    @Override
    public void onEnable() {
        int pluginId = 21523;
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);

        this.saveDefaultConfig();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! Enabling placeholders.");
            placeholderApiEnabled = true;
        }
        else {
            getLogger().warning("PlaceholderAPI not found! Placeholders will not work in death messages!");
        }
        getServer().getPluginManager().registerEvents(new ChangeDeathMessage(), this);
        getLogger().info("Enabled DeathMsgEdit");
    }
    @Override
    public void onDisable() {
        getLogger().info("Disabled DeathMsgEdit");
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