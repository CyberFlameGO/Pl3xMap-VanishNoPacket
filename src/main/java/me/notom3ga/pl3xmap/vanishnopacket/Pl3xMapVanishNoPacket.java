package me.notom3ga.pl3xmap.vanishnopacket;

import net.pl3x.map.api.Pl3xMapProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.VanishPlugin;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class Pl3xMapVanishNoPacket extends JavaPlugin {
    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("VanishNoPacket")) {
            getLogger().severe("Failed to find plugin VanishNoPacket!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!getServer().getPluginManager().isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Failed to find plugin Pl3xMap!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void vanish(VanishStatusChangeEvent event) {
                Pl3xMapProvider.get().playerManager()
                        .hidden(event.getPlayer().getUniqueId(), event.isVanishing());
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void join(PlayerJoinEvent event) {
                VanishPlugin vanish = (VanishPlugin) Bukkit.getPluginManager().getPlugin("VanishNoPacket");
                Pl3xMapProvider.get().playerManager()
                        .hidden(event.getPlayer().getUniqueId(), vanish.getManager().isVanished(event.getPlayer()));
            }
        }, this);
    }
}
