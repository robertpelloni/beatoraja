package bms.player.beatoraja.audio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class AudioTest {

    @Nested
    @DisplayName("LegacyPCM Tests")
    class LegacyPCMTests {

        @Test
        @DisplayName("Load WAV file successfully")
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
                assertTrue(pcm.validate());
                
                System.out.println("Loaded WAV: " + pcm.getSample().length + " samples, " + pcm.getChannels() + " channels, " + pcm.getSampleRate() + "Hz");
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to load WAV file: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Error handling for missing file")
        public void testLoadMissingFile() {
            Path nonExistentPath = Paths.get("nonexistent/missing_audio.wav");
            assertThrows(IOException.class, () -> new LegacyPCM(nonExistentPath));
        }

        @Test
        @DisplayName("Error handling for unsupported format")
        public void testLoadUnsupportedFormat() {
            Path invalidPath = Paths.get("build.gradle");
            assertThrows(IOException.class, () -> new LegacyPCM(invalidPath));
        }

        @Test
        @DisplayName("Change sample rate")
        public void testChangeSampleRate() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testChangeSampleRate: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM original = new LegacyPCM(path);
                int originalSampleRate = original.getSampleRate();
                int targetSampleRate = 22050;
                
                LegacyPCM resampled = original.changeSampleRate(targetSampleRate);
                
                assertNotNull(resampled);
                assertEquals(targetSampleRate, resampled.getSampleRate());
                assertEquals(original.getChannels(), resampled.getChannels());
                assertTrue(resampled.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to change sample rate: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Change frequency (pitch)")
        public void testChangeFrequency() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testChangeFrequency: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM original = new LegacyPCM(path);
                
                LegacyPCM pitched = original.changeFrequency(1.5f);
                
                assertNotNull(pitched);
                assertEquals(original.getSampleRate(), pitched.getSampleRate());
                assertTrue(pitched.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to change frequency: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Change channels (mono to stereo)")
        public void testChangeChannels() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testChangeChannels: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM original = new LegacyPCM(path);
                int targetChannels = original.getChannels() == 1 ? 2 : 1;
                
                LegacyPCM converted = original.changeChannels(targetChannels);
                
                assertNotNull(converted);
                assertEquals(targetChannels, converted.getChannels());
                assertEquals(original.getSampleRate(), converted.getSampleRate());
                assertTrue(converted.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to change channels: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Slice audio segment")
        public void testSlice() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testSlice: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM original = new LegacyPCM(path);
                
                LegacyPCM sliced = original.slice(0, 100);
                
                assertNotNull(sliced);
                assertEquals(original.getSampleRate(), sliced.getSampleRate());
                assertEquals(original.getChannels(), sliced.getChannels());
                assertTrue(sliced.getSample().length < original.getSample().length);
                assertTrue(sliced.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to slice audio: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Get input stream from PCM")
        public void testGetInputStream() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testGetInputStream: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM pcm = new LegacyPCM(path);
                
                java.io.InputStream inputStream = pcm.getInputStream();
                
                assertNotNull(inputStream);
                assertTrue(inputStream.available() > 0);
                
                byte[] header = new byte[4];
                int read = inputStream.read(header);
                assertEquals(4, read);
                assertEquals('R', header[0]);
                assertEquals('I', header[1]);
                assertEquals('F', header[2]);
                assertEquals('F', header[3]);
                
                inputStream.close();
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to get input stream: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Validate method returns true for valid sample")
        public void testValidateValidSample() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testValidateValidSample: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM original = new LegacyPCM(path);
                assertTrue(original.validate());
                
                LegacyPCM sliced = original.slice(0, 50);
                assertTrue(sliced.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to test validate: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("PCMLoader Tests")
    class PCMLoaderTests {

        @Test
        @DisplayName("Load WAV file with PCMLoader")
        public void testLoadWavWithPCMLoader() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testLoadWavWithPCMLoader: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                PCMLoader loader = PCMLoader.load(wavFile.getAbsolutePath());
                
                assertNotNull(loader);
                assertNotNull(loader.pcm);
                assertTrue(loader.channels > 0);
                assertTrue(loader.sampleRate > 0);
                assertTrue(loader.bitsPerSample > 0);
                
                System.out.println("PCMLoader WAV: " + loader.channels + " channels, " 
                    + loader.sampleRate + "Hz, " + loader.bitsPerSample + " bits");
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to load WAV with PCMLoader: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Error handling for missing file in PCMLoader")
        public void testLoadMissingFileWithPCMLoader() {
            assertThrows(IOException.class, () -> PCMLoader.load("nonexistent/missing_audio.wav"));
        }

        @Test
        @DisplayName("Error handling for unsupported format in PCMLoader")
        public void testLoadUnsupportedFormatWithPCMLoader() {
            assertThrows(IOException.class, () -> PCMLoader.load("build.gradle"));
        }

        @Test
        @DisplayName("Format detection - WAV extension")
        public void testFormatDetectionWav() {
            assertThrows(IOException.class, () -> PCMLoader.load("nonexistent.wav"));
        }

        @Test
        @DisplayName("Format detection - MP3 extension")
        public void testFormatDetectionMp3() {
            assertThrows(IOException.class, () -> PCMLoader.load("nonexistent.mp3"));
        }

        @Test
        @DisplayName("Format detection - OGG extension")
        public void testFormatDetectionOgg() {
            assertThrows(IOException.class, () -> PCMLoader.load("nonexistent.ogg"));
        }

        @Test
        @DisplayName("Format detection - FLAC extension")
        public void testFormatDetectionFlac() {
            assertThrows(IOException.class, () -> PCMLoader.load("nonexistent.flac"));
        }
    }

    @Nested
    @DisplayName("PCM Static Methods Tests")
    class PCMStaticTests {

        @Test
        @DisplayName("PCM.load with string path returns null for missing file")
        public void testPcmLoadStringPathMissingFile() {
            PCM<?> pcm = PCM.load("nonexistent/missing.wav", null);
            assertNull(pcm);
        }

        @Test
        @DisplayName("PCM.load with Path returns null for missing file")
        public void testPcmLoadPathMissingFile() {
            Path path = Paths.get("nonexistent/missing.wav");
            PCM<?> pcm = PCM.load(path, null);
            assertNull(pcm);
        }

        @Test
        @DisplayName("PCM.load with valid WAV file")
        public void testPcmLoadValidWav() {
            File wavFile = new File("defaultsound/guide-pg.wav");
            if (!wavFile.exists()) {
                System.out.println("Skipping testPcmLoadValidWav: defaultsound/guide-pg.wav not found");
                return;
            }
            
            PCM<?> pcm = PCM.load(wavFile.getAbsolutePath(), null);
            assertNotNull(pcm);
            assertTrue(pcm.validate());
        }
    }

    @Nested
    @DisplayName("WavFileInputStream Tests")
    class WavFileInputStreamTests {

        @Test
        @DisplayName("InputStream mark and reset")
        public void testInputStreamMarkReset() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testInputStreamMarkReset: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM pcm = new LegacyPCM(path);
                java.io.InputStream is = pcm.getInputStream();
                
                assertTrue(is.markSupported());
                
                byte[] firstRead = new byte[10];
                is.read(firstRead);
                
                is.mark(100);
                
                byte[] secondRead = new byte[10];
                is.read(secondRead);
                
                is.reset();
                
                byte[] afterReset = new byte[10];
                is.read(afterReset);
                
                assertArrayEquals(secondRead, afterReset);
                
                is.close();
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to test mark/reset: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("InputStream skip")
        public void testInputStreamSkip() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testInputStreamSkip: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM pcm = new LegacyPCM(path);
                java.io.InputStream is = pcm.getInputStream();
                
                int initialAvailable = is.available();
                long skipped = is.skip(100);
                
                assertEquals(100, skipped);
                assertEquals(initialAvailable - 100, is.available());
                
                is.close();
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to test skip: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("InputStream negative skip returns 0")
        public void testInputStreamNegativeSkip() {
            try {
                File wavFile = new File("defaultsound/guide-pg.wav");
                if (!wavFile.exists()) {
                    System.out.println("Skipping testInputStreamNegativeSkip: defaultsound/guide-pg.wav not found");
                    return;
                }
                
                Path path = Paths.get(wavFile.toURI());
                LegacyPCM pcm = new LegacyPCM(path);
                java.io.InputStream is = pcm.getInputStream();
                
                long skipped = is.skip(-10);
                assertEquals(0, skipped);
                
                is.close();
                
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to test negative skip: " + e.getMessage());
            }
        }
    }
}
