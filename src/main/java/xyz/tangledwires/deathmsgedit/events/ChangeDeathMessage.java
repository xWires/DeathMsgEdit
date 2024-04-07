package xyz.tangledwires.deathmsgedit.events;

import java.util.List;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.clip.placeholderapi.PlaceholderAPI;

import net.md_5.bungee.api.ChatColor;

import xyz.tangledwires.deathmsgedit.DeathMsgEditPlugin;

public class ChangeDeathMessage implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        DeathMsgEditPlugin plugin = DeathMsgEditPlugin.getPlugin(DeathMsgEditPlugin.class);
        FileConfiguration config = plugin.getConfig();
        List<?> deathMessages = config.getList("messages." + event.getEntity().getLastDamageCause().getCause().toString());
        if (deathMessages == null) {
            return;
        }
        if (deathMessages.size() > 0) {
            Random random = new Random();
            String message = deathMessages.get(random.nextInt(deathMessages.size())).toString();
            message = ChatColor.translateAlternateColorCodes('&', message);
            message = message.replace("{victim}", event.getEntity().getName());
            if (getKiller(event) != null) {
                message = message.replace("{attacker}", getKiller(event).getName());
            }
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (plugin.placeholderApiEnabled) {
                message = PlaceholderAPI.setPlaceholders(event.getEntity(), message);
            }
            event.setDeathMessage(message);
        }
    }
    public static Entity getKiller(EntityDeathEvent event) {
        EntityDamageEvent entityDamageEvent = event.getEntity().getLastDamageCause();
        if ((entityDamageEvent != null) && !entityDamageEvent.isCancelled() && (entityDamageEvent instanceof EntityDamageByEntityEvent)) {
            Entity damager = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
        
            if (damager instanceof Projectile) {
                LivingEntity shooter = (LivingEntity) ((Projectile) damager).getShooter();
                if (shooter != null) {
                    return shooter;
                }
            }
        
            return damager;
        }
        return null;
    }
}
