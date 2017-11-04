package Main;

import org.lwjgl.LWJGLException;

public class NewLifeLauncher {
	public static void main(String[] args) throws LWJGLException {
		Game game = new Game();
		
		game.init();
		game.loop();
		game.cleanUp();
	}
}
