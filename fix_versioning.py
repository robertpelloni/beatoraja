import re

# 1. Update MainController.java
with open("src/bms/player/beatoraja/MainController.java", "r") as f:
    content = f.read()

# Make sure it dynamically reads VERSION.md correctly and removes the "unknown version" fallback if possible,
# or leaves it as a true fallback but logs it.
# Actually, the file currently has:
# private static String VERSION = "beatoraja (unknown version)";
# and a static block reading VERSION.md. We need to make sure the UI titles use MainController.getVersion()
# instead of hardcoding "beatoraja 0.8" or "beatoraja 0.8.x".
