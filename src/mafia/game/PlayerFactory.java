package mafia.game;

public class PlayerFactory {
	
	public Player createPlayer(String playerName, Role role, Game game) {
		return new Player(playerName, role, game);
	}
}
