package bms.player.beatoraja.audio;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

public class LegacyPCM extends PCM {

	public LegacyPCM(Path p) throws java.io.IOException {
        super(null);
	}
}
