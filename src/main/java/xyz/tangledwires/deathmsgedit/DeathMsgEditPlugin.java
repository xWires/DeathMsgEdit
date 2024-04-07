package xyz.tangledwires.deathmsgedit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import xyz.tangledwires.deathmsgedit.events.ChangeDeathMessage;
import xyz.tangledwires.deathmsgedit.events.UpdateNotifier;

public class DeathMsgEditPlugin extends JavaPlugin {
    public boolean placeholderApiEnabled = false;
    public String version = getDescription().getVersion();
	public String latestVersion;
	public boolean isOutdated = false;
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
        getServer().getPluginManager().registerEvents(new UpdateNotifier(), this);
        /*
		 * This checks whether the plugin is up to date.
		 * The URL below returns the latest build number from Jenkins.
		 * 
		 * It gets the latest build number and compares it with the version string of this instance of DeathMsgEdit.
		 */
		HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ci.tangledwires.xyz/job/DeathMsgEdit/lastSuccessfulBuild/buildNumber"))
                .GET()
                .build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			int newestVersion = Integer.parseInt(response.body());
			latestVersion = Integer.toString(newestVersion);
			if (newestVersion > Integer.parseInt(getDescription().getVersion())) {
				isOutdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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