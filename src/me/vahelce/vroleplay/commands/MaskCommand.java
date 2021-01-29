package me.vahelce.vroleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.suchdifferent.data.MaskedPlayerData;
import me.temaflux.utils.Utils;
import me.vahelce.vroleplay.VRoleplay;

public class MaskCommand implements CommandExecutor {
    private String enabledMessage = null;
    private String disabledMessage = null;
    private String nopermissions = null;
    private String notplayer = null;
	private VRoleplay plugin;
    
    public MaskCommand(VRoleplay instance) {
    	this.plugin = instance;
        FileConfiguration configuration = plugin.getConfig();
        enabledMessage = configuration.getString("message.commands.mask.activate", "&eMask enabled");
        disabledMessage = configuration.getString("message.commands.mask.deactivate", "&6Mask disabled");
        nopermissions = configuration.getString("message.general.nopermissions", "&cIncorrect use of the command. Right: /todo message*action");
        notplayer = configuration.getString("message.general.notplayer", "null");
    }
    
    public void loadConfigurations() {
        FileConfiguration configuration = plugin.getConfig();
        enabledMessage = configuration.getString("message.commands.mask.activate", "null");
        disabledMessage = configuration.getString("message.commands.mask.deactivate", "null");
        nopermissions = configuration.getString("message.general.nopermissions", "null");
        notplayer = configuration.getString("message.general.notplayer", "null");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("vroleplay.mask")) {
                if (MaskedPlayerData.getInstance().isMasked(player)) {
                    MaskedPlayerData.getInstance().removeMask(player);
                    Utils.sendMessage(sender, disabledMessage);
                } else {
                    MaskedPlayerData.getInstance().addMask(player);
                    Utils.sendMessage(sender, enabledMessage);
                }
                return true;
            }
            Utils.sendMessage(sender, nopermissions);
            return false;
        }
        Utils.sendMessage(sender, notplayer);
        return false;
    }
}
