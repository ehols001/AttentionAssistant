package AttentionAssistant;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test File for AudioHandler functions.
 * @author ehols001
 */
public class Test_AudioHandler {

	AudioHandler handler;
	static String testAudioPath;
	
	@BeforeEach
	void setup() {
		handler = new AudioHandler();
		testAudioPath = "src/test/resources/TestAudioSounds/";
	}
	
	@Test
	@DisplayName("<AudioHandler> AudioPlayer")
	void AudioHandlerAudioPlayer() {
		String testSoundPath = testAudioPath + "testSound.wav";
		handler.AudioPlayer(testSoundPath);
		long endTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(3L, TimeUnit.SECONDS);
		while (System.nanoTime()< endTime){}
	}
	
	@Test
	@DisplayName("<AudioHandler> notificationTTS")
	void AudioHandlerNotificationTTS() {
		String text = "I am robot";
		handler.notificationTTS(text);
	}
}
