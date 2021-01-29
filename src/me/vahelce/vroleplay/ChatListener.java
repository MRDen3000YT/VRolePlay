package me.vahelce.vroleplay;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.suchdifferent.data.MaskedPlayerData;

public class ChatListener implements Listener {
    private String maskName = null;
	private VRoleplay plugin;
    
    public ChatListener(VRoleplay instance) {
		this.plugin = instance;
		maskName = plugin.getConfig().getString("message.commands.mask.name", "&eUnknown&r");
	}

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
    	Player player = event.getPlayer();
        if ((MaskedPlayerData.getInstance().isMasked(player))) {
            synchronized (event) {
            	event.setCancelled(true);
                String oldName = player.getCustomName();
                if (oldName == null) oldName = event.getPlayer().getName();
                player.setCustomName(maskName);
                String finalOldName = oldName;
                player.chat(event.getMessage());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setCustomName(finalOldName);
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }


}
