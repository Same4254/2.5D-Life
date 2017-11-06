package Main;

import Input.KeyManager;
import World.World;

public class Handler {
	private Game game;
	private World world;
	private KeyManager keyManager;
	
	public Handler(Game game) {
		this.game = game;
		
		keyManager = game.getKeyManager();
	}

	public void init() {
		world = new World(this);
		world.init();
	}
	
	public Game getGame() { return game; }
	public World getWorld() { return world; }
	public KeyManager getKeyManager() { return keyManager; }
	public int getWidth() { return game.getWindow().getWidth(); }
	public int getHeight() { return game.getWindow().getHeight(); }
}