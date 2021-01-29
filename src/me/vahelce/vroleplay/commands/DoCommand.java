package me.vahelce.vroleplay.commands;

import me.temaflux.utils.Utils;
import me.vahelce.vroleplay.VRoleplay;
import me.vahelce.vroleplay.utils.LocationUtils;
import me.vahelce.vroleplay.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DoCommand implements CommandExecutor {
	private String rawMessage = null;
	private String errorMessage = null;
	private String nopermissions = null;
	private String notplayer = null;
	private int range = 0;
	private VRoleplay plugin;
	private LocationUtils locationUtil;

	public DoCommand(VRoleplay instance, LocationUtils locationUtil) {
		this.plugin = instance;
		this.locationUtil = locationUtil;
		loadConfiguration();
	}

	private void loadConfiguration() {
		FileConfiguration configuration = plugin.getConfig();
		this.rawMessage = configuration.getString("message.commands.do", "&7*%action%* (%player%) ");
		this.errorMessage = configuration.getString("message.general.error",
				"&4An error occurred when using the command. Use /vroleplay help for help.");
		this.nopermissions = configuration.getString("message.general.nopermissions", "&4You have no right to do this");
		this.notplayer = configuration.getString("message.general.notplayer", "&4You are not a player");
		this.range = configuration.getInt("range", 5);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("vroleplay.do")) {
				if (args.length > 0) {
					String message = rawMessage.replace("%player%", player.getName()).replace("%action%",
							StringUtils.concatMessage(args));
					locationUtil.getPlayersAt(player.getLocation(), range).forEach(p -> Utils.sendMessage(p, message));
					return true;
				} else {
					Utils.sendMessage(sender, errorMessage);
					return false;
				}
			} else {
				Utils.sendMessage(sender, nopermissions);
				return false;
			}
		} else {
			Utils.sendMessage(sender, notplayer);
			return false;
		}

	}
}
