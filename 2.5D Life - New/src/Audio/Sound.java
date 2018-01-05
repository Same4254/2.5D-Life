package Audio;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class Sound {
	private static ArrayList<Integer> buffers;
	private static SoundSource[] sources;
	
	public static void init() {
		buffers = new ArrayList<>();
		sources = new SoundSource[10];
		
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < sources.length; i++) 
			sources[i] = new SoundSource();
	}
	
	public static void play(int buffer, Vector3f position) {
		SoundSource source = getSource();
		
		if(source != null) {
			source.setPosition(position);
			source.play(buffer);
		} else {
			System.err.println("No Sound Sources Left!");
		}
	}
	
	public static SoundSource getSource() {
		SoundSource source = null;
		for(SoundSource s : sources) { 
			if(!s.isPlaying()) {
				source = s;
				break;
			}
		}
		
		return source;
	}
	
	public static void updateListenerData(Camera camera) {
		setListenerPosition(camera.getPosition());
		setListenerOrientation(camera);
	}
	 
	private static void setListenerPosition(Vector3f position) {
		AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
	}
	
	private static void setListenerOrientation(Camera camera) {
		Vector3f forward = MatrixUtil.forward(camera.getViewMatrix()).multiply(-1).normalize();
		Vector3f up = MatrixUtil.up(camera.getViewMatrix()).normalize();

		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(6);
		floatBuffer.put(forward.x);
		floatBuffer.put(forward.y);
		floatBuffer.put(forward.z);
		floatBuffer.put(up.x);
		floatBuffer.put(up.y);
		floatBuffer.put(up.z);
		
		floatBuffer.flip();
		
		AL10.alListener(AL10.AL_ORIENTATION, floatBuffer);
	}
	
	public static int loadSound(String file) {
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}
	
	public static void cleanUp() {
		for(int buffer : buffers)
			AL10.alDeleteBuffers(buffer);
		
		AL.destroy();
	}
}
