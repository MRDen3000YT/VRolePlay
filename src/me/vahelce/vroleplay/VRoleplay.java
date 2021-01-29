package me.vahelce.vroleplay;

import me.temaflux.config.Config;
import me.temaflux.utils.Utils;
import me.vahelce.vroleplay.commands.*;
import me.vahelce.vroleplay.utils.LocationUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class VRoleplay extends JavaPlugin implements CommandExecutor {
	private Config config = null;

	@Override
	public void onEnable() {
		this.config = new Config("config.yml", this);
		this.config.load();
		registerCommands();
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
	}

	private void registerCommands() {
		LocationUtils locationUtil = new LocationUtils();
		getServer().getPluginCommand("try").setExecutor(new TryCommand());
		getServer().getPluginCommand("me").setExecutor(new MeCommand());
		getServer().getPluginCommand("do").setExecutor(new DoCommand(this, locationUtil));
		getServer().getPluginCommand("b").setExecutor(new OocChatCommand());
		getServer().getPluginCommand("rpmask").setExecutor(new MaskCommand(this));
		getServer().getPluginCommand("todo").setExecutor(new TodoCommand());
		getServer().getPluginCommand("vroleplay").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			Utils.sendMessage(sender, "§6V§eRolePlay §rv. " + this.getDescription().getVersion(),
					"§eEnter /vroleplay §fhelp §7for help.");
		} else
			Utils.sendMessage(sender, this.getConfig().getStringList("message.help"));
		return true;
	}

	@Override
	public FileConfiguration getConfig() {
		return this.config.getConfig();
	}

	public Config getConfiguration() {
		return config;
	}
}
