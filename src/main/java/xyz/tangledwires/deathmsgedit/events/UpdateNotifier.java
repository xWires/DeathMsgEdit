package xyz.tangledwires.deathmsgedit.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.tangledwires.deathmsgedit.DeathMsgEditPlugin;

/**
 * When a player joins, if they are opped and the plugin is outdated, they will recieve a message notifying them of this.
 */
public class UpdateNotifier implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DeathMsgEditPlugin mainClass = DeathMsgEditPlugin.getPlugin(DeathMsgEditPlugin.class);
        if (event.getPlayer().isOp()) {
            if (mainClass.isOutdated) {
                event.getPlayer().sendMessage(ChatColor.GRAY + "--------------------------------------------");
                event.getPlayer().sendMessage(ChatColor.GREEN + "An update is available for DeathMsgEdit!");
                event.getPlayer().sendMessage(ChatColor.GREEN + "You are currently running version " + mainClass.version);
                event.getPlayer().sendMessage(ChatColor.GREEN + "The newest version is " + mainClass.latestVersion);
                TextComponent message = new TextComponent("Click here to download it");
                message.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://ci.tangledwires.xyz/job/DeathMsgEdit/"));
                event.getPlayer().spigot().sendMessage(message);
                event.getPlayer().sendMessage(ChatColor.GRAY + "--------------------------------------------");
            }
        }
    }
}
