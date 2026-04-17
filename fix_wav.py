with open("src/bms/player/beatoraja/audio/LegacyPCM.java", "r") as f:
    content = f.read()

# Since WavFileInputStream is already in PCM.java as a package-private class,
# LegacyPCM should probably just use it or be embedded in PCM, or we rename it to LegacyWavFileInputStream.
# Let's see the constructor in PCM.java.
