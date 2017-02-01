package mafia.factory;

import mafia.game.Game;
import mafia.game.Player;
import mafia.game.Role;

public class PlayerFactory {
	
	public Player createPlayer(String playerName, Role role, Game game) {
		return new Player(playerName, role, game);
	}
}
