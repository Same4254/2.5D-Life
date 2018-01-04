package Audio;

import org.lwjgl.openal.AL10;

import com.Engine.Util.Vectors.Vector3f;

public class Source {
	private int sourceId;
	
	public Source() {
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_GAIN, 1);
		AL10.alSourcef(sourceId, AL10.AL_PITCH, 1);
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
	}
	
	public void play(int buffer) {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceId);
	}

	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	public void setPosition(Vector3f position) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x, position.y, position.z);
	}
	
	public void delte() {
		AL10.alDeleteBuffers(sourceId);
	}
}
