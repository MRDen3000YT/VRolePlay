package me.vahelce.vroleplay.commands;

import me.temaflux.utils.Utils;
import me.vahelce.vroleplay.VRoleplay;
import me.vahelce.vroleplay.utils.LocationUtils;
import me.vahelce.vroleplay.utils.RandomHelper;
import me.vahelce.vroleplay.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TryCommand implements CommandExecutor {
    private String rawMessage = "null";
    private String success = "null";
    private String fail = "null";
    private String errorMessage = "null";
    private String nopermissions = "null";
    private String notplayer = "null";
    private int range = 0;
    
    public TryCommand() {
        FileConfiguration configuration = VRoleplay.getInstance().getConfig();
        rawMessage = configuration.getString("message.commands.try.message", "null");
        success = configuration.getString("message.commands.try.success", "null");
        fail = configuration.getString("message.commands.try.fail", "null");
        errorMessage = configuration.getString("message.general.error", "null");
        nopermissions = configuration.getString("message.general.nopermissions", "null");
        notplayer = configuration.getString("message.general.notplayer", "null");
        range = configuration.getInt("range", 0);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("vroleplay.try")) {
                if (args.length > 0) {
                    String result = RandomHelper.getRandom(2) == 0 ? success : fail;
                    String message = rawMessage
                            .replace("%player%", player.getName())
                            .replace("%action%", StringUtils.concatMessage(args))
                            .replace("%result%", result);
                    LocationUtils.getPlayersAt(player.getLocation(), range).forEach(p -> Utils.sendMessage(p, message));
                    return true;
                }
                Utils.sendMessage(sender, errorMessage);
                return false;
            }
            Utils.sendMessage(sender, nopermissions);
            return false;
        }
        Utils.sendMessage(sender, notplayer);
        return false;
    }
}
