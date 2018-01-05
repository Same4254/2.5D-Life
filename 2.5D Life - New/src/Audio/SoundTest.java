package Audio;

import java.io.IOException;

import com.Engine.Util.Vectors.Vector3f;

public class SoundTest {
	public static void main(String[] args) throws IOException {
		Sound.init();
//		Sound.setListenerPosition(new Vector3f());
		
		int buffer = Sound.loadSound("sounds/bounce.wav");
		SoundSource source = new SoundSource();

		source.setVolume(100);
		source.setLooping(true);
		source.play(buffer);
		
		Vector3f position = new Vector3f(8, 0, 2);
		
		char c = ' ';
		while(c != 'q') {
			position = position.add(-.03f, 0, 0);
			source.setPosition(position);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		source.delete();
		Sound.cleanUp();
	}
}
