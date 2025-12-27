package bms.player.beatoraja.audio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

public class AudioTest {

    @Test
    public void testLoadWav() {
        try {
            File wavFile = new File("defaultsound/guide-pg.wav");
            if (!wavFile.exists()) {
                System.out.println("Skipping testLoadWav: defaultsound/guide-pg.wav not found");
                return;
            }
            
            Path path = Paths.get(wavFile.toURI());
            LegacyPCM pcm = new LegacyPCM(path);
            
            assertNotNull(pcm.getSample());
            assertTrue(pcm.getSample().length > 0);
            assertTrue(pcm.getChannels() > 0);
            assertTrue(pcm.getSampleRate() > 0);
            
            System.out.println("Loaded WAV: " + pcm.getSample().length + " samples, " + pcm.getChannels() + " channels, " + pcm.getSampleRate() + "Hz");
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to load WAV file: " + e.getMessage());
        }
    }
}
