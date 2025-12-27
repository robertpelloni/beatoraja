package bms.player.beatoraja.audio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javazoom.jl.decoder.*;

import org.jflac.FLACDecoder;
import org.jflac.PCMProcessor;
import org.jflac.metadata.StreamInfo;
import org.jflac.util.ByteData;

import com.badlogic.gdx.backends.lwjgl3.audio.OggInputStream;
import com.badlogic.gdx.utils.StreamUtils;

public class PCMLoader {
    public ByteBuffer pcm;
    public int bitsPerSample;
    public int channels;
    public int sampleRate;
    
    public static PCMLoader load(String path) throws IOException {
        PCMLoader loader = new PCMLoader();
        Path p = Paths.get(path);
        
        if (path.toLowerCase().endsWith(".mp3")) {
            try {
                byte[] data = decodeMP3(new BufferedInputStream(new BufferedInputStream(Files.newInputStream(p))), loader);
                loader.pcm = ByteBuffer.wrap(data);
            } catch (Throwable ex) {
                throw new IOException("Failed to load MP3: " + path, ex);
            }
        } else if (path.toLowerCase().endsWith(".ogg")) {
            try (OggInputStream input = new OggInputStream(new BufferedInputStream(Files.newInputStream(p)))) {
                ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
                byte[] buff = new byte[2048];
                while (!input.atEnd()) {
                    int length = input.read(buff);
                    if (length == -1)
                        break;
                    output.write(buff, 0, length);
                }

                loader.channels = input.getChannels();
                loader.sampleRate = input.getSampleRate();
                loader.bitsPerSample = 16;
                loader.pcm = ByteBuffer.wrap(output.toByteArray());
            } catch (Throwable ex) {
                throw new IOException("Failed to load OGG: " + path, ex);
            }
        } else if (path.toLowerCase().endsWith(".wav")) {
            try (WavInputStream input = new WavInputStream(new BufferedInputStream(Files.newInputStream(p)))) {
                byte[] data;
                if (input.type == 85) { // MP3 inside WAV
                    try {
                        data = decodeMP3(new ByteArrayInputStream(
                                StreamUtils.copyStreamToByteArray(input, input.dataRemaining)), loader);
                    } catch (BitstreamException e) {
                        throw new IOException("Failed to decode MP3 in WAV: " + path, e);
                    }
                } else {
                    data = StreamUtils.copyStreamToByteArray(input, input.dataRemaining);
                }
                loader.pcm = ByteBuffer.wrap(data);
                loader.channels = input.channels;
                loader.sampleRate = input.sampleRate;
                loader.bitsPerSample = input.bitsPerSample;
            } catch (IOException e) {
                throw new IOException("Failed to load WAV: " + path, e);
            }
        } else if (path.toLowerCase().endsWith(".flac")) {
            try (InputStream is = new BufferedInputStream(Files.newInputStream(p))) {
                FLACDecoder decoder = new FLACDecoder(is);
                ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
                
                decoder.addPCMProcessor(new PCMProcessor() {
                    @Override
                    public void processStreamInfo(StreamInfo streamInfo) {
                        loader.channels = streamInfo.getChannels();
                        loader.sampleRate = streamInfo.getSampleRate();
                        loader.bitsPerSample = streamInfo.getBitsPerSample();
                    }
                    
                    @Override
                    public void processPCM(ByteData byteData) {
                        output.write(byteData.getData(), 0, byteData.getLen());
                    }
                });
                
                decoder.decode();
                loader.pcm = ByteBuffer.wrap(output.toByteArray());
            } catch (Throwable ex) {
                throw new IOException("Failed to load FLAC: " + path, ex);
            }
        } else {
             throw new IOException("Unsupported file format: " + path);
        }
        
        return loader;
    }

    private static byte[] decodeMP3(InputStream is, PCMLoader loader) throws BitstreamException {
        Bitstream bitstream = new Bitstream(is);
        ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
        MP3Decoder decoder = new MP3Decoder();
        OutputBuffer outputBuffer = null;
        while (true) {
            Header header = bitstream.readFrame();
            if (header == null)
                break;
            if (outputBuffer == null) {
                loader.channels = header.mode() == Header.SINGLE_CHANNEL ? 1 : 2;
                outputBuffer = new OutputBuffer(loader.channels, false);
                decoder.setOutputBuffer(outputBuffer);
                loader.sampleRate = header.getSampleRate();
                loader.bitsPerSample = 16;
            }
            try {
                decoder.decodeFrame(header, bitstream);
            } catch (Exception ignored) {
            }
            bitstream.closeFrame();
            output.write(outputBuffer.getBuffer(), 0, outputBuffer.reset());
        }
        bitstream.close();
        return output.toByteArray();
    }
    
    /** @author Nathan Sweet */
    private static class WavInputStream extends FilterInputStream {
        private int dataRemaining;
        int channels;
        int sampleRate;
        int bitsPerSample;
        int type;

        WavInputStream(InputStream p) {
            super(p);
            try {
                if (read() != 'R' || read() != 'I' || read() != 'F' || read() != 'F')
                    throw new RuntimeException("RIFF header not found");

                skipFully(4);

                if (read() != 'W' || read() != 'A' || read() != 'V' || read() != 'E')
                    throw new RuntimeException("Invalid wave file header");

                int fmtChunkLength = seekToChunk('f', 'm', 't', ' ');

                type = read() & 0xff | (read() & 0xff) << 8;

                channels = read() & 0xff | (read() & 0xff) << 8;

                sampleRate = read() & 0xff | (read() & 0xff) << 8 | (read() & 0xff) << 16 | (read() & 0xff) << 24;

                skipFully(6);

                bitsPerSample = read() & 0xff | (read() & 0xff) << 8;

                skipFully(fmtChunkLength - 16);

                dataRemaining = seekToChunk('d', 'a', 't', 'a');
            } catch (Throwable ex) {
                StreamUtils.closeQuietly(this);
                throw new RuntimeException("Error reading WAV file", ex);
            }
        }

        private int seekToChunk(char c1, char c2, char c3, char c4) throws IOException {
            while (true) {
                boolean found = read() == c1;
                found &= read() == c2;
                found &= read() == c3;
                found &= read() == c4;
                int chunkLength = read() & 0xff | (read() & 0xff) << 8 | (read() & 0xff) << 16 | (read() & 0xff) << 24;
                if (chunkLength == -1)
                    throw new IOException("Chunk not found: " + c1 + c2 + c3 + c4);
                if (found)
                    return chunkLength;
                skipFully(chunkLength);
            }
        }

        private void skipFully(int count) throws IOException {
            while (count > 0) {
                long skipped = in.skip(count);
                if (skipped <= 0)
                    throw new EOFException("Unable to skip.");
                count -= skipped;
            }
        }

        public int read(byte[] buffer) throws IOException {
            if (dataRemaining == 0)
                return -1;
            int length = Math.min(super.read(buffer), dataRemaining);
            if (length == -1)
                return -1;
            dataRemaining -= length;
            return length;
        }
    }
}
