package me.suchdifferent.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class MaskedPlayerData {
	
	private List<Player> maskedPlayers = new ArrayList<Player>();
	public static MaskedPlayerData instance;

	public MaskedPlayerData(List<Player> maskedPlayers) {
		this.maskedPlayers = maskedPlayers;
		instance = this;
	}
	
	public static MaskedPlayerData getInstance() {
		if(instance == null) {
			instance = new MaskedPlayerData(null);
		}
		return instance;
	}
	
    public boolean isMasked(Player player) {
        return maskedPlayers.contains(player);
    }

    public void addMask(Player player) {
        maskedPlayers.add(player);
    }

    public void removeMask(Player player) {
        maskedPlayers.remove(player);
    }

}
