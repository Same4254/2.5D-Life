package Main;

import Input.KeyManager;
import Input.MouseManager;
import World.World;

public class Handler {
	private Game game;
	private World world;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public Handler(Game game) {
		this.game = game;
		
		keyManager = new KeyManager();
	}

	public void init() {
		world = new World(this);
		world.init();
		
		mouseManager = new MouseManager(this);
	}
	
	public Game getGame() { return game; }
	public World getWorld() { return world; }
	public KeyManager getKeyManager() { return keyManager; }
	public MouseManager getMouseManager() { return mouseManager; }
	public int getWidth() { return game.getWindow().getWidth(); }
	public int getHeight() { return game.getWindow().getHeight(); }
}