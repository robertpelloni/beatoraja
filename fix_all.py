import os
import glob

# 1. Fix SkinObjectRenderer
target_skin = "src/bms/player/beatoraja/skin/Skin.java"
with open(target_skin, 'r') as f:
    content = f.read()

renderer_code = """public class Skin {

	public static class SkinObjectRenderer extends SpriteBatch {
		public static final int TYPE_NORMAL = 0;
		public static final int TYPE_BILINEAR = 1;
		public static final int TYPE_LINEAR = 2;
		public static final int TYPE_LAYER = 3;
		public static final int TYPE_FFMPEG = 4;
		public static final int TYPE_DISTANCE_FIELD = 5;
		public static final int TYPE_MIRROR = 6;
		public static final int TYPE_FLIP = 7;
		public static final int TYPE_FLIP_MIRROR = 8;

		private int type = 0;

		public void setType(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}"""

if "public static class SkinObjectRenderer" not in content:
    content = content.replace('public class Skin {', renderer_code)
    with open(target_skin, 'w') as f:
        f.write(content)

# 2. Fix PCM generics
pcm_files = {
    "src/bms/player/beatoraja/audio/FloatPCM.java": "extends PCM<float[]>",
    "src/bms/player/beatoraja/audio/BytePCM.java": "extends PCM<byte[]>",
    "src/bms/player/beatoraja/audio/LegacyPCM.java": "extends PCM<short[]>",
    "src/bms/player/beatoraja/audio/ShortPCM.java": "extends PCM<short[]>",
    "src/bms/player/beatoraja/audio/ShortDirectPCM.java": "extends PCM<ByteBuffer>"
}

for path, old_val in pcm_files.items():
    if os.path.exists(path):
        with open(path, 'r') as f:
            content = f.read()
        content = content.replace(old_val, "extends PCM")
        with open(path, 'w') as f:
            f.write(content)

# 3. Fix OggInputStream
pcm_base = "src/bms/player/beatoraja/audio/PCM.java"
if os.path.exists(pcm_base):
    with open(pcm_base, 'r') as f:
        content = f.read()
    content = content.replace("com.badlogic.gdx.backends.lwjgl.audio.OggInputStream", "com.badlogic.gdx.backends.lwjgl3.audio.OggInputStream")
    with open(pcm_base, 'w') as f:
        f.write(content)

# 4. Fix AsioDriver
play_config = "src/bms/player/beatoraja/PlayConfigurationView.java"
if os.path.exists(play_config):
    with open(play_config, 'r') as f:
        content = f.read()
    content = content.replace("import com.synthbot.jasiohost.AsioDriver;", "")
    content = content.replace("List<String> drivers = AsioDriver.getDriverNames();", "List<String> drivers = new java.util.ArrayList<>();")
    with open(play_config, 'w') as f:
        f.write(content)

print("Fixes applied.")
